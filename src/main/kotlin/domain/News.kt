package domain

import kotlinx.serialization.Serializable

@Serializable

data class News(
    val no: Int,
    val title: String,
    val url: String
)
