package subcommand

import Repository.NewsRepository
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.NewsService

@OptIn(ExperimentalCli::class)
class NewsSubCommand : Subcommand("news", "News") {
    lateinit var newsService: NewsService

    private fun init(){
        newsService = NewsService(NewsRepository())
    }

    override fun execute() {
        init()
        newsService.getNews()
    }
}