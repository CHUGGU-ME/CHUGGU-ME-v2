package club.view

import club.domain.ClubCoreInfo

class ClubInputView {

    fun chooseClub(playerCoreInfoList: MutableList<ClubCoreInfo>): ClubCoreInfo {
        for (idx in 0..playerCoreInfoList.lastIndex) {
            println("${idx + 1} : ${playerCoreInfoList[idx].clubName}")
        }

        var number: Int = 1

        while (true) {
            println("Enter the number of the Club: ")
            val input = readLine()
            if (input == "end") {
                break
            }

            try {
                number = Integer.parseInt(input)
            } catch (e: TypeCastException) {
                println("Wrong input")
                continue
            }

            if (!(1 <= number && number <= playerCoreInfoList.size)) {
                println("Enter 1 ~ ${playerCoreInfoList.size}")
            }

            break
        }
        return playerCoreInfoList[number - 1]
    }

}