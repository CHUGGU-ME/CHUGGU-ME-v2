package subcommand

import com.microsoft.playwright.Page
import common.PlaywrightUtil
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.UpdateService

@OptIn(ExperimentalCli::class)
class UpdateSubCommand : Subcommand("update", "Update Data") {


    lateinit var page: Page

    override fun execute() {
        val updateService = UpdateService()
        page = PlaywrightUtil.getNewPlayWrightPage()
        updateService.updatePlayers(page)
        updateService.updateNews(page)
        println("update successfully done!")
    }
}
