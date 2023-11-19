package subcommand

import common.FileName
import common.readFromFile
import domain.News
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.NewsService

@OptIn(ExperimentalCli::class)
class NewsSubCommand : Subcommand("news", "News") {
    override fun execute() {
        val newsService = NewsService()
        newsService.getNews()
    }
}