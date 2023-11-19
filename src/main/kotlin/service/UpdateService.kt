package service

import com.microsoft.playwright.ElementHandle
import com.microsoft.playwright.Page
import com.microsoft.playwright.Route
import com.microsoft.playwright.options.LoadState
import common.FileName
import common.PlaywrightUtil
import common.readFromFile
import common.saveToBin
import domain.News
import domain.PlayerCoreInfo
import java.io.FileNotFoundException
import java.util.*

class UpdateService(
    val page: Page
) {

    private fun loadPlayersPage() {
        page.navigate("https://www.premierleague.com/players")
        PlaywrightUtil.firstStepOnPage(page)
        PlaywrightUtil.ignoreDownImage(page)
    }

    fun updatePlayers() {
        loadPlayersPage()
        page.waitForLoadState(LoadState.NETWORKIDLE)
        val pageSeason: String = page.querySelector("#mainContent > div.playerIndex > div.wrapper > div > section > div.dropDown.active > div.current").innerText()

        lateinit var savedSeason: String
        try {
            savedSeason  = readFromFile<String>(FileName.PLAYER_SEASON.fileName)
        }catch (exception: FileNotFoundException){
            savedSeason = ""
        }
        if(pageSeason.equals(savedSeason)) return
        saveToBin(pageSeason, FileName.PLAYER_SEASON.fileName)


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
        saveToBin(
            savePlayerCoreInfoList.sortedWith(
                compareBy({ it.playerName },
                    { it.playerName })
            ), FileName.PLAYER_CORE_INFO_LIST.fileName
        )
    }

    fun updateNews() {
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
}