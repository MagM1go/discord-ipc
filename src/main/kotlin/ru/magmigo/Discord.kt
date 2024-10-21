package ru.magmigo

import ru.magmigo.models.Button
import ru.magmigo.server.DiscordIPC

public class Discord(public val clientId: Long, body: Body.() -> Unit) {
    init { apply { body(Body) } }

    public object Body {
        internal var state: String? = null
        internal var details: String? = null
        internal var buttonContainer: ButtonContainer? = null

        public fun state(text: String) { state = text }
        public fun details(text: String) { details = text }
        public fun image(container: ImageBody.() -> Unit) { ImageBody.apply(container) }
        public fun container(buttonContainer: ButtonContainer.() -> Unit) { this.buttonContainer = ButtonContainer.apply(buttonContainer) }
    }

    public object ButtonContainer {
        internal val buttons: MutableList<Button> = mutableListOf()

        public fun button(text: String, url: String): Boolean =
            buttons.add(Button(text, url))
    }

    public object ImageBody {
        internal var largeImageAsset: String? = null
        internal var largeImageText: String? = null
        internal var smallImageAsset: String? = null
        internal var smallImageText: String? = null

        public abstract class ImageBodyContainer {
            public abstract var assetName: String

            public abstract fun text(text: String)
        }

        public class Large(private val largeAssetName: String) : ImageBodyContainer() {
            override var assetName: String
                get() = largeAssetName
                set(value) { largeImageAsset = value }

            override fun text(text: String) { largeImageText = text }
        }

        public class Small(private val smallAssetName: String) : ImageBodyContainer() {
            override var assetName: String
                get() = this.smallAssetName
                set(value) { smallImageAsset = value }

            override fun text(text: String) { smallImageText = text }
        }

        public fun large(
            assetName: String,
            body: Large.() -> Unit
        ): Large = Large(assetName).apply(body)
        public fun small(
            assetName: String,
            body: Small.() -> Unit
        ): Small = Small(assetName).apply(body)
    }

    public fun ipc(): DiscordIPC = DiscordIPC

    public suspend fun start(): Unit = ipc().start(clientId)

    public fun update(state: String) { Body.state = state }

    public fun update(state: String, details: String) {
        update(state)
        Body.details = details
    }

    public fun update(
        largeImageAsset: String,
        largeImageText: String,
        smallImageAsset: String,
        smallImageText: String
    ) {
        ImageBody.largeImageAsset = largeImageAsset
        ImageBody.smallImageAsset = smallImageAsset
        ImageBody.smallImageText = smallImageText
        ImageBody.largeImageText = largeImageText
    }

    public fun update(buttonContainer: ButtonContainer.() -> Unit): Unit =
        Body.container(buttonContainer)
}

public fun discord(
    clientId: Long,
    body: Discord.Body.() -> Unit
): Discord = Discord(clientId, body)