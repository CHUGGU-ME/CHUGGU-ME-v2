package subcommand

import common.FileName
import common.OsCommand.Companion.browser
import common.readFromFile
import domain.News
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand


@OptIn(ExperimentalCli::class)
class ArticleSubCommand:  Subcommand("article", "Article") {


    override fun execute() {
        while(true) {
            println("Enter the number of the article: ")
            val input = readLine()
            if (input == "end") {
                break
            }
            getNewsSelect(input?.toIntOrNull())
        }
    }

    private fun getNewsSelect(no: Int?) {
        val newsList = readFromFile<MutableList<News>>(FileName.NEWS_LIST.fileName)
        if (no != null && no <= 10 && no > 0) {
            browser(newsList[no-1].url)
        } else {
            println("Wrong Input, please try again between 1 to 10")
        }
    }
}