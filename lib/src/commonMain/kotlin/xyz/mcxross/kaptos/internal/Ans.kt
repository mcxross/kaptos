/*
 * Copyright 2024 McXross
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xyz.mcxross.kaptos.internal

import xyz.mcxross.kaptos.model.*

/**
 * Checks if a fragment of a name, either the domain or subdomain, is valid
 *
 * @param fragment A fragment of a name, either the domain or subdomain
 * @return boolean indicating if the fragment is a valid fragment
 */
fun isValidANSSegment(fragment: String): Boolean {
  if (fragment.isEmpty()) return false
  if (fragment.length < 3) return false
  if (fragment.length > 63) return false
  // only lowercase a-z and 0-9 are allowed, along with -. a domain may not start or end with a
  // hyphen
  if (!Regex("^[a-z\\d][a-z\\d-]{1,61}[a-z\\d]$").matches(fragment)) return false
  return true
}

val VALIDATION_RULES_DESCRIPTION =
  listOf(
      "A name must be between 3 and 63 characters long,",
      "and can only contain lowercase a-z, 0-9, and hyphens.",
      "A name may not start or end with a hyphen.",
    )
    .joinToString(" ")

/**
 * Checks if an ANS name is valid or not
 *
 * @param name A string of the domain name, can include or exclude the .apt suffix
 * @return a Pair containing the domain name and optionally the subdomain name
 */
fun isValidANSName(name: String): Pair<String, String?> {
  val parts = name.replace("\\.apt$".toRegex(), "").split(".")

  if (parts.size > 2) {
    throw IllegalArgumentException(
      "$name is invalid. A name can only have two parts, a domain and a subdomain separated by a '.'"
    )
  }

  val first = parts[0]
  if (!isValidANSSegment(first)) {
    throw IllegalArgumentException("$first is not valid. $VALIDATION_RULES_DESCRIPTION")
  }

  val second = parts.getOrNull(1)
  if (second != null && !isValidANSSegment(second)) {
    throw IllegalArgumentException("$second is not valid. $VALIDATION_RULES_DESCRIPTION")
  }

  return Pair(second ?: first, if (second != null) first else null)
}

val LOCAL_ANS_ACCOUNT_PK = "0x37368b46ce665362562c6d1d4ec01a08c8644c488690df5a17e13ba163e20221"
const val LOCAL_ANS_ACCOUNT_ADDRESS =
  "0x585fc9f0f0c54183b039ffc770ca282ebd87307916c215a3e692f2f8e4305e82"

val NetworkToAnsContract: Map<Network, String?> =
  mapOf(
    Network.TESTNET to "0x5f8fd2347449685cf41d4db97926ec3a096eaf381332be4f1318ad4d16a8497c",
    Network.MAINNET to "0x867ed1f6bf916171b1de3ee92849b8978b7d1b9e0a8cc982a3d19d535dfd9c0c",
    Network.LOCAL to LOCAL_ANS_ACCOUNT_ADDRESS,
    Network.CUSTOM to null,
    Network.DEVNET to null,
    Network.RANDOMNET to null,
  )

fun getRouterAddress(aptosConfig: AptosConfig): String {
  val address =
    NetworkToAnsContract[aptosConfig.network]
      ?: throw Error("The ANS contract is not deployed to ${aptosConfig.network}")
  return address
}

suspend fun getOwnerAddress(aptosConfig: AptosConfig, name: String): Option<AccountAddress> {
  val routerAddress = getRouterAddress(aptosConfig)
  val ansName = isValidANSName(name)

  val viewFunctionData =
    InputViewFunctionData(
      function = "${routerAddress}::router::get_owner_addr",
      typeArguments = emptyList(),
      functionArguments = listOf(MoveString(ansName.first), MoveString(ansName.second ?: "")),
    )

  val res =
    view<List<MoveValue.MoveListType<MoveValue.String>>>(aptosConfig, payload = viewFunctionData)

  return when (res) {
    is Option.None -> Option.None
    is Option.Some -> {
      Option.Some(AccountAddress.fromString(res.value[0].value[0].value))
    }
  }
}

suspend fun getExpiration(aptosConfig: AptosConfig, name: String): Option<Long> {
  val routerAddress = getRouterAddress(aptosConfig)
  val ansName = isValidANSName(name)

  val viewFunctionData =
    InputViewFunctionData(
      function = "${routerAddress}::router::get_expiration",
      typeArguments = emptyList(),
      functionArguments = listOf(MoveString(ansName.first), MoveString(ansName.second ?: "")),
    )

  return when (val res = view<List<MoveValue.String>>(aptosConfig, payload = viewFunctionData)) {
    is Option.None -> Option.None
    is Option.Some -> {
      Option.Some(res.value[0].value.toLong())
    }
  }
}

suspend fun getTargetAddress(aptosConfig: AptosConfig, name: String): Option<AccountAddress> {
  val routerAddress = getRouterAddress(aptosConfig)
  val ansName = isValidANSName(name)

  val viewFunctionData =
    InputViewFunctionData(
      function = "${routerAddress}::router::get_target_addr",
      typeArguments = emptyList(),
      functionArguments = listOf(MoveString(ansName.first), MoveString(ansName.second ?: "")),
    )

  val res =
    view<List<MoveValue.MoveListType<MoveValue.String>>>(aptosConfig, payload = viewFunctionData)

  return when (res) {
    is Option.None -> Option.None
    is Option.Some -> {
      val accountAddressString = res.value.getOrNull(0)?.value?.getOrNull(0)?.value
      if (accountAddressString != null) {
        Option.Some(AccountAddress.fromString(accountAddressString))
      } else {
        Option.None
      }
    }
  }
}

suspend fun setTargetAddress(
  aptosConfig: AptosConfig,
  sender: AccountAddress,
  name: String,
  address: AccountAddressInput,
  options: InputGenerateTransactionOptions = InputGenerateTransactionOptions(),
): SimpleTransaction {
  val routerAddress = getRouterAddress(aptosConfig)
  val ansName = isValidANSName(name)

  val inputEntryFunctionData =
    InputEntryFunctionData(
      function = "${routerAddress}::router::set_target_addr",
      typeArguments = emptyList(),
      functionArguments =
        listOf(
          MoveString(ansName.first),
          MoveString(ansName.second ?: ""),
          MoveString(address.value),
        ),
    )

  val signerRawTransactionData =
    InputGenerateSingleSignerRawTransactionData(
      sender = sender,
      data = inputEntryFunctionData,
      options = options,
      withFeePayer = false,
      secondarySignerAddresses = null,
    )

  val transaction = generateTransaction(aptosConfig, signerRawTransactionData)

  return transaction as SimpleTransaction
}

suspend fun getPrimaryName(aptosConfig: AptosConfig, address: AccountAddressInput): Option<String> {
  val routerAddress = getRouterAddress(aptosConfig)

  val viewFunctionData =
    InputViewFunctionData(
      function = "${routerAddress}::router::get_primary_name",
      typeArguments = emptyList(),
      functionArguments =
        when (address) {
          is AccountAddress -> listOf(address)
          is HexInput -> listOf(AccountAddress.fromString(address.value))
          else -> {
            throw Error("Unsupported address type")
          }
        },
    )

  val res =
    view<List<MoveValue.MoveListType<MoveValue.String>>>(aptosConfig, payload = viewFunctionData)

  return when (res) {
    is Option.None -> Option.None
    is Option.Some -> {
      Option.Some(
        res.value
          .mapNotNull { if (it.value.isNotEmpty()) it.value[0].value else null }
          .joinToString(".")
      )
    }
  }
}
