package org.example

import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import io.ktor.utils.io.readUTF8Line
import io.ktor.utils.io.writeFully


class ClientHandler(client: Socket) {
    val input = client.openReadChannel()
    val output = client.openWriteChannel(autoFlush = true)
    var room: Room? = null

    suspend fun handleConnection() {
        var message: String
        output.writeFully("Welcome to the app\n".toByteArray())
        output.writeFully("Available commands:\n/LIST list of rooms you can join\n/JOIN <room name> join/create room\n/EXIT exit the room you are currently in\n".toByteArray())
        while (true) {
            message = input.readUTF8Line() ?: break
            when (Commands.fromString(message)) {
                Commands.EXIT -> {
                    output.writeFully("Exiting room ${room?.name}\n".toByteArray())
                    println("EXIT")
                    exitRoom()
                }

                Commands.JOIN -> {
                    output.writeFully("Joining...\n".toByteArray())
                    println("JOIN")
                    joinRoom(message.split(Regex.fromLiteral(" "), 2)[1])
                }
                Commands.LIST -> {
                    output.writeFully("Rooms:\n${RoomManager.rooms.keys.joinToString(",\n")}------".toByteArray())
                    println("LIST")
                }
                null -> { // regular message
                    room?.broadcast(message, this)
                    println("Message: $message")
                }
            }
        }
    }

    private fun joinRoom(roomName: String) {
        room = RoomManager.getOrCreateRoom(roomName, this)

    }

    private fun exitRoom() {
        room?.exit(this)
        room = null
    }
}