package subcommand

import com.microsoft.playwright.ElementHandle
import com.microsoft.playwright.Route
import com.microsoft.playwright.options.LoadState
import common.FileName
import common.PlaywrightUtil
import common.saveToBin
import domain.ManOfTheMatchInfo
import domain.News
import domain.PlayerCoreInfo
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import java.util.*

@OptIn(ExperimentalCli::class)
class UpdateSubCommand : Subcommand("update", "Update Data") {


    private val playwright = PlaywrightUtil()
    private val page = playwright.playWrightUp()

    fun looadPlayersPage() {
        page.navigate("https://www.premierleague.com/players")
        page.waitForLoadState(LoadState.LOAD)
        if (page.querySelector("#onetrust-banner-sdk > div").isVisible) {
            page.querySelector("#onetrust-accept-btn-handler").click()
        }
        if(page.querySelector("#advertClose").isVisible){
            page.querySelector("#advertClose").click()
        }
        page.route("**/*.{png,jpg,jpeg}") { route: Route -> route.abort() }
    }


    override fun execute() {
        updatePlayers()
        updateNews()
    }

    private fun updatePlayers() {
        looadPlayersPage()

        page.keyboard().press("End")
        page.waitForTimeout(30000.0)

        val players: List<ElementHandle> = page.querySelectorAll("tr.player")

        val savePlayerCoreInfoList = mutableListOf<PlayerCoreInfo>()

        players.forEach {
            val urlInfo: String = it.querySelector("a.player__name").getAttribute("href")
                .split("players/")[1].replace("/overview", "")

            savePlayerCoreInfoList.add(
                PlayerCoreInfo(
                    playerCode = urlInfo.split("/")[0],
                    playerName = urlInfo.split("/")[1].uppercase(Locale.getDefault())
                )
            )
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
        saveToBin(saveNewsList, FileName.NEWS_LIST.fileName)
    }

    private fun updateMomInfo() {
        /*
         * TODO:다 가져와서 리스트업.
         * TODO:date,fexture,mom 제대로 가져오기(지금은 엉터리임)
         * TODO:num 도 의미있게개수새는거 가져오기.
         */

        page.navigate("https://www.premierleague.com/man-of-the-match")
        page.waitForLoadState(LoadState.NETWORKIDLE)

        val momList = page.querySelectorAll("#mainContent > div.tabbedContent")
        val saveMomList = mutableListOf<ManOfTheMatchInfo>()
        var num = 1

        for (manOfTheMatchInfo in momList) {
            val date =  page.querySelector("li:nth-child($num) > a").getAttribute("href")
            val fexture =  page.querySelector("li:nth-child($num) > a").getAttribute("href")
            val mom =  page.querySelector("li:nth-child($num) > a").getAttribute("href")

            val saveMom = ManOfTheMatchInfo(date, fexture, mom)
            saveMomList.add(saveMom)
            num += 1
        }
        saveToBin(saveMomList, FileName.MAN_OF_THE_MATCH_INFO_LIST.fileName)
    }
}