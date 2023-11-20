package subcommand

import Repository.NewsRepository
import Repository.PlayerRepository
import com.microsoft.playwright.Page
import common.PlaywrightUtil
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.UpdateService

@OptIn(ExperimentalCli::class)
class UpdateSubCommand : Subcommand("update", "Update Data") {


    lateinit var page: Page
    lateinit var updateService: UpdateService

    private fun init(){
        page = PlaywrightUtil.getNewPlayWrightPage()
        updateService = UpdateService(
            page = page,
            playerRepository = PlayerRepository(),
            newsRepository = NewsRepository(),
        )
    }

    override fun execute() {
        init()
        updateService.updatePlayer()
        updateService.updateNews()
        println("update successfully done!")
    }
}
