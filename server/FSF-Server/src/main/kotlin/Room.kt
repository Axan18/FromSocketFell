package org.example

import io.ktor.utils.io.writeFully
import java.util.concurrent.ConcurrentHashMap

class Room(val name: String) {
    private val clients = ConcurrentHashMap.newKeySet<ClientHandler>()
    fun add(client: ClientHandler) = clients.add(client)
    fun exit(client: ClientHandler) {
        clients.remove(client)

        if(clients.isEmpty()){
            RoomManager.rooms.remove(name)
        }
    }

    suspend fun broadcast(message: String, except : ClientHandler) {
        for (client in clients) {
            if(client != except){
                client.output.writeFully("$message\n".toByteArray())
            }
        }
    }
}