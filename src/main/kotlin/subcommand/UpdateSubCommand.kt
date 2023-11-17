package subcommand

import com.microsoft.playwright.ElementHandle
import com.microsoft.playwright.Page
import com.microsoft.playwright.Page.WaitForLoadStateOptions
import com.microsoft.playwright.Route
import com.microsoft.playwright.options.LoadState
import common.PlaywrightUtil
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import java.io.File
import java.nio.file.Path
import java.util.*
import kotlin.collections.HashMap

@OptIn(ExperimentalCli::class)
class UpdateSubCommand : Subcommand("update", "Update Data") {


    val page: Page = PlaywrightUtil.page

    fun looadPlayersPage() {
        page.navigate("https://www.premierleague.com/players")
        page.waitForLoadState(LoadState.LOAD)
        if (page.querySelector("#onetrust-banner-sdk > div").isVisible) {
            page.querySelector("#onetrust-accept-btn-handler").click()
        }
        page.waitForLoadState(LoadState.NETWORKIDLE)
        page.route("**/*.{png,jpg,jpeg}") { route: Route -> route.abort() }
    }


    override fun execute() {
        looadPlayersPage()

        page.keyboard().press("End")
        page.waitForTimeout(5000.0)

        val players: List<ElementHandle> = page.querySelectorAll("tr.player")

        val savePlayerData = linkedMapOf<String, String>()

        players.forEach {
            val urlInfo: String = it.querySelector("a.player__name").getAttribute("href").split("players/")[1].replace("/overview", "")

            val keyword = urlInfo.split("/")[1].uppercase(Locale.getDefault())
            val urlData: String = urlInfo
            savePlayerData[keyword] = urlData

        }


        val file = File(Path.of("./","data.json").toUri())



    /// ... ing..
    }
}