package mom

import mom.domain.MomInfo

class MomService(
    private val momRepository: MomRepository,
) {

    fun searchMomInfo(matchSeason: String, matchDate: String, matchTeam: String): MomInfo? {
        val momInfos = momRepository.getMomInfoList()
        return momInfos.find { momInfo -> momInfo.matchDate == matchDate && momInfo.match.contains(matchTeam) }
    }
}
