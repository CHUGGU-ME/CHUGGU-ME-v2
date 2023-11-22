package subcommand

import Repository.ScheduleRepository
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import service.ScheduleService

@OptIn(ExperimentalCli::class)
class ScheduleSubCommand: Subcommand("schedule", "Matches info") {
    lateinit var scheduleService: ScheduleService

    private fun init() {
        scheduleService = ScheduleService(ScheduleRepository())
    }

    override fun execute() {
        init()
        scheduleService.printSchedule()
    }
}