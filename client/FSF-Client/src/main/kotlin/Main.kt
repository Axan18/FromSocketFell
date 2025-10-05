package org.example

import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
  while (true) {
    print("\u001b[H\u001b[2J")
    println("Insert IP address and port of the server you want to connect to:")
    print("Address: (x.x.x.x)")
    val addr = readln()
    print("Port: (x)")
    try {
      val port = readln().toInt()
      val socket = Connector.connect(addr, port)
      ConnectionHandler(socket).handleConnection()
    } catch (_: NumberFormatException) {
      println("Invalid port number")
      println("Press any key to try again...")
      if (readln().isNotEmpty()) continue
      else break
    } catch (e: Exception) {
      println(
        "Something went wrong. Make sure you entered the correct IP address and port number.\n" +
                "Exception: ${e.message}"
      )
    }
  }
}