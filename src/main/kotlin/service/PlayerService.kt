package service

import Repository.PlayerRepository
import com.microsoft.playwright.Page
import common.PlaywrightUtil
import domain.Player
import java.util.*

class PlayerService(
    private val page: Page,
    private val playerRepository: PlayerRepository,
) {
    fun printPlayer(playerName: String) {
        val playerCoreInfoList = playerRepository.getPlayerCoreInfoList()
        val filteredPlayerCoreInfo = playerCoreInfoList.filter {
            it.playerName.startsWith(
                playerName.uppercase(
                    Locale.getDefault()
                )
            )
        }

        for (idx in 0..filteredPlayerCoreInfo.lastIndex) {
            println("${idx + 1} : ${filteredPlayerCoreInfo[idx].playerName}")
        }

        var number: Int

        while (true) {
            println("Enter the number of the Player: ")
            val input = readLine()
            if (input == "end") {
                break
            }

            try {
                number = Integer.parseInt(input)
            } catch (e: TypeCastException) {
                println("Wrong input")
                continue
            }

            if (!(1 <= number && number <= filteredPlayerCoreInfo.size)) {
                println("Enter 1 ~ ${filteredPlayerCoreInfo.size}")
            }

            page.navigate(filteredPlayerCoreInfo[number - 1].toUrl())
            PlaywrightUtil.firstStepOnPage(page)
            PlaywrightUtil.ignoreDownImage(page)

            println(Player.of(page).toString())
            println(page.url())
            break
        }
    }
}