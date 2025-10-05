package org.example

enum class Commands(val command: String) {
    EXIT("/EXIT"),
    LIST("/LIST"),
    JOIN("/JOIN");

    companion object {
        fun fromString(str: String): Commands? = entries.find { it.command == str.split(" ")[0] }
    }

}