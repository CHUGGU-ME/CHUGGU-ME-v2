package subcommand

import Repository.ArticleRepository
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.ArticleService


@OptIn(ExperimentalCli::class)
class ArticleSubCommand:  Subcommand("article", "Article") {

    lateinit var articleService: ArticleService

    private fun init(){
        articleService = ArticleService(ArticleRepository())
    }

    override fun execute() {
        init()
        while(true) {
            println("Enter the number of the article: ")
            val input = readLine()
            if (input == "end") {
                break
            }
            articleService.getNewsSelect(input?.toIntOrNull())
        }
    }
}