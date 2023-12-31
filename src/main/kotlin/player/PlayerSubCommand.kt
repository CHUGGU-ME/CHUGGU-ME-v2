package player

import com.microsoft.playwright.Page
import common.PlaywrightUtil
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class PlayerSubCommand : Subcommand("player", "Player info") {

    val playerName by argument(ArgType.String, description = "Player Name")

    lateinit var page: Page
    lateinit var playerService: PlayerService
    lateinit var playerInputView: PlayerInputView
    lateinit var playerOutputView: PlayerOutView


    private fun init() {
        page = PlaywrightUtil.getNewPlayWrightPage()
        playerInputView = PlayerInputView()
        playerOutputView = PlayerOutView()
        playerService = PlayerService(
            page = page,
            playerRepository = PlayerRepository()
        )
    }

    override fun execute() {
        init()
        val searchedPlayer = playerService.searchPlayer(playerName)
        val chosedPlayer = playerInputView.choosePlayer(searchedPlayer)
        val fullPlayerInfo = playerService.getPlayerInfo(chosedPlayer)
        playerOutputView.print(fullPlayerInfo)
    }
}