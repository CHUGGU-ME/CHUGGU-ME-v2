package subcommand

import com.microsoft.playwright.*
import com.microsoft.playwright.options.LoadState
import domain.Player
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class PlayerSubCommand : Subcommand("player", "Player info") {

    /**
     * 아래 두 줄 관련된 playwrigt에서 크롤링 하는 로직은
     * UpdateSubCommand 쪽에서 처리 되도록 리팩토링 부탁드립니다.
     */
    private val playwright = PlaywrightUtil()
    private val page = playwright.playWrightUp()

    val playerName by argument(ArgType.String, description = "Player Name")

    fun loadPlayersPage() {
        page.navigate("https://www.premierleague.com/players")
        page.waitForLoadState(LoadState.LOAD)
        if (page.querySelector("#onetrust-banner-sdk > div").isVisible) {
            page.querySelector("#onetrust-accept-btn-handler").click()
        }
        page.waitForLoadState(LoadState.NETWORKIDLE)
        page.route("**/*.{png,jpg,jpeg}") { route: Route -> route.abort() }
    }


    override fun execute() {

        loadPlayersPage()

        page.querySelector("#search-input").fill(playerName)
        page.querySelector("#mainContent > div.playerIndex > header > div > div > div > div")
            .click()
        page.waitForTimeout(1000.0)
        page.querySelector(".player").querySelector(".player__name").click()
        page.waitForLoadState(LoadState.NETWORKIDLE)

        println(Player.of(page).toString())
        println(page.url())

    }
}