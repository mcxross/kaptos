// Copyright 2026 McXross
// SPDX-License-Identifier: Apache-2.0

import { mkdir, writeFile } from "node:fs/promises";
import { dirname, resolve } from "node:path";
import { ristretto255 } from "@noble/curves/ed25519.js";
import { H_RISTRETTO } from "./src/crypto/twistedEd25519.js";
import {
  APTOS_FRAMEWORK_ADDRESS,
  sigmaProtocolFiatShamir,
} from "./src/crypto/sigmaProtocol.js";
import { bcsSerializeRegistrationSession } from "./src/crypto/sigmaProtocolRegistration.js";

const outputPath = process.argv[2];
if (!outputPath) {
  throw new Error("Usage: generate-confidential-assets-fixtures.ts <output.json>");
}

const address = (lastByte: number) => {
  const result = new Uint8Array(32);
  result[31] = lastByte;
  return result;
};
const toHex = (bytes: Uint8Array) => Buffer.from(bytes).toString("hex");
const scalarToLittleEndianHex = (value: bigint) => {
  const bytes = new Uint8Array(32);
  let remaining = value;
  for (let index = 0; index < bytes.length; index += 1) {
    bytes[index] = Number(remaining & 0xffn);
    remaining >>= 8n;
  }
  return toHex(bytes);
};

const generator = ristretto255.Point.BASE;
const sender = address(7);
const asset = address(9);
const sessionId = bcsSerializeRegistrationSession(sender, asset);
const proofCommitment = generator.multiply(2n).toBytes();
const result = sigmaProtocolFiatShamir(
  {
    contractAddress: APTOS_FRAMEWORK_ADDRESS,
    chainId: 2,
    protocolId: new TextEncoder().encode("AptosConfidentialAsset/RegistrationV1"),
    sessionId,
  },
  "0x1::sigma_protocol_registration::Registration",
  {
    points: [H_RISTRETTO, generator],
    compressedPoints: [H_RISTRETTO.toBytes(), generator.toBytes()],
    scalars: [],
  },
  [proofCommitment],
  1,
);

const fixture = {
  baseline: {
    aptosTsSdkVersion: "7.3.1",
    aptosTsSdkCommit: "82b310449d7adf184540730ffdd754ada18d8ca6",
    confidentialAssetsSdkVersion: "2.0.0",
  },
  registrationFiatShamir: {
    chainId: 2,
    protocolId: "AptosConfidentialAsset/RegistrationV1",
    typeName: "0x1::sigma_protocol_registration::Registration",
    sender: toHex(sender),
    asset: toHex(asset),
    sessionId: toHex(sessionId),
    statement: [toHex(H_RISTRETTO.toBytes()), toHex(generator.toBytes())],
    proofCommitment: toHex(proofCommitment),
    witnessCount: 1,
    challengeScalarLe: scalarToLittleEndianHex(result.e),
  },
};

const absoluteOutput = resolve(outputPath);
await mkdir(dirname(absoluteOutput), { recursive: true });
await writeFile(absoluteOutput, `${JSON.stringify(fixture, null, 2)}\n`);
