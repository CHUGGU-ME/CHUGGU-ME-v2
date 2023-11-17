import kotlinx.cli.ArgParser
import subcommand.*
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    val parser = ArgParser("chuggue-me")

    parser.subcommands(
        NewsSubCommand(),
        PlayerSubCommand(),
        UpdateSubCommand(),
        GoalSubCommand(),
        PersonSubCommand()
        )
    parser.parse(args)

    exitProcess(0)

}




