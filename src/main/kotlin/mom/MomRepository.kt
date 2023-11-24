package mom

import common.FileName
import common.readFromFile
import common.saveToBin
import mom.domain.MomInfo
import player.domain.PlayerCoreInfo

class MomRepository {
    fun saveMomInfoList(momInfoList: List<MomInfo>) {
        saveToBin(momInfoList, FileName.MAN_OF_THE_MATCH_INFO_LIST.fileName)
    }

    fun getMomInfoList(): List<MomInfo> {
        return readFromFile<List<MomInfo>>(FileName.MAN_OF_THE_MATCH_INFO_LIST.fileName)
    }

}