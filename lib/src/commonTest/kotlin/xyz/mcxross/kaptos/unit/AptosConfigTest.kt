package xyz.mcxross.kaptos.unit

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.expect
import xyz.mcxross.kaptos.model.AptosApiType
import xyz.mcxross.kaptos.model.TransportConfig
import xyz.mcxross.kaptos.model.AptosConfigurationException
import xyz.mcxross.kaptos.model.AptosError
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
    val aptosConfig = TransportConfig(settings)
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
    val aptosConfig = TransportConfig(settings)
    expect(Network.TESTNET) { aptosConfig.network }
    expect(aptosConfig.getRequestUrl(AptosApiType.FULLNODE)) {
      NetworkToNodeAPI[Network.TESTNET.name.lowercase()]
    }
    val error =
      assertFailsWith<AptosConfigurationException> {
        aptosConfig.getRequestUrl(AptosApiType.FAUCET)
      }
    assertIs<AptosError.UnsupportedFeature>(error.error)
    expect(aptosConfig.getRequestUrl(AptosApiType.INDEXER)) {
      NetworkToIndexerAPI[Network.TESTNET.name.lowercase()]
    }
  }

  // It should set urls based on a mainnet
  @Test
  fun aptosConfigMainnetTest() {
    val settings = AptosSettings(network = Network.MAINNET)
    val aptosConfig = TransportConfig(settings)
    expect(Network.MAINNET) { aptosConfig.network }
    expect(aptosConfig.getRequestUrl(AptosApiType.FULLNODE)) {
      NetworkToNodeAPI[Network.MAINNET.name.lowercase()]
    }
    val error =
      assertFailsWith<AptosConfigurationException> {
        aptosConfig.getRequestUrl(AptosApiType.FAUCET)
      }
    assertIs<AptosError.UnsupportedFeature>(error.error)
    expect(aptosConfig.getRequestUrl(AptosApiType.INDEXER)) {
      NetworkToIndexerAPI[Network.MAINNET.name.lowercase()]
    }
  }

  // It should set urls based on a devnet
  @Test
  fun aptosConfigDevnetTest() {
    val settings = AptosSettings(network = Network.DEVNET)
    val aptosConfig = TransportConfig(settings)
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

  // It should throw an error when network is custom and no urls provided
  @Test
  fun aptosConfigCustomNetworkNoSetUrlsTest() {
    val settings = AptosSettings(network = Network.CUSTOM)
    val aptosConfig = TransportConfig(settings)
    expect(Network.CUSTOM) { aptosConfig.network }
    assertFailsWith<Exception> { aptosConfig.getRequestUrl(AptosApiType.FULLNODE) }
    assertFailsWith<Exception> { aptosConfig.getRequestUrl(AptosApiType.FAUCET) }
    assertFailsWith<Exception> { aptosConfig.getRequestUrl(AptosApiType.INDEXER) }
  }

  @Test
  fun aptosConfigNewNetworksTest() {
    listOf(Network.SHELBYNET, Network.NETNA).forEach { network ->
      val aptosConfig = TransportConfig(AptosSettings(network = network))
      expect(NetworkToNodeAPI.getValue(network.name.lowercase())) {
        aptosConfig.getRequestUrl(AptosApiType.FULLNODE)
      }
      expect(NetworkToIndexerAPI.getValue(network.name.lowercase())) {
        aptosConfig.getRequestUrl(AptosApiType.INDEXER)
      }
      expect(NetworkToFaucetAPI.getValue(network.name.lowercase())) {
        aptosConfig.getRequestUrl(AptosApiType.FAUCET)
      }
      aptosConfig.close()
    }
  }
}
