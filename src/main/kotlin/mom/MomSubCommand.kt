package mom

import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class MomSubCommand : Subcommand("mom", "Man of the Match info") {

    val matchSeason by argument(ArgType.String, description = "Match Season")
    val matchDate by argument(ArgType.String, description = "Match Date")
    val matchTeam by argument(ArgType.String, description = "Match Team")

    override fun execute() {
        val momService = MomService(
            momRepository = MomRepository()
        )
        val momInfoOutView = MomInfoOutView()
        val searchedMomInfo = momService.searchMomInfo(matchSeason, matchDate, matchTeam)
        momInfoOutView.printMomInfo(searchedMomInfo)
    }
}
