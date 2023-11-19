package subcommand

import com.microsoft.playwright.ElementHandle
import com.microsoft.playwright.Page
import com.microsoft.playwright.Route
import com.microsoft.playwright.options.LoadState
import common.PlaywrightUtil
import common.saveToBin
import domain.News
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import java.util.*

@OptIn(ExperimentalCli::class)
class UpdateSubCommand : Subcommand("update", "Update Data") {

    val page: Page = PlaywrightUtil.page

    fun looadPlayersPage() {
        page.navigate("https://www.premierleague.com/players")
        page.waitForLoadState(LoadState.LOAD)
        if (page.querySelector("#onetrust-banner-sdk > div").isVisible) {
            page.querySelector("#onetrust-accept-btn-handler").click()
        }
        page.waitForLoadState(LoadState.NETWORKIDLE)
        page.route("**/*.{png,jpg,jpeg}") { route: Route -> route.abort() }
    }


    override fun execute() {
        looadPlayersPage()

        page.keyboard().press("End")
        page.waitForTimeout(5000.0)

        val players: List<ElementHandle> = page.querySelectorAll("tr.player")

        val savePlayerData = linkedMapOf<String, String>()

        players.forEach {
            val urlInfo: String = it.querySelector("a.player__name").getAttribute("href").split("players/")[1].replace("/overview", "")

            val keyword = urlInfo.split("/")[1].uppercase(Locale.getDefault())
            val urlData: String = urlInfo
            savePlayerData[keyword] = urlData

        }

        saveToBin(savePlayerCoreInfoList, FileName.PLAYER_CORE_INFO_LIST.fileName)
    }

    private fun updateNews() {
        page.navigate("https://www.premierleague.com/news")
        page.waitForLoadState(LoadState.NETWORKIDLE)

        val newsList = page.querySelectorAll("#mainContent > section > div.wrapper.col-12 > ul > li ")
        val saveNewsList = mutableListOf<News>()
        var num = 1

        for (news in newsList) {
            val title = news.querySelector(".media-thumbnail__title").innerText()
            var url = page.querySelector("#mainContent > section > div.wrapper.col-12 > ul > li:nth-child($num) > a").getAttribute("href")

            url = url.replace("//", "https:")

            val saveNews = News(num, title, url)
            saveNewsList.add(saveNews)
            num += 1
        }
        saveToBin(saveNewsList, "newsList")
    }
}