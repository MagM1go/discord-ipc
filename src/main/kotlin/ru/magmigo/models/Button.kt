package ru.magmigo.models

import kotlinx.serialization.Serializable

@Serializable
public data class Button(val label: String, val url: String)
