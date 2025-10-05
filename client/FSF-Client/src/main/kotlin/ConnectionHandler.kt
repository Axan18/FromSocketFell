package org.example

import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ConnectionHandler(client: Socket) {
  val input = client.openReadChannel()
  val output = client.openWriteChannel(autoFlush = true)

  suspend fun handleConnection() = coroutineScope {
    clearScreen()
    val readJob = launch {
      while (true) {
        val line = input.readUTF8Line() ?: break
        println(line)
      }
    }
    var message: String
    val writeJob = launch(Dispatchers.IO) {
      while (true) {
        message = readln()
        when (Commands.fromString(message)) {
          Commands.EXIT -> {
            clearScreen()
            break
          }

          Commands.JOIN -> {
            clearScreen()
            output.writeFully("$message\r\n".toByteArray())
            output.flush()
          }

          Commands.LIST -> {
            clearScreen()
            output.writeFully("$message\r\n".toByteArray())
            output.flush()
          }

          null -> { // regular message
            output.writeFully("$message\r\n".toByteArray())
            output.flush()
          }
        }
      }
    }
    try {
      writeJob.join()
    } finally {
      readJob.cancelAndJoin()
      try {
        output.flushAndClose()
      } catch (_: Throwable) {
      }
      try {
        input.cancel()
      } catch (_: Throwable) {
      }
    }
  }

  fun clearScreen() {
    print("\u001b[H\u001b[2J")
  }

}