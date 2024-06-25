package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.expect
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.account.Ed25519Account
import xyz.mcxross.kaptos.core.crypto.Ed25519PublicKey
import xyz.mcxross.kaptos.model.SigningScheme

class AccountTest {
  // should create an instance of Account with a legacy ED25519 when nothing is specified
  @Test
  fun accountDefaultTest() {
    val account = Account.generate()
    expect(Ed25519Account::class) { account::class }
    expect(Ed25519PublicKey::class) { account.publicKey::class }
    assertEquals(account.signingScheme, SigningScheme.Ed25519)
  }
}
