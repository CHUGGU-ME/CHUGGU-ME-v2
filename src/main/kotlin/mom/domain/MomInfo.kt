package mom.domain

import com.microsoft.playwright.Page
import kotlinx.serialization.Serializable

@Serializable
data class MomInfo(
    val matchDate: String,
    val match: String,
    val playerName: String,
    val goals: String,
    val assist: String,
){

    override fun toString(): String{
        val sb: StringBuilder = StringBuilder()
        sb.append("Man of the Match - ${playerName}\n")
        sb.append("goals: ${goals}\n")
        sb.append("assist: ${assist}\n")
        return sb.toString()
    }
}
