package org.example

import java.util.concurrent.ConcurrentHashMap

class Room (val name:String){
    private val clients = ConcurrentHashMap.newKeySet<ClientHandler>()
    fun add(client : ClientHandler) = clients.add(client)
}