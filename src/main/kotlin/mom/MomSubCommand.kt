package mom

import com.microsoft.playwright.Page
import common.PlaywrightUtil
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class MomSubCommand : Subcommand("mom", "Man of the Match info") {

    val matchSeason by argument(ArgType.String, description = "Match Season")
    val matchDate by argument(ArgType.String, description = "Match Date")
    val matchTeam by argument(ArgType.String, description = "Match Team")

    lateinit var page: Page
    lateinit var momService: MomService
    //lateinit var momInfoInputView: MomInfoInputView
    lateinit var momInfoOutView: MomInfoOutView


    private fun init() {
        page = PlaywrightUtil.getNewPlayWrightPage()
        momService = MomService(
            page = page,
            momRepository = MomRepository()
        )
    }

    override fun execute() {
        init()
        val searchedMomInfo = momService.searchMomInfo(matchSeason, matchDate, matchTeam)
        //val fullMomInfo = momService.getFullPlayerInfo(searchedPlayer)
        //momInfoOutView.printMomInfo(searchedMomInfo)
    }
}