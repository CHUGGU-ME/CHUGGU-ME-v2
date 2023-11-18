package subcommand

import com.microsoft.playwright.Browser
import com.microsoft.playwright.BrowserType
import com.microsoft.playwright.Playwright
import common.readFromFile
import domain.News
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand


@OptIn(ExperimentalCli::class)
class ArticleSubCommand:  Subcommand("article", "Article") {

    val newsList = readFromFile<MutableList<News>>("newsList")
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
        if (no != null && no <= 10 && no > 0) {
            fun newBrowser(playwright: Playwright): Browser = playwright
                .chromium()
                .launch(
                    BrowserType
                        .LaunchOptions()
                        .setHeadless(false)
                )

            val newPage = newBrowser(Playwright.create()).newContext().newPage()

            newPage.navigate(newsList.get(no-1).url)
        } else {
            println("Wrong Input, please try again between 1 to 10")
        }
    }
}