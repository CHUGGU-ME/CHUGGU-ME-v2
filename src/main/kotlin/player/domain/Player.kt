package player.domain

import com.google.gson.JsonObject
import common.PremierLeagueApi
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val firstName: String,
    val lastName: String,
    val number: String,
    val club: String,
    val position: String,
    val nationality: String,
    val dateOfBirth: String,
    val height: String,
    val score: String,
    val assist: String,
    val playerUrl: String,
) {

    companion object {
        fun of(playerCode: String): Player {
            val json = PremierLeagueApi.getJson("/football/players/$playerCode")

            val name = json.getAsJsonObject("name")
            val firstName = name.get("first")?.asString ?: ""
            val lastName = name.get("last")?.asString ?: ""

            val info = json.getAsJsonObject("info")
            val shirtNum = info?.get("shirtNum")?.asString ?: "-"
            val positionInfo = info?.get("positionInfo")?.asString ?: info?.get("position")?.asString ?: "-"

            val club = json.getAsJsonObject("currentTeam")
                ?.getAsJsonObject("club")
                ?.get("name")?.asString ?: "-"

            val nationality = json.getAsJsonObject("nationalTeam")
                ?.get("country")?.asString ?: "-"

            val birth = json.getAsJsonObject("birth")
                ?.getAsJsonObject("date")
                ?.get("label")?.asString ?: "-"

            val heightCm = json.get("height")?.asString ?: "-"

            val goals = json.get("goals")?.asString ?: "0"
            val assists = json.get("assists")?.asString ?: "0"

            return Player(
                firstName = firstName,
                lastName = lastName,
                number = shirtNum,
                club = club,
                position = positionInfo,
                nationality = nationality,
                dateOfBirth = birth,
                height = if (heightCm != "-") "${heightCm}cm" else "-",
                score = goals,
                assist = assists,
                playerUrl = "https://www.premierleague.com/players/$playerCode/${firstName}-${lastName}",
            )
        }
    }
}
