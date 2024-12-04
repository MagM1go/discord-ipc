# Discord RPC client.

СЫНИЩЕ БЛЯДИ ЕБАНОЙ, КАКУЮ ЖЕ ХУЙНЮ ТЫ ВЫСРАЛ
## Usage
```kotlin
val presence = discord(clientId) {
    container {
        button("label", "url")
        button("label_2", "url_2")
    }
    image {
        large("your_asset_name") {
            text("hu tao")
        }
        small("your_small_asset_name") {
            text("vo cha nou ba rou lin dau vene dip")
        }
    }
    state("State")
    details("Details")
}

presence.ipc().start(presence.clientId)
// Or you can start like this:
presence.start()
```

### To update presence, you simply need to unlock the cycle and send data.
```kotlin
presence.ipc().unlock()
presence.update(state = "new state")
```
