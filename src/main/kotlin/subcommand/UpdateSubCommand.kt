package subcommand

import Repository.NewsRepository
import player.PlayerRepository
import Repository.ScheduleRepository
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
            scheduleRepository = ScheduleRepository(),
        )
    }

    override fun execute() {
        init()
        updateService.initUpdateService()
        updateService.updatePlayer()
        updateService.updateNews()
        updateService.updateSchedule()
        println("update successfully done!")
    }
}
