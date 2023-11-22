package domain

import com.microsoft.playwright.ElementHandle
import kotlinx.serialization.Serializable

@Serializable
data class Fixture(
    val no: Int,
    val date: String,
    val time: String,
    val home: String,
    val away: String,
    val venue: String
)