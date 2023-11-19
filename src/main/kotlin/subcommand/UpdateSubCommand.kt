package subcommand

import com.microsoft.playwright.Page
import common.PlaywrightUtil
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.UpdateService

@OptIn(ExperimentalCli::class)
class UpdateSubCommand : Subcommand("update", "Update Data") {


    lateinit var page: Page
    lateinit var updateService: UpdateService

    private fun initService(){
        page = PlaywrightUtil.getNewPlayWrightPage()
        updateService = UpdateService(page)
    }

    override fun execute() {
        initService()
        updateService.updatePlayers()
        updateService.updateNews()
        println("update successfully done!")
    }
}
