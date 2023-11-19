package subcommand

import com.microsoft.playwright.*
import com.microsoft.playwright.options.LoadState
import common.PlaywrightUtil
import domain.Player
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class PlayerSubCommand : Subcommand("player", "Player info") {

    val playerName by argument(ArgType.String, description = "Player Name")
    lateinit var page: Page

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
        page = PlaywrightUtil.getNewPlayWrightPage()
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