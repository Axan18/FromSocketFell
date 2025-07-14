package org.example

import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.readUTF8Line


class ClientHandler(client: Socket) {
    val input = client.openReadChannel()
    val output = client.openWriteChannel(autoFlush = true)
    var room: Room? = null;
    fun handleWrite() {
        TODO()
    }

    suspend fun handleRead() {
        var message: String
        while (true) {
            message = input.readUTF8Line() ?: break
            when (Commands.fromString(message)) {
                Commands.EXIT -> {

                }

                Commands.CONNECT -> {
                    joinRoom(message.split(Regex.fromLiteral(" "), 1)[1])
                }

                null -> {

                }
            }
        }
    }

    private fun joinRoom(roomName: String) {
        room = RoomManager.getOrCreateRoom(roomName, this)
    }

    private fun exitRoom() = room?.exit(this)
}