#!/usr/bin/env node
// Copyright 2026 McXross
// SPDX-License-Identifier: Apache-2.0

import { mkdir, writeFile } from "node:fs/promises";
import { createHash } from "node:crypto";
import { dirname, resolve } from "node:path";
import { pathToFileURL } from "node:url";

const [sdkDirectory, outputPath] = process.argv.slice(2);
if (!sdkDirectory || !outputPath) {
  throw new Error("Usage: generate-aptos-ts-fixtures.mjs <aptos-ts-sdk> <output.json>");
}

const aptos = await import(pathToFileURL(resolve(sdkDirectory, "dist/index.js")));
const keyless = await import(
  pathToFileURL(resolve(sdkDirectory, "dist/functions/keyless.js")),
);
const keylessCrypto = await import(
  pathToFileURL(resolve(sdkDirectory, "dist/core/crypto/keyless.js")),
);
const { p256 } = await import(
  pathToFileURL(resolve(sdkDirectory, "node_modules/@noble/curves/nist.js"))
);
const toHex = (bytes) => Buffer.from(bytes).toString("hex");
const toBytes = (value) =>
  typeof value.toUint8Array === "function" ? value.toUint8Array() : value;
const toBcs = (value) => {
  const serializer = new aptos.Serializer();
  value.serialize(serializer);
  return serializer.toUint8Array();
};
const fromHex = (value) => Uint8Array.from(Buffer.from(value.replace(/^0x/, ""), "hex"));

const sender = aptos.AccountAddress.fromString("0x1");
const secondary = aptos.AccountAddress.fromString("0x2");
const feePayer = aptos.AccountAddress.fromString("0x3");
const entry = aptos.EntryFunction.build("0x1::coin", "transfer", [], [new aptos.U64(123n)]);
const entryPayload = new aptos.TransactionPayloadEntryFunction(entry);
const scriptPayload =
  new aptos.TransactionPayloadScript(
    new aptos.Script(Uint8Array.from([1, 2, 3]), [], [new aptos.U64(456n)]),
  );
const multisigPayload =
  new aptos.TransactionPayloadMultiSig(
    new aptos.MultiSig(feePayer, new aptos.MultiSigTransactionPayload(entry)),
  );
const orderlessPayload =
  new aptos.TransactionInnerPayloadV1(
    new aptos.TransactionExecutableEntryFunction(entry),
    new aptos.TransactionExtraConfigV1(undefined, 0xcafebabedeadbeefn),
  );
const raw =
  new aptos.RawTransaction(
    sender,
    7n,
    entryPayload,
    2_000_000n,
    100n,
    999_999n,
    new aptos.ChainId(4),
  );
const orderlessRaw =
  new aptos.RawTransaction(
    sender,
    0xffffffffffffffffn,
    orderlessPayload,
    2_000_000n,
    100n,
    999_999n,
    new aptos.ChainId(4),
  );
const simple = new aptos.SimpleTransaction(raw);
const multiAgent = new aptos.MultiAgentTransaction(raw, [secondary]);
const sponsored = new aptos.MultiAgentTransaction(raw, [secondary], feePayer);

const publicKey = new aptos.Ed25519PublicKey(Uint8Array.from({ length: 32 }, () => 0x11));
const rotationProofChallenge =
  new aptos.RotationProofChallenge({
    sequenceNumber: 7n,
    originator: sender,
    currentAuthKey: secondary,
    newPublicKey: publicKey,
  });
const signature = new aptos.Ed25519Signature(Uint8Array.from({ length: 64 }, () => 0x22));
const accountAuthenticator = new aptos.AccountAuthenticatorEd25519(publicKey, signature);
const transactionAuthenticator =
  new aptos.TransactionAuthenticatorMultiAgent(
    accountAuthenticator,
    [secondary],
    [accountAuthenticator],
  );
const signed = new aptos.SignedTransaction(raw, transactionAuthenticator);
const secp256r1PrivateKey =
  new aptos.Secp256r1PrivateKey(Uint8Array.from({ length: 32 }, (_, index) => index + 1), false);
const secp256r1Message = new TextEncoder().encode("aptos-secp256r1");
const secp256r1Signature = secp256r1PrivateKey.signBytes(secp256r1Message);
const webAuthnSigningMessage = new TextEncoder().encode("aptos-webauthn");
const webAuthnChallenge = createHash("sha3-256").update(webAuthnSigningMessage).digest();
const webAuthnClientData = new TextEncoder().encode(
  JSON.stringify({
    type: "webauthn.get",
    challenge: webAuthnChallenge.toString("base64url"),
    origin: "https://example.com",
    crossOrigin: false,
  }),
);
const webAuthnAuthenticatorData = Uint8Array.from([
  73, 150, 13, 229, 136, 14, 140, 104, 116, 52, 23, 15, 100, 118, 96, 91,
  143, 228, 174, 185, 162, 134, 50, 199, 153, 92, 243, 186, 131, 29, 151, 99,
  29, 0, 0, 0, 0,
]);
const webAuthnClientHash = createHash("sha256").update(webAuthnClientData).digest();
const webAuthnSignedBytes = Buffer.concat([
  webAuthnAuthenticatorData,
  webAuthnClientHash,
]);
const webAuthnDigest = createHash("sha256").update(webAuthnSignedBytes).digest();
const webAuthnRawSignature = p256.sign(
  webAuthnDigest,
  secp256r1PrivateKey.toUint8Array(),
  { prehash: false },
);
const webAuthnSignature = new aptos.WebAuthnSignature(
  webAuthnRawSignature,
  webAuthnAuthenticatorData,
  webAuthnClientData,
);
const webAuthnAuthenticator = new aptos.AccountAuthenticatorSingleKey(
  new aptos.AnyPublicKey(secp256r1PrivateKey.publicKey()),
  new aptos.AnySignature(webAuthnSignature),
);
const authenticationFunction = "0x1::permissioned_delegation::authenticate";
const abstractionSignature = Uint8Array.from([0xaa, 0xbb, 0xcc]);
const abstractedAccount =
  new aptos.AbstractedAccount({
    accountAddress: sender,
    authenticationFunction,
    signer: () => abstractionSignature,
  });
const abstractionSigningMessage = aptos.AbstractedAccount.generateAccountAbstractionMessage(
  aptos.generateSigningMessageForTransaction(simple),
  authenticationFunction,
);
const abstractionAuthenticator = abstractedAccount.signTransactionWithAuthenticator(simple);
const derivableIdentity = Uint8Array.from([1, 2, 3, 4]);
const derivableAbstractedAccount =
  new aptos.DerivableAbstractedAccount({
    authenticationFunction,
    abstractPublicKey: derivableIdentity,
    signer: () => abstractionSignature,
  });
const derivableAbstractionAuthenticator =
  derivableAbstractedAccount.signTransactionWithAuthenticator(simple);

// Keyless fixture values are the official SDK's deterministic unit-test inputs. The
// encodings and derived values below are produced by the pinned SDK implementation.
const keylessJwt =
  "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6InRlc3QtcnNhIn0.eyJpc3MiOiJ0ZXN0Lm9pZGMucHJvdmlkZXIiLCJhdWQiOiJ0ZXN0LWtleWxlc3MtZGFwcCIsInN1YiI6InRlc3QtdXNlci0wIiwiZW1haWwiOiJ0ZXN0QGFwdG9zbGFicy5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaWF0Ijo5ODc2NTQzMjA5LCJleHAiOjk4NzY1NDMyMTAsIm5vbmNlIjoiMTk2NDM2OTg4NjEyNjU1Njc4MDQ5MDk5MTMxMzA1MDcyNDc4MTQ1MjY5MTM1NzAyMjgzMTY0MTczNzc5NjUxMDU2ODE3OTYxNzMwOTgifQ.C6QG9WyEIAqYEiLkY8-5yqTKYtCzmnu2RM4P7iqr17toRXhL2ZqCiQYgE2TpY60RlOqBI7_aiHOlxJRvF_iQghEQQSWkgWhkcjVkSvBJW0IHm0IrSRl9ZytQHi6x0vPa8bUff5L--9JfxMiH27wOTrGtTA1n8Fz3G8JKQfYNQF2VawzytJu3lywduRj6pZw9-FFTgPqPsZWQvwhiX75Tgud976CpDusKOrPAM3rA9fXgKo_aTKeOPiEIm11ezI1bsOJ3B4JhsxLT5vszZ11Ywytst8XXwqWHjnulkJWjM9QfVUJhsO-jEQ5T_dYDqMVnnkdzjJyMRbvgbyNPUkvx8Q";
const keylessPepper =
  "0x772714089792b0bc8c621843bd88599627c74564c47cb4dc7bc0196914a56c";
const keylessProofBytes = fromHex(
  "0x00ac1c3add4fa703c66a940e9e947a71bcdb8f30258e72460c01f63d6236d9b2a835188c3ac199bea3905270b9660ddafacbf4f9addb93a3e235e9703ca72c40258362273f596f93594499527ca4802ef40cf0166ba3bd2d65d1a7f50562060127dcdcd995f9ae5e193582cce456f3ddfe8c0c935719ad8636a8e777369279d5a480969800000000000000010040d6433ea43090d25fc4f4a15c362a98a5343dcf4e29e3854f3b74d0d99a0b43abd9955c55a7d20f47372a5802a0e26cc4f860969109d48c9e989dab8287c41501",
);
const keylessOfficialSignatureBytes = fromHex(
  "0x000028edb9b770bd33823ed3aa95d6a464ee61a6370f3662f9edb205e1fad45e3c943296fed377bcece279bd6f68649fdab2f81ca3e83b34fce490492574e2943f04e4f9bfda70c4325e4567bcb58c1a7ccbd67ff9ba618e03be794be0483141bc15a436433070367193c102fbad99c1fed866e34b1f624d64ecfd818a09aa62a41b80969800000000000000010040119893806295fa773fa806a1f5e0754055f773e8a2ca72a0442c23945c004f011c3a03ed218f246e0d758032f16de78b9c93b6868b0b81bea083c114e6d855052c7b22616c67223a225253323536222c22747970223a224a5754222c226b6964223a22746573742d727361227dea16b04c020000000020d04ab232742bb4ab3a1368bd4615e4e6d0224ab71a016baf8520a332c97787370040be6bc1c26488a31fdb030ccd1546e0dcb6dd4ffbada797040deba21231ce894470f1aef38272fe4c77725e77945c6c3b67c6c16d29e2d3ccf4f30bf4374b0f08",
);
const keylessJwkBytes = fromHex(
  "0x08746573742d727361035253410552533235360441514142d6027935456673315a7a69734c4c4b4341525376547a7467576a354a465033373738645a57742d6f643738666d4f5a4678656d33615f6159624f58534a546f5270383632646f3050784a3450444d706d7177563566374b706c4649364e737751562d57507566514838496148585a74755064436a504f634879626344694c6b4f31326430644736695a51557a79706a414a6636334150636164696f2d344a444e576c4743355f4f775f5851396c495937316b544d6954396c6b434364305a787145696647746e4a653578536f5a6f614d524b72766c4f772d523669566a4c557450416b356879555839354c444b787741522d6f73686e6a37676d4154656a676132457648396f7a646e334d38476f31315053446130344f517850634132354f6f445466784c765432384c5270535872626d55575a2d4f5f6c4774446c335a41746a4967755947456f62546b344e3131655273734339354377",
);
const keylessEphemeral = new keyless.EphemeralKeyPair({
  privateKey: new aptos.Ed25519PrivateKey(
    "ed25519-priv-0x1111111111111111111111111111111111111111111111111111111111111111",
  ),
  expiryDateSecs: 9_876_543_210,
  blinder: new Uint8Array(31),
});
const keylessProof = keyless.ZeroKnowledgeSig.fromBytes(keylessProofBytes);
const keylessPublicKey = keyless.KeylessPublicKey.fromJwtAndPepper({
  jwt: keylessJwt,
  pepper: keylessPepper,
});
const keylessAccount = keyless.KeylessAccount.create({
  jwt: keylessJwt,
  pepper: keylessPepper,
  ephemeralKeyPair: keylessEphemeral,
  proof: keylessProof,
});
const keylessMessage = new TextEncoder().encode("hello world");
const keylessAccountSignature = keylessAccount.sign(keylessMessage);
const keylessOfficialSignature = keyless.KeylessSignature.deserialize(
  new aptos.Deserializer(keylessOfficialSignatureBytes),
);
const keylessJwk = keylessCrypto.MoveJWK.deserialize(new aptos.Deserializer(keylessJwkBytes));
const keylessAnyPublicKey = new aptos.AnyPublicKey(keylessPublicKey);
const keylessAnySignature = new aptos.AnySignature(keylessOfficialSignature);

const fixture = {
  baseline: {
    version: "7.3.1",
    commit: "82b310449d7adf184540730ffdd754ada18d8ca6",
  },
  payloads: {
    entryFunction: toHex(toBcs(entryPayload)),
    script: toHex(toBcs(scriptPayload)),
    multisig: toHex(toBcs(multisigPayload)),
    orderless: toHex(toBcs(orderlessPayload)),
  },
  rawTransaction: toHex(toBcs(raw)),
  orderlessRawTransaction: toHex(toBcs(orderlessRaw)),
  signingMessages: {
    simple: toHex(aptos.generateSigningMessageForTransaction(simple)),
    multiAgent: toHex(aptos.generateSigningMessageForTransaction(multiAgent)),
    feePayer: toHex(aptos.generateSigningMessageForTransaction(sponsored)),
  },
  authenticators: {
    accountEd25519: toHex(toBcs(accountAuthenticator)),
    transactionMultiAgent: toHex(toBcs(transactionAuthenticator)),
    signedMultiAgent: toHex(toBcs(signed)),
  },
  typeTags: {
    i8: toHex(toBcs(new aptos.TypeTagI8())),
    i16: toHex(toBcs(new aptos.TypeTagI16())),
    i32: toHex(toBcs(new aptos.TypeTagI32())),
    i64: toHex(toBcs(new aptos.TypeTagI64())),
    i128: toHex(toBcs(new aptos.TypeTagI128())),
    i256: toHex(toBcs(new aptos.TypeTagI256())),
  },
  accounts: {
    rotationProofChallenge: toHex(toBcs(rotationProofChallenge)),
    abstraction: {
      authenticationFunction,
      signingMessage: toHex(toBytes(abstractionSigningMessage)),
      signingMessageDigest: toHex(createHash("sha3-256").update(toBytes(abstractionSigningMessage)).digest()),
      authenticator: toHex(toBcs(abstractionAuthenticator)),
      signature: toHex(abstractionSignature),
      derivableIdentity: toHex(derivableIdentity),
      derivableAddress: derivableAbstractedAccount.accountAddress.toStringLong(),
      derivableAuthenticator: toHex(toBcs(derivableAbstractionAuthenticator)),
    },
  },
  crypto: {
    secp256r1: {
      privateKey: toHex(secp256r1PrivateKey.toUint8Array()),
      publicKey: toHex(secp256r1PrivateKey.publicKey().toUint8Array()),
      message: toHex(secp256r1Message),
      signature: toHex(secp256r1Signature.toUint8Array()),
    },
    webAuthn: {
      signingMessage: toHex(webAuthnSigningMessage),
      authenticatorData: toHex(webAuthnAuthenticatorData),
      clientDataJson: toHex(webAuthnClientData),
      signature: toHex(webAuthnRawSignature),
      accountAuthenticator: toHex(toBcs(webAuthnAuthenticator)),
    },
    keyless: {
      jwt: keylessJwt,
      pepper: keylessPepper,
      issuer: keylessPublicKey.iss,
      idCommitment: toHex(keylessPublicKey.idCommitment),
      publicKey: keylessPublicKey.toString(),
      publicKeyBcs: toHex(toBcs(keylessPublicKey)),
      anyPublicKeyBcs: toHex(toBcs(keylessAnyPublicKey)),
      authenticationKey: keylessPublicKey.authKey().toString(),
      accountAddress: keylessAccount.accountAddress.toStringLong(),
      nonce: keylessEphemeral.nonce,
      ephemeralKeyPairBcs: toHex(toBcs(keylessEphemeral)),
      proofBcs: toHex(toBcs(keylessProof)),
      message: toHex(keylessMessage),
      signatureBcs: toHex(toBcs(keylessOfficialSignature)),
      anySignatureBcs: toHex(toBcs(keylessAnySignature)),
      accountSignatureBcs: toHex(toBcs(keylessAccountSignature)),
      accountBcs: toHex(toBcs(keylessAccount)),
      jwkBcs: toHex(toBcs(keylessJwk)),
      verificationKey: {
        alphaG1: "e2f26dbea299f5223b646cb1fb33eadb059d9407559d7441dfd902e3a79a4d2d",
        betaG2:
          "abb73dc17fbc13021e2471e0c08bd67d8401f52b73d6d07483794cad4778180e0c06f33bbc4c79a9cadef253a68084d382f17788f885c9afd176f7cb2f036789",
        deltaG2:
          "b106619932d0ef372c46909a2492e246d5de739aa140e27f2c71c0470662f125219049cfe15e4d140d7e4bb911284aad1cad19880efb86f2d9dd4b1bb344ef8f",
        gammaAbcG1: [
          "6123b6fea40de2a7e3595f9c35210da8a45a7e8c2f7da9eb4548e9210cfea81a",
          "32a9b8347c512483812ee922dc75952842f8f3083edb6fe8d5c3c07e1340b683",
        ],
        gammaG2:
          "edf692d95cbdde46ddda5ef7d422436779445c5e66006a42761e1f12efde0018c212f3aeb785e49712e7a9353349aaf1255dfb31b7bf60723a480d9293938e19",
        trainingWheelsPublicKey:
          "1388de358cf4701696bd58ed4b96e9d670cbbb914b888be1ceda6374a3098ed4",
      },
    },
  },
};

const absoluteOutput = resolve(outputPath);
await mkdir(dirname(absoluteOutput), { recursive: true });
await writeFile(absoluteOutput, `${JSON.stringify(fixture, null, 2)}\n`);
