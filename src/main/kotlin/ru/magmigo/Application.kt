package ru.magmigo

internal object Application {
    const val DISCORD_WEBSOCKET = "127.0.0.1"

    internal fun buildPath(
        version: Int = 1,
        clientId: Long,
        encoding: Encoding = Encoding.Json
    ) = "/?version=$version&client_id=$clientId&${encoding.name.lowercase()}"

    internal enum class Encoding {
        Json,
        Etf
    }
}