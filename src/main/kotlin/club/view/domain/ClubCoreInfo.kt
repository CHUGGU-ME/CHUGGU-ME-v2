package club.domain

import com.microsoft.playwright.ElementHandle
import com.microsoft.playwright.Page
import kotlinx.serialization.Serializable

@Serializable
data class ClubCoreInfo(
    val clubCode: String,
    val clubName: String
){
    fun toUrl(): String{
        return "https://www.premierleague.com/clubs/${clubCode}/${clubName}"
    }
}