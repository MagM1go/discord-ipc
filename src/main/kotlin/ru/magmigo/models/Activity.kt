package ru.magmigo.models

import kotlinx.serialization.Serializable

@Serializable
public data class Activity(
    val state: String?,
    val details: String?,
    val buttons: List<Button>?,
    val assets: Assets?
)