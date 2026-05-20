package player

import player.domain.Player
import player.domain.PlayerCoreInfo
import java.util.*

class PlayerService(
    private val playerRepository: PlayerRepository,
) {

    fun searchPlayer(playerName: String): MutableList<PlayerCoreInfo> {
        val playerCoreInfoList: MutableList<PlayerCoreInfo> = playerRepository.getPlayerCoreInfoList()
        val query = playerName.uppercase(Locale.getDefault())
        return playerCoreInfoList.filter {
            it.playerName.uppercase(Locale.getDefault()).contains(query)
        }.toMutableList()
    }

    fun getPlayerInfo(playerCoreInfo: PlayerCoreInfo): Player {
        return Player.of(playerCoreInfo.playerCode)
    }
}
