package org.example

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withTimeout

object Connector {
  private val selectorManager = SelectorManager(Dispatchers.IO)
  suspend fun connect(address: String, port: Int): Socket =
    withTimeout(3000){
      aSocket(selectorManager).tcp().connect(address, port)
    }
}