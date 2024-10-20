package ru.magmigo.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class Assets(
    @SerialName("large_image") val largeAsset: String?,
    @SerialName("large_text") val largeText: String?,
    @SerialName("small_image") val smallAsset: String?,
    @SerialName("small_text") val smallText: String?
)
