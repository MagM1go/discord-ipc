package ru.magmigo.server

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.magmigo.Application
import ru.magmigo.Discord
import ru.magmigo.models.Activity
import ru.magmigo.models.Args
import ru.magmigo.models.Assets
import ru.magmigo.models.Command
import ru.magmigo.models.enums.CommandType
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.time.Duration.Companion.seconds

public object DiscordIPC {
    private val locked = AtomicBoolean(false)

    private const val PORT: Int = 6463

    private val client = HttpClient(CIO) {
        install(WebSockets)
    }

    public fun lock(): Unit = locked.set(true)
    public fun unlock(): Unit = locked.set(false)

    internal suspend fun start(clientId: Long) {
        client.webSocket(
            method = HttpMethod.Get,
            host = Application.DISCORD_WEBSOCKET,
            port = PORT,
            path = Application.buildPath(clientId = clientId)
        ) {
            while (true) {
                if (!locked.get()) {
                    sendActivityMessage(this)
                    lock()
                }
            }
        }
    }

    private suspend fun sendActivityMessage(
        session: DefaultWebSocketSession
    ) = session.send(Frame.Text(Json.encodeToString(buildActivityCommand())))

    private fun buildActivityCommand(): Command =
        Command(CommandType.SET_ACTIVITY, Args(
            ProcessHandle.current().pid().toInt(),
            Activity(
                Discord.Body.state,
                Discord.Body.details,
                Discord.Body.buttonContainer?.buttons ?: listOf(),
                Assets(
                    Discord.ImageBody.largeImageAsset,
                    Discord.ImageBody.largeImageText,
                    Discord.ImageBody.smallImageAsset,
                    Discord.ImageBody.smallImageAsset
                )
            )
        ))
}