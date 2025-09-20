package xyz.mcxross.kaptos.account

import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.core.crypto.multikey.MultiKey
import xyz.mcxross.kaptos.model.AccountAddress
import xyz.mcxross.kaptos.model.AccountAddressInput
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningScheme
import xyz.mcxross.kaptos.transaction.authenticatior.AccountAuthenticator

class MultiKeyAccount(
    val multiKey: MultiKey,
    signers: List<Ed25519Account> = emptyList(),
    address: AccountAddressInput? = null
) : Account() {
    override val publicKey: PublicKey
        get() = TODO("Not yet implemented")

    override val accountAddress: AccountAddress
        get() = multiKey.authKey().deriveAddress()

    override val signingScheme: SigningScheme
        get() = TODO("Not yet implemented")

    override fun signWithAuthenticator(message: HexInput): AccountAuthenticator {
        TODO("Not yet implemented")
    }

    override fun sign(message: HexInput): Signature {
        TODO("Not yet implemented")
    }

}
