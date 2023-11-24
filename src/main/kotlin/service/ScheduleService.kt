package service

import Repository.ScheduleRepository

class ScheduleService(
    val scheduleRepository: ScheduleRepository,
) {
    fun printSchedule() {
        val schedule = scheduleRepository.get()
        for (fixture in schedule) {
            println("${fixture.no} | date: ${fixture.date}, time: ${fixture.time}, match: ${fixture.home} vs ${fixture.away}, venue: ${fixture.venue}")
        }
    }
}