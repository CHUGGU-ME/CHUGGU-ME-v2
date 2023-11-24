package mom

import com.microsoft.playwright.Page
import mom.domain.MomInfo

class MomService(
    private val page: Page,
    private val momRepository: MomRepository,
) {

    fun searchMomInfo(matchSeason: String, matchDate: String, matchTeam: String): MomInfo? {
        println(matchSeason)
        println(matchDate)
        println(matchTeam)

        val momInfos = momRepository.getMomInfoList()
        val foundMomInfo =
            momInfos.find { momInfo -> momInfo.matchDate == matchDate && momInfo.match.contains(matchTeam) }
        return foundMomInfo
    }
}

/*return momInfoList.filter {

    /*//TODO: matchSeason/matchDate/matchTeam
    *it.matchSeason.startsWith(
    *    playerName.uppercase
    *        Locale.getDefault()
    *    )


}.toMutableList()*/