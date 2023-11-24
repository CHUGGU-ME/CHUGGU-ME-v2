package help

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class HelpSubCommand : Subcommand("help", "command words list") {

    lateinit var helpOutputView: HelpOutputView
    override fun execute() {
        helpOutputView = HelpOutputView()
        helpOutputView.printCommendList()
    }
}