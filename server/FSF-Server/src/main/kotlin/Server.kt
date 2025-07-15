package org.example

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.*


class Server(val address: String, val port: Int) {
    private val selectorManager = SelectorManager(Dispatchers.IO + SupervisorJob())

    suspend fun start() = coroutineScope {
        val serverSocket = aSocket(selectorManager).tcp().bind(InetSocketAddress(address, port))

        while (true) {
            val client = serverSocket.accept()
            launch(selectorManager.coroutineContext) {
                handleClient(client)
            }
        }
    }

    suspend fun handleClient(client: Socket) {
        try {
            val handler = ClientHandler(client)
            handler.handleConnection()
        } finally {
            client.close()
        }
    }
}