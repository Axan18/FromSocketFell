package org.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.ServerSocket
import java.net.Socket

class Server(val port: Int) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    fun start() = {
        val serverSocket = ServerSocket(port)

        while (true) {
            val client = serverSocket.accept()
            scope.handleClient(client)
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