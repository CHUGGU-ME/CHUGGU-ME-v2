package subcommand

import Repository.NewsRepository
import Repository.ScheduleRepository
import player.PlayerRepository
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import mom.MomRepository
import service.UpdateService

@OptIn(ExperimentalCli::class)
class UpdateSubCommand : Subcommand("update", "Update Data") {

    override fun execute() {
        val updateService = UpdateService(
            playerRepository = PlayerRepository(),
            newsRepository = NewsRepository(),
            scheduleRepository = ScheduleRepository(),
        )
        updateService.updatePlayer()
        updateService.updateNews()
        updateService.updateSchedule()
        println("update successfully done!")
    }
}
