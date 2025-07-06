package org.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class ClientHandler(val client : Socket){
    val input = BufferedReader(InputStreamReader(client.inputStream))
    val output= PrintWriter(client.outputStream,true)
    fun joinRoom(){
        val roomName = input.readLine()
        RoomManager.getOrCreateRoom(roomName, this)
    }
    fun handleWrite(){
        TODO()
    }
    fun handleRead(){
        TODO()
    }
}