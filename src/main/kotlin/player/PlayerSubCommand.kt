package player

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class PlayerSubCommand : Subcommand("player", "Player info") {

    val playerName by argument(ArgType.String, description = "Player Name")

    override fun execute() {
        val playerInputView = PlayerInputView()
        val playerOutputView = PlayerOutView()
        val playerService = PlayerService(
            playerRepository = PlayerRepository()
        )

        val searchedPlayer = playerService.searchPlayer(playerName)
        val chosedPlayer = playerInputView.choosePlayer(searchedPlayer)
        val fullPlayerInfo = playerService.getPlayerInfo(chosedPlayer)
        playerOutputView.print(fullPlayerInfo)
    }
}
