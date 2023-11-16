package subcommand

import com.microsoft.playwright.ElementHandle
import com.microsoft.playwright.Page
import com.microsoft.playwright.Page.WaitForLoadStateOptions
import com.microsoft.playwright.options.LoadState
import common.PlaywrightUtil
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

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
    }


    override fun execute() {
        looadPlayersPage()

        page.keyboard().press("End")
        page.waitForLoadState(LoadState.NETWORKIDLE, WaitForLoadStateOptions().setTimeout(2000.0))

        val palyers: List<ElementHandle> = page.querySelectorAll("tr.player")
        /// ... ing..
    }
}