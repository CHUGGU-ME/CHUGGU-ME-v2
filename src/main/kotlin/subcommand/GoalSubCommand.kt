package subcommand

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.GoalService

@OptIn(ExperimentalCli::class)
class GoalSubCommand : Subcommand("goal", "GOAL!!") {
    override fun execute() {
        val goalService = GoalService()
        goalService.printlnGoal()
    }
}