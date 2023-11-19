package Repository

import common.FileName
import common.readFromFile
import common.saveToBin
import domain.Player
import domain.PlayerCoreInfo

class PlayerRepository {

    fun getPlayerSeason(): String {
        return readFromFile<String>(FileName.PLAYER_SEASON.fileName)
    }

    fun savePlayerSeason(season: String) {
        saveToBin(season, FileName.PLAYER_SEASON.fileName)
    }


    fun getPlayerList(): MutableList<Player> {
        return readFromFile<MutableList<Player>>(FileName.PLAYER_LIST.fileName)
    }

    fun savePlayerList(savePlayerList: MutableList<Player>) {
        saveToBin(savePlayerList, FileName.PLAYER_LIST.fileName)
    }

    fun getPlayerCoreInfoList(): MutableList<PlayerCoreInfo> {
        return readFromFile<MutableList<PlayerCoreInfo>>(FileName.PLAYER_CORE_INFO_LIST.fileName)
    }

    fun savePlayerCoreInfoListWithSortByPlayerName(savePlayerCoreInfoList: MutableList<PlayerCoreInfo>) {
        saveToBin(
            savePlayerCoreInfoList.sortedWith(
                compareBy({ it.playerName },
                    { it.playerName })
            ), FileName.PLAYER_CORE_INFO_LIST.fileName
        )
    }
}