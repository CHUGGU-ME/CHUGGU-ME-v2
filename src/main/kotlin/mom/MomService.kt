package mom

import com.microsoft.playwright.Page
import mom.domain.MomInfo

class MomService(
    private val page: Page,
    private val momRepository: MomRepository,
) {

    fun searchMomInfo(matchSeason: String, matchDate: String, matchTeam:String):MomInfo {
        println(matchSeason)
        println(matchDate)
        println(matchTeam)

        val momInfo: List<MomInfo> = momRepository.getMomInfoList()
         println("matchDate : " + momInfo[0].matchDate)
        println("fixture : " + momInfo[0].match)

        return MomInfo.of(page)
    }
}

        /*return momInfoList.filter {

            /*//TODO: matchSeason/matchDate/matchTeam
            *it.matchSeason.startsWith(
            *    playerName.uppercase
            *        Locale.getDefault()
            *    )


        }.toMutableList()*/