package org.example

import java.util.concurrent.ConcurrentHashMap

object RoomManager {
    var rooms = ConcurrentHashMap<String, Room>()
    fun getOrCreateRoom(name: String, client: ClientHandler): Room =
        rooms.computeIfAbsent(name) {
            Room(name)
        }.apply {
            add(client)
        }
}