package subcommand

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.GoalService

@OptIn(ExperimentalCli::class)
class GoalSubCommand : Subcommand("goal", "GOAL!!") {

    lateinit var goalService: GoalService

    private fun init(){
        goalService = GoalService()
    }


    override fun execute() {
        init()
        goalService.printlnIdiot()
    }
}