package ru.magmigo.models

import kotlinx.serialization.Serializable

@Serializable
public data class Args(val pid: Int, val activity: Activity)