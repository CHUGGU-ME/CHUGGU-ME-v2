package mom.domain

import com.microsoft.playwright.Page

data class MomInfo(
    val matchDate: String,
    val match: String,
    val playerName: String,
    val goals: String,
    val assist: String,
){

    companion object{
        fun of(page: Page): MomInfo {
            return MomInfo(
                matchDate = page.querySelector(".kotm-match-list__time--small").innerText(),
                match = page.querySelector(".kotm-match-list__fixture").innerText().replace('\n',' '),
                playerName = page.querySelector(".kotm-match-list__player-name").innerText(),
                goals = page.querySelector("#mainContent > section > div > div.kotm-results.js-kotm-results.bg-t3 > div.kotm-results__results-column > div.tabbedContent > div.js-fixture-stats-container.kotmModalTab.active > div > div.kotm-results__fixture-stats > div:nth-child(1) > span.kotm-results__fixture-stats-value.kotm-results__fixture-stats-value--bold").innerText(),
                assist = page.querySelector("#mainContent > section > div > div.kotm-results.js-kotm-results.bg-t3 > div.kotm-results__results-column > div.tabbedContent > div.js-fixture-stats-container.kotmModalTab.active > div > div.kotm-results__fixture-stats > div:nth-child(2) > span.kotm-results__fixture-stats-value.kotm-results__fixture-stats-value--bold").innerText(),
            )
        }
    }

    override fun toString(): String{
        val sb: StringBuilder = StringBuilder()
        sb.append("Man of the Match - ${playerName}\n")
        sb.append("goals: ${goals}\n")
        sb.append("assist: ${assist}\n")
        return sb.toString()
    }
}
