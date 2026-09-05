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

  },
};

const absoluteOutput = resolve(outputPath);
await mkdir(dirname(absoluteOutput), { recursive: true });
await writeFile(absoluteOutput, `${JSON.stringify(fixture, null, 2)}\n`);
