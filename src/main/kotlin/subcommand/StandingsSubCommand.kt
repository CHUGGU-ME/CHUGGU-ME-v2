package subcommand

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.StandingsService

@OptIn(ExperimentalCli::class)
class StandingsSubCommand : Subcommand("table", "League Table") {

    override fun execute() {
        StandingsService().printStandings()
    }
}
