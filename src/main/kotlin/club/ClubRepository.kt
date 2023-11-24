package club

import club.domain.Club
import club.domain.ClubCoreInfo
import common.FileName
import common.readFromFile
import common.saveToBin

class ClubRepository {

    fun getClubSeason(): String {
        return readFromFile<String>(FileName.CLUB_SEASON.fileName)
    }

    fun saveClubSeason(season: String) {
        saveToBin(season, FileName.CLUB_SEASON.fileName)
    }


    fun getClubList(): MutableList<Club> {
        return readFromFile<MutableList<Club>>(FileName.CLUB_LIST.fileName)
    }

    fun saveClubList(saveClubList: MutableList<Club>) {
        saveToBin(saveClubList, FileName.CLUB_LIST.fileName)
    }

    fun getClubCoreInfoList(): MutableList<ClubCoreInfo> {
        return readFromFile<MutableList<ClubCoreInfo>>(FileName.CLUB_CORE_INFO_LIST.fileName)
    }

    fun saveClubCoreInfoListWithSortByClubName(saveClubCoreInfoList: MutableList<ClubCoreInfo>) {
        saveToBin(
            saveClubCoreInfoList.sortedWith(
                compareBy({ it.clubName },
                    { it.clubName })
            ), FileName.CLUB_CORE_INFO_LIST.fileName
        )
    }

}