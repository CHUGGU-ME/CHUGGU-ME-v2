package subcommand

import Repository.PlayerRepository
import com.microsoft.playwright.Page
import common.PlaywrightUtil
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.PlayerService

@OptIn(ExperimentalCli::class)
class PlayerSubCommand : Subcommand("player", "Player info") {

    val playerName by argument(ArgType.String, description = "Player Name")
    lateinit var page: Page
    lateinit var playerService: PlayerService


    private fun init() {
        page = PlaywrightUtil.getNewPlayWrightPage()
        playerService = PlayerService(
            page = page,
            playerRepository = PlayerRepository()
        )
    }

    override fun execute() {
        init()
        playerService.printPlayer(playerName)
    }
}