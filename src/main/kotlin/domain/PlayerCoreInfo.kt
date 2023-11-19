package domain

import kotlinx.serialization.Serializable

@Serializable
data class PlayerCoreInfo(
    val playerCode: String,
    val playerName: String
){
    fun toUrl(): String{
        return "https://www.premierleague.com/players/${playerCode}/${playerName}"
    }
}