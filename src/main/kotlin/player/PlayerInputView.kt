package player

import player.domain.PlayerCoreInfo

class PlayerInputView {

    fun choosePlayer(playerCoreInfoList: MutableList<PlayerCoreInfo>): PlayerCoreInfo {
        for (idx in 0..playerCoreInfoList.lastIndex) {
            println("${idx + 1} : ${playerCoreInfoList[idx].playerName}")
        }

        var number: Int = 1

        while (true) {
            println("Enter the number of the Player: ")
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