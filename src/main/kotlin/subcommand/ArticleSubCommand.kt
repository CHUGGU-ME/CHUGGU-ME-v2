package subcommand

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.ArticleService


@OptIn(ExperimentalCli::class)
class ArticleSubCommand:  Subcommand("article", "Article") {
    override fun execute() {
        val articleService = ArticleService()
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