package service

import Repository.NewsRepository
import player.PlayerRepository
import com.microsoft.playwright.ElementHandle
import com.microsoft.playwright.Page
import com.microsoft.playwright.options.LoadState
import common.PlaywrightUtil
import domain.News
import mom.MomRepository
import mom.domain.MomInfo
import player.domain.PlayerCoreInfo
import java.io.FileNotFoundException
import java.util.*

class UpdateService(
    private val page: Page,
    private val playerRepository: PlayerRepository,
    private val newsRepository: NewsRepository,
    private val momRepository: MomRepository,
) {

    private fun loadPlayersPage() {
        page.navigate("https://www.premierleague.com/players")
        PlaywrightUtil.firstStepOnPage(page)
        PlaywrightUtil.ignoreDownImage(page)
    }

    private fun updatePlayerCoreInfo() {
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

    fun updatePlayerSeasonInfo() {
        // TODO...
    }

    fun updatePlayer() {
        loadPlayersPage()
        page.waitForLoadState(LoadState.NETWORKIDLE)
        val pageSeason: String =
            page.querySelector("#mainContent > div.playerIndex > div.wrapper > div > section > div.dropDown.active > div.current")
                .innerText()

        lateinit var savedSeason: String
        try {
            savedSeason = playerRepository.getPlayerSeason()
        } catch (exception: FileNotFoundException) {
            savedSeason = ""
        }
        if (pageSeason.equals(savedSeason)) return
        playerRepository.savePlayerSeason(pageSeason)
        updatePlayerCoreInfo()
        updatePlayerSeasonInfo()
    }

    fun updateNews() {
        page.navigate("https://www.premierleague.com/news")
        page.waitForLoadState(LoadState.NETWORKIDLE)

        val newsList =
            page.querySelectorAll("#mainContent > section > div.wrapper.col-12 > ul > li ")
        val saveNewsList = mutableListOf<News>()
        var num = 1

        for (news in newsList) {
            val title = news.querySelector(".media-thumbnail__title").innerText()
            var url =
                page.querySelector("#mainContent > section > div.wrapper.col-12 > ul > li:nth-child($num) > a")
                    .getAttribute("href")

            url = url.replace("//", "https:")

            val saveNews = News(num, title, url)
            saveNewsList.add(saveNews)
            num += 1
        }

        newsRepository.saveNewsInfo(saveNewsList)
    }

    fun updateSchedule() {
        page.navigate("https://www.premierleague.com/fixtures")
        while (!page.isVisible("div.fixtures__date-container") && !page.isVisible(".match-fixture")) {
            page.waitForTimeout(100.0)
        }
    }

    fun updateMomInfo() {
        page.navigate("https://www.premierleague.com/man-of-the-match")
        page.waitForLoadState(LoadState.NETWORKIDLE)

        //val momList = page.querySelectorAll("li.kotm-match-list__row.js-kotm-row")
        val sMominfoList = mutableListOf<MomInfo>()
        val list = page.querySelectorAll(".kotm-match-list__row")
        while (!page.isVisible("li.kotm-match-list__row.js-kotm-row") && !page.isVisible(".kotm-match-list__row") && !page.isVisible(
                "onetrust-accept-btn-handler"
            )
        ) {
            page.waitForTimeout(100.0)
        }

        println("#list = ${list.size}")
//        val blockScreenElement = page.querySelector("#onetrust-accept-btn-handler")
//        blockScreenElement.click()

        list.forEach { momElement ->
            val dateElement = momElement.querySelector(".kotm-match-list__time--small")
            val matchElement = momElement.querySelector(".kotm-match-list__fixture")
            val playerNameElement = momElement.querySelector(".kotm-match-list__player-name")

            momElement.click()
            while (!page.isVisible("div.kotm-results__fixture-stats > div:nth-child(1) > span.kotm-results__fixture-stats-value.kotm-results__fixture-stats-value--bold")) {
                page.waitForTimeout(100.0)
            }
            val goalElement =
                page.querySelector("div.kotm-results__fixture-stats > div:nth-child(1) > span.kotm-results__fixture-stats-value.kotm-results__fixture-stats-value--bold")
            val assistElement =
                page.querySelector("div.kotm-results__fixture-stats > div:nth-child(2) > span.kotm-results__fixture-stats-value.kotm-results__fixture-stats-value--bold")
            sMominfoList.add(
                MomInfo(
                    matchDate = dateElement.innerText(),
                    match = matchElement.innerText(),
                    playerName = playerNameElement.innerText(),
                    goals = goalElement.innerText(),
                    assist = assistElement.innerText(),
                )
            )
            val closeElement = page.querySelector("#mainContent > section > div > button")
            closeElement.click()

        }
        momRepository.saveMomInfoList(sMominfoList.toList())
    }
}