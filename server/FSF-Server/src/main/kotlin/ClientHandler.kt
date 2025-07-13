package org.example

import kotlinx.coroutines.yield
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ClientHandler(val client : Socket){
    val input = BufferedReader(InputStreamReader(client.inputStream))
    val output= PrintWriter(client.outputStream,true)
    var room : Room? = null;
    fun handleWrite(){
        TODO()
    }
    suspend fun handleRead(){
        var message : String
        while (true){
            message = input.readLine()
            when (Commands.fromString(message)) {
                Commands.EXIT -> {

                }
                Commands.CONNECT -> {
                    joinRoom(message.split(Regex.fromLiteral(" "),1)[1])
                }
                null -> {

                }
            }
        }
    }
    private fun joinRoom(roomName : String){
        room = RoomManager.getOrCreateRoom(roomName, this)
    }
    private fun exitRoom() = room?.exit(this)
}