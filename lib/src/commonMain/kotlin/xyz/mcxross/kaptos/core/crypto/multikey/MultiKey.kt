package xyz.mcxross.kaptos.core.crypto.multikey

import xyz.mcxross.bcs.Bcs
import xyz.mcxross.kaptos.core.AuthenticationKey
import xyz.mcxross.kaptos.core.crypto.PublicKey
import xyz.mcxross.kaptos.core.crypto.Signature
import xyz.mcxross.kaptos.model.AuthenticationKeyScheme
import xyz.mcxross.kaptos.model.HexInput
import xyz.mcxross.kaptos.model.SigningScheme

class MultiKey(val pks: List<PublicKey>, val signaturesRequired: Int) : AbstractMultiKey(pks) {
  override fun getSigsRequired(): Int {
    TODO("Not yet implemented")
  }

  override fun authKey(): AuthenticationKey {
    return AuthenticationKey.fromSchemeAndBytes(
      AuthenticationKeyScheme.Signing(SigningScheme.MultiKey),
      HexInput.fromByteArray(
        Bcs.encodeToByteArray(publicKeys.map { it.toByteArray() }) +
          Bcs.encodeToByteArray(signaturesRequired.toUByte())
      ),
    )
  }

  override fun verifySignature(message: HexInput, signature: Signature): Boolean {
    TODO("Not yet implemented")
  }

  override fun toByteArray(): ByteArray {
    return Bcs.encodeToByteArray(publicKeys.map { it.toByteArray() }) +
      Bcs.encodeToByteArray(signaturesRequired.toUByte())
  }

  override fun toBcs(): ByteArray {
    TODO("Not yet implemented")
  }
}
