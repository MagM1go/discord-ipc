package ru.magmigo.models

import kotlinx.serialization.Serializable
import ru.magmigo.models.enums.CommandType

@Serializable
public data class Command(val cmd: CommandType, val args: Args)