import common.PlaywrightUtil
import org.junit.jupiter.api.DisplayName
import player.PlayerOutView
import player.PlayerRepository
import player.PlayerService
import kotlin.test.Test

class PlayerTest {

    val playerService: PlayerService = PlayerService(
        page = PlaywrightUtil.getNewPlayWrightPage(),
        playerRepository = PlayerRepository()
    )

    val playerOutView: PlayerOutView = PlayerOutView()


    @DisplayName("손흥민 찾기")
    @Test
    fun `son`() {
        val searchedPlayer = playerService.searchPlayer("son")
        val chosedPlayer = searchedPlayer[0]
        val fullPlayerInfo = playerService.getPlayerInfo(chosedPlayer)
        playerOutView.print(fullPlayerInfo)

    }

}