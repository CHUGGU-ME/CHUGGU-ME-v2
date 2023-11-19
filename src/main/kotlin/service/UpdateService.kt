package service

import Repository.NewsRepository
import Repository.PlayerRepository
import com.microsoft.playwright.ElementHandle
import com.microsoft.playwright.Page
import com.microsoft.playwright.options.LoadState
import common.PlaywrightUtil
import domain.News
import domain.PlayerCoreInfo
import java.io.FileNotFoundException
import java.util.*

class UpdateService(
    private val page: Page,
    private val playerRepository: PlayerRepository,
    private val newsRepository: NewsRepository,
) {

    private fun loadPlayersPage() {
        page.navigate("https://www.premierleague.com/players")
        PlaywrightUtil.firstStepOnPage(page)
        PlaywrightUtil.ignoreDownImage(page)
    }

    private fun updatePlayerCoreInfo(){
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
        playerRepository.savePlayerCoreInfoListWithSortByPlayerName(savePlayerCoreInfoList)
    }

    fun updatePlayerSeasonInfo(){
        // TODO...
    }

    fun updatePlayer() {
        loadPlayersPage()
        page.waitForLoadState(LoadState.NETWORKIDLE)
        val pageSeason: String = page.querySelector("#mainContent > div.playerIndex > div.wrapper > div > section > div.dropDown.active > div.current").innerText()

        lateinit var savedSeason: String
        try {
            savedSeason = playerRepository.getPlayerSeason()
        }catch (exception: FileNotFoundException){
            savedSeason = ""
        }
        if(pageSeason.equals(savedSeason)) return
        playerRepository.savePlayerSeason(pageSeason)
        updatePlayerCoreInfo()
        updatePlayerSeasonInfo()
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

        newsRepository.saveNewsInfo(saveNewsList)
    }
}