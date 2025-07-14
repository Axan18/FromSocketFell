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
            selectorManager.handleClient(client)
        }
    }

    fun CoroutineScope.handleClient(client: Socket) {
        launch {
            try {
                val handler = ClientHandler(client)
                coroutineScope {
                    launch {
                        handler.handleWrite()
                    }
                    launch {
                        handler.handleRead()
                    }
                }
            } finally {
                client.close()
            }
        }
    }
}