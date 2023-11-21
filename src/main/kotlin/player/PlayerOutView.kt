package player

import player.domain.Player

class PlayerOutView {

    fun print(player: Player) {
        val sb: StringBuilder = StringBuilder()
        sb.append("name: ${player.firstName} ${player.lastName}\n")
        sb.append("club: ${player.club}\n")
        sb.append("position: ${player.position}\n")
        sb.append("number: ${player.number}\n")
        sb.append("height: ${player.height}\n")
        sb.append("date of birth: ${player.dateOfBirth}\n")
        sb.append("nationality: ${player.nationality}\n")
        sb.append("score: ${player.score}\n")
        sb.append("assist: ${player.assist}\n")
        sb.append("\n${player.playerUrl}")
         
        println(sb.toString())
    }
}