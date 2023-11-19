package subcommand

import com.microsoft.playwright.*
import com.microsoft.playwright.options.LoadState
import common.PlaywrightUtil
import domain.Player
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class MOMSubCommand : Subcommand("mom", "Man of the Match info") {

    /**
     * 아래 두 줄 관련된 playwrigt에서 크롤링 하는 로직은
     * UpdateSubCommand 쪽에서 처리 되도록 리팩토링 부탁드립니다.
     */
    private val playwright = PlaywrightUtil()
    private val page = playwright.playWrightUp()

    var matchWeek by argument(ArgType.String, description = "Match Week")
    fun whoseManOfTheMatch() {
        page.navigate("https://www.premierleague.com/man-of-the-match")
        page.waitForLoadState(LoadState.LOAD)

        page.waitForLoadState(LoadState.NETWORKIDLE)

        page.route("**/*.{png,jpg,jpeg}") { route: Route -> route.abort() }
    }


    override fun execute() {

        whoseManOfTheMatch()

        if(matchWeek.isEmpty()) {
            matchWeek = 1.toString()
        } else {
            matchWeek = (matchWeek.toInt() + 1).toString();
        }
        page.querySelector("#mainContent > div.tabbedContent > div.wrapper.col-12.mainKotmTab.active > section > div.dropDown.mobile").click()

        page.querySelector("#mainContent > div.tabbedContent > div.wrapper.col-12.mainKotmTab.active > section > div.dropDown.mobile.open > div.dropdownListContainer > ul > li:nth-child("+matchWeek+")").click()

        page.waitForTimeout(1000.0)

        page.waitForLoadState(LoadState.NETWORKIDLE)

        println(page.url())

    }
}