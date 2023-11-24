package player

import com.microsoft.playwright.Page
import common.PlaywrightUtil
import player.domain.Player
import player.domain.PlayerCoreInfo
import java.util.*

class PlayerService(
    private val page: Page,
    private val playerRepository: PlayerRepository,
) {

    fun searchPlayer(playerName: String):MutableList<PlayerCoreInfo> {
        val playerCoreInfoList: MutableList<PlayerCoreInfo> = playerRepository.getPlayerCoreInfoList()
        return playerCoreInfoList.filter {
            it.playerName.startsWith(
                playerName.uppercase(
                    Locale.getDefault()
                )
            )
        }.toMutableList()
    }

    fun getPlayerInfo(playerCoreInfo: PlayerCoreInfo): Player{
        page.navigate(playerCoreInfo.toUrl())
        PlaywrightUtil.firstStepOnPage(page)
        PlaywrightUtil.ignoreDownImage(page)
        return Player.of(page)
    }
}