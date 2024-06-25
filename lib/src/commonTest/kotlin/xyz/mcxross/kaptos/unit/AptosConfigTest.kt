package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.expect
import xyz.mcxross.kaptos.model.AptosApiType
import xyz.mcxross.kaptos.model.AptosConfig
import xyz.mcxross.kaptos.model.AptosSettings
import xyz.mcxross.kaptos.model.Network
import xyz.mcxross.kaptos.util.NetworkToFaucetAPI
import xyz.mcxross.kaptos.util.NetworkToIndexerAPI
import xyz.mcxross.kaptos.util.NetworkToNodeAPI

class AptosConfigTest {

  // It should set urls based on a local network
  @Test
  fun aptosConfigLocalNetworkTest() {
    val settings = AptosSettings(network = Network.LOCAL)
    val aptosConfig = AptosConfig(settings)
    expect(Network.LOCAL) { aptosConfig.network }
    expect(aptosConfig.getRequestUrl(AptosApiType.FULLNODE)) {
      NetworkToNodeAPI[Network.LOCAL.name.lowercase()]
    }
    expect(aptosConfig.getRequestUrl(AptosApiType.FAUCET)) {
      NetworkToFaucetAPI[Network.LOCAL.name.lowercase()]
    }
    expect(aptosConfig.getRequestUrl(AptosApiType.INDEXER)) {
      NetworkToIndexerAPI[Network.LOCAL.name.lowercase()]
    }
  }

  // It should set urls based on a testnet
  @Test
  fun aptosConfigTestnetTest() {
    val settings = AptosSettings(network = Network.TESTNET)
    val aptosConfig = AptosConfig(settings)
    expect(Network.TESTNET) { aptosConfig.network }
    expect(aptosConfig.getRequestUrl(AptosApiType.FULLNODE)) {
      NetworkToNodeAPI[Network.TESTNET.name.lowercase()]
    }
    expect(aptosConfig.getRequestUrl(AptosApiType.FAUCET)) {
      NetworkToFaucetAPI[Network.TESTNET.name.lowercase()]
    }
    expect(aptosConfig.getRequestUrl(AptosApiType.INDEXER)) {
      NetworkToIndexerAPI[Network.TESTNET.name.lowercase()]
    }
  }

  // It should set urls based on a mainnet
  @Test
  fun aptosConfigMainnetTest() {
    val settings = AptosSettings(network = Network.MAINNET)
    val aptosConfig = AptosConfig(settings)
    expect(Network.MAINNET) { aptosConfig.network }
    expect(aptosConfig.getRequestUrl(AptosApiType.FULLNODE)) {
      NetworkToNodeAPI[Network.MAINNET.name.lowercase()]
    }
    expect(aptosConfig.getRequestUrl(AptosApiType.FAUCET)) {
      NetworkToFaucetAPI[Network.MAINNET.name.lowercase()]
    }
    expect(aptosConfig.getRequestUrl(AptosApiType.INDEXER)) {
      NetworkToIndexerAPI[Network.MAINNET.name.lowercase()]
    }
  }

  // It should set urls based on a devnet
  @Test
  fun aptosConfigDevnetTest() {
    val settings = AptosSettings(network = Network.DEVNET)
    val aptosConfig = AptosConfig(settings)
    expect(Network.DEVNET) { aptosConfig.network }
    expect(aptosConfig.getRequestUrl(AptosApiType.FULLNODE)) {
      NetworkToNodeAPI[Network.DEVNET.name.lowercase()]
    }
    expect(aptosConfig.getRequestUrl(AptosApiType.FAUCET)) {
      NetworkToFaucetAPI[Network.DEVNET.name.lowercase()]
    }
    expect(aptosConfig.getRequestUrl(AptosApiType.INDEXER)) {
      NetworkToIndexerAPI[Network.DEVNET.name.lowercase()]
    }
  }

  // It should have undefined urls when network is custom and no urls provided
  @Test
  fun aptosConfigCustomNetworkTest() {
    val settings = AptosSettings(network = Network.CUSTOM)
    val aptosConfig = AptosConfig(settings)
    expect(Network.CUSTOM) { aptosConfig.network }
    expect(aptosConfig.fullNode) { null }
    expect(aptosConfig.faucet) { null }
    expect(aptosConfig.indexer) { null }
  }

  // It should throw an error when network is custom and no urls provided
  @Test
  fun aptosConfigCustomNetworkNoSetUrlsTest() {
    val settings = AptosSettings(network = Network.CUSTOM)
    val aptosConfig = AptosConfig(settings)
    expect(Network.CUSTOM) { aptosConfig.network }
    assertFailsWith<Exception> { aptosConfig.getRequestUrl(AptosApiType.FULLNODE) }
    assertFailsWith<Exception> { aptosConfig.getRequestUrl(AptosApiType.FAUCET) }
    assertFailsWith<Exception> { aptosConfig.getRequestUrl(AptosApiType.INDEXER) }
  }

  @Test
  fun aptosConfigCustomNetworkSetUrlsTest() {
    val settings =
      AptosSettings(
        network = Network.CUSTOM,
        fullNode = "my-full-node",
        faucet = "my-faucet",
        indexer = "my-indexer",
      )

    val aptosConfig = AptosConfig(settings)

    expect(Network.CUSTOM) { aptosConfig.network }
    expect(aptosConfig.fullNode) { "my-full-node" }
    expect(aptosConfig.faucet) { "my-faucet" }
    expect(aptosConfig.indexer) { "my-indexer" }
  }
}
