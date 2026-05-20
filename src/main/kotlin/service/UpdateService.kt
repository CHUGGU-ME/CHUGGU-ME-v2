package service

import Repository.NewsRepository
import Repository.ScheduleRepository
import player.PlayerRepository
import common.PremierLeagueApi
import domain.Fixture
import domain.News
import player.domain.PlayerCoreInfo
import java.io.FileNotFoundException

class UpdateService(
    private val playerRepository: PlayerRepository,
    private val newsRepository: NewsRepository,
    private val scheduleRepository: ScheduleRepository,
) {

    private val currentSeasonId: Int by lazy { fetchCurrentSeasonId() }

    private fun fetchCurrentSeasonId(): Int {
        val json = PremierLeagueApi.getJson("/football/competitions/1/compseasons?page=0&pageSize=1")
        return json.getAsJsonArray("content")[0].asJsonObject.get("id").asInt
    }

    fun updatePlayer() {
        val seasonLabel = fetchSeasonLabel()

        val savedSeason = try {
            playerRepository.getPlayerSeason()
        } catch (e: Exception) {
            ""
        }
        if (seasonLabel == savedSeason) return
        playerRepository.savePlayerSeason(seasonLabel)
        updatePlayerCoreInfo()
    }

    private fun fetchSeasonLabel(): String {
        val json = PremierLeagueApi.getJson("/football/compseasons/$currentSeasonId")
        return json.get("label").asString
    }

    private fun updatePlayerCoreInfo() {
        val savePlayerCoreInfoList = mutableListOf<PlayerCoreInfo>()
        var page = 0

        while (true) {
            val json = PremierLeagueApi.getJson(
                "/football/players?pageSize=100&compSeasons=$currentSeasonId&altIds=true&page=$page&type=player&id=-1&compSeasonId=$currentSeasonId"
            )
            val content = json.getAsJsonArray("content")
            if (content.size() == 0) break

            content.forEach { element ->
                val player = element.asJsonObject
                val id = player.get("id").asLong.toString()
                val name = player.getAsJsonObject("name").get("display").asString
                savePlayerCoreInfoList.add(PlayerCoreInfo(playerCode = id, playerName = name))
            }

            val pageInfo = json.getAsJsonObject("pageInfo")
            val currentPage = pageInfo.get("page").asInt
            val numPages = pageInfo.get("numPages").asInt
            if (currentPage >= numPages - 1) break
            page++
        }

        playerRepository.savePlayerCoreInfoListWithSortByPlayerName(savePlayerCoreInfoList)
    }

    fun updateNews() {
        val content = PremierLeagueApi.getJsonArray(
            "/content/premierleague/text/EN?pageSize=10&type=article",
            "content"
        )

        val saveNewsList = mutableListOf<News>()
        content.forEachIndexed { index, element ->
            val article = element.asJsonObject
            val title = article.get("title").asString
            val slug = article.get("titleUrlSegment").asString
            val url = "https://www.premierleague.com/news/$slug"
            saveNewsList.add(News(no = index + 1, title = title, url = url))
        }

        newsRepository.saveNewsInfo(saveNewsList)
    }

    fun updateSchedule() {
        val content = PremierLeagueApi.getJsonArray(
            "/football/fixtures?compSeasons=$currentSeasonId&pageSize=20&sort=asc&statuses=U",
            "content"
        )

        val saveScheduleList = mutableListOf<Fixture>()
        content.forEachIndexed { index, element ->
            val fixture = element.asJsonObject
            val kickoff = fixture.getAsJsonObject("kickoff")
            val kickoffLabel = kickoff.get("label").asString  // "Sun 24 May 2026, 16:00 BST"
            val commaIdx = kickoffLabel.indexOf(", ")
            val date = if (commaIdx >= 0) kickoffLabel.substring(0, commaIdx) else kickoffLabel
            val timePart = if (commaIdx >= 0) kickoffLabel.substring(commaIdx + 2).trim() else ""
            val time = timePart.split(" ")[0]  // "16:00"

            val teams = fixture.getAsJsonArray("teams")
            val home = teams[0].asJsonObject.getAsJsonObject("team").get("shortName").asString
            val away = teams[1].asJsonObject.getAsJsonObject("team").get("shortName").asString
            val ground = fixture.getAsJsonObject("ground")
            val venue = ground.get("name").asString

            saveScheduleList.add(
                Fixture(
                    no = index + 1,
                    date = date,
                    time = time,
                    home = home,
                    away = away,
                    venue = venue
                )
            )
        }

        scheduleRepository.save(saveScheduleList)
    }
}
