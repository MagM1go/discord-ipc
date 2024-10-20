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
        public fun container(buttonContainer: ButtonContainer.() -> Unit) { this.buttonContainer = ButtonContainer().apply(buttonContainer) }
    }

    public class ButtonContainer {
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
            public abstract fun image(assetName: String)
            public abstract fun text(text: String)
        }

        public class Large : ImageBodyContainer() {
            override fun image(assetName: String) { largeImageAsset = assetName }

            override fun text(text: String) { largeImageText = text }
        }

        public class Small : ImageBodyContainer() {
            override fun image(assetName: String) { smallImageAsset = assetName }

            override fun text(text: String) { smallImageText = text }
        }

        public fun large(body: Large.() -> Unit): Large = Large().apply(body)
        public fun small(body: Small.() -> Unit): Small = Small().apply(body)
    }

    public fun ipc(): DiscordIPC = DiscordIPC
}

public fun discord(
    clientId: Long,
    body: Discord.Body.() -> Unit
): Discord = Discord(clientId, body)