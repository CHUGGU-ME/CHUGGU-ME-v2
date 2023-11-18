package subcommand

import common.readFromFile
import domain.News
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class NewsSubCommand : Subcommand("news", "News") {
    override fun execute() {

        val resultNews = readFromFile<MutableList<News>>("newsList")
        for (news in resultNews) {
            println("${news.no} : ${news.title}")
            println("url: ${news.url}")
            println()
        }
    }
}