package Repository

import common.FileName
import common.readFromFile
import common.saveToBin
import domain.Fixture

class ScheduleRepository {
    fun get(): MutableList<Fixture> {
        return readFromFile<MutableList<Fixture>>(FileName.SCHEDULE_LIST.fileName)
    }

    fun save(saveScheduleList: MutableList<Fixture>) {
        saveToBin(saveScheduleList, FileName.SCHEDULE_LIST.fileName)
    }
}