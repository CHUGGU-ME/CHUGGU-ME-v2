package mom

import mom.domain.MomInfo

class MomInfoOutView {

    fun printMomInfo(momInfo: MomInfo?) {
        if(momInfo == null) println("매치 정보가 없습니다.")
        println(MomInfo.toString())
    }
}