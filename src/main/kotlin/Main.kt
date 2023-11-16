import kotlinx.cli.ArgParser
import subcommand.GoalSubCommand
import subcommand.NewsSubCommand
import subcommand.PlayerSubCommand
import subcommand.UpdateSubCommand
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    val parser = ArgParser("chuggue-me")

    parser.subcommands(NewsSubCommand(), PlayerSubCommand(), UpdateSubCommand(), GoalSubCommand())
    parser.parse(args)

    exitProcess(0)

}




