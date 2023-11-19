package domain

import kotlinx.serialization.Serializable

@Serializable

data class ManOfTheMatchInfo(
    val date: String,
    val fixture: String,
    val mom: String
)
