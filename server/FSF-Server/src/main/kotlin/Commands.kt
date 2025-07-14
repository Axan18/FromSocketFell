package org.example

enum class Commands(val command: String) {
    EXIT("/EXIT"),
    CONNECT("/CONNECT");

    companion object {
        fun fromString(str: String): Commands? = entries.find { it.command == str }
    }

}