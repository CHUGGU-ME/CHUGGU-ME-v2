import common.FileName
import common.fileioexample.subcommand.PersonSubCommand
import kotlinx.cli.ArgParser
import player.PlayerSubCommand
import subcommand.*
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    val parser = ArgParser("chuggue-me")

    parser.subcommands(
        NewsSubCommand(),
        PlayerSubCommand(),
        UpdateSubCommand(),
        GoalSubCommand(),
        PersonSubCommand(),
        ArticleSubCommand(),
        )
    parser.parse(args)

    exitProcess(0)

}




