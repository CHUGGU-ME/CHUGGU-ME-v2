import player.PlayerRepository
import common.PlaywrightUtil
import org.junit.jupiter.api.DisplayName
import org.mockito.Mock
import player.PlayerService
import java.io.Console
import kotlin.test.Test

class PlayerTest {

    val playerService: PlayerService = PlayerService(
        page = PlaywrightUtil.getNewPlayWrightPage(),
        playerRepository = PlayerRepository()
    )


    @DisplayName("손흥민 찾기")
    @Test
    fun `son`() {
        val searchedPlayer = playerService.searchPlayer("son")
        val chosedPlayer = searchedPlayer[0]
        val fullPlayerInfo = playerService.getFullPlayerInfo(chosedPlayer)
    }

}