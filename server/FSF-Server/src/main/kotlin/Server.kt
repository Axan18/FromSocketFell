package org.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.ServerSocket
import java.net.Socket

class Server(val port:Int){
    fun start() = runBlocking {
        val serverSocket = ServerSocket(port)

        while(true){
            val client = serverSocket.accept()
            handleClient(client)
        }
    }
    fun CoroutineScope.handleClient(client : Socket){
        launch {
            val handler = ClientHandler(client)
            handler.joinRoom()
            launch {
                handler.handleWrite()
            }
            launch {
                handler.handleRead()
            }
        }
    }
}