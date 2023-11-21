package player.domain

import com.microsoft.playwright.Page

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
){

    companion object{
        fun of(page: Page): Player {
            return Player(
                firstName = page.querySelector(".player-header__name-first").innerText(),
                lastName = page.querySelector(".player-header__name-last").innerText(),
                number = page.querySelector("section > div.player-header__player-number.player-header__player-number--large").innerText(),
                position = page.querySelector("div.wrapper.hasFixedSidebar > nav > div > section:nth-child(1) > div:nth-child(3) > div.player-overview__info").innerText(),
                club = page.querySelector("div.wrapper.hasFixedSidebar > nav > div > section:nth-child(1) > div:nth-child(2) > div.player-overview__info").innerText(),
                nationality = page.querySelector(".player-info__player-country").innerText(),
                dateOfBirth = page.querySelector("div.wrapper.hasFixedSidebar > div > div > div.player-info.u-hide-mob > section > div > div:nth-child(2) > div.player-info__info").innerText(),
                height = page.querySelector("div.wrapper.hasFixedSidebar > div > div > div.player-info.u-hide-mob > section > div > div:nth-child(3) > div.player-info__info").innerText(),
                score = page.querySelector("div.wrapper.hasFixedSidebar > nav > div > section:nth-child(2) > div > div:nth-child(2) > div.player-overview__info").innerText(),
                assist = page.querySelector("div.wrapper.hasFixedSidebar > nav > div > section:nth-child(2) > div > div:nth-child(3) > div.player-overview__info").innerText(),
                playerUrl = page.url(),
            )
        }
    }

    override fun toString(): String{
        val sb: StringBuilder = StringBuilder()
        sb.append("name: ${firstName} ${lastName}\n")
        sb.append("club: ${club}\n")
        sb.append("position: ${position}\n")
        sb.append("number: ${number}\n")
        sb.append("height: ${height}\n")
        sb.append("date of birth: ${dateOfBirth}\n")
        sb.append("nationality: ${nationality}\n")
        sb.append("score: ${score}\n")
        sb.append("assist: ${assist}\n")

        sb.append("\n${playerUrl}")
        return sb.toString()
    }
}
