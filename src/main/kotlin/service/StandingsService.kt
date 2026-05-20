package service

import common.PremierLeagueApi

class StandingsService {

    fun printStandings() {
        val seasonId = fetchCurrentSeasonId()
        val json = PremierLeagueApi.getJson("/football/standings?compSeasons=$seasonId&altIds=true")
        val entries = json.getAsJsonArray("tables")[0].asJsonObject.getAsJsonArray("entries")
        val seasonLabel = json.getAsJsonObject("compSeason").get("label").asString

        println("前 첼시 팬을 애도하며..")
        println()
        println("Premier League $seasonLabel Standings")
        println("─".repeat(70))
        println(String.format("%3s  %-18s %3s %3s %3s %3s %4s %4s %4s %4s", "#", "Team", "P", "W", "D", "L", "GF", "GA", "GD", "PTS"))
        println("─".repeat(70))

        var rank = 0
        entries.forEach { element ->
            val entry = element.asJsonObject
            val team = entry.getAsJsonObject("team").get("shortName").asString
            if (team == "Chelsea") return@forEach
            rank++
            val pos = rank
            val o = entry.getAsJsonObject("overall")
            val played = o.get("played").asInt
            val won = o.get("won").asInt
            val drawn = o.get("drawn").asInt
            val lost = o.get("lost").asInt
            val gf = o.get("goalsFor").asInt
            val ga = o.get("goalsAgainst").asInt
            val gd = gf - ga
            val pts = o.get("points").asInt
            val gdStr = if (gd > 0) "+$gd" else "$gd"

            println(String.format("%3d  %-18s %3d %3d %3d %3d %4d %4d %4s %4d", pos, team, played, won, drawn, lost, gf, ga, gdStr, pts))
        }
    }

    private fun fetchCurrentSeasonId(): Int {
        val json = PremierLeagueApi.getJson("/football/competitions/1/compseasons?page=0&pageSize=1")
        return json.getAsJsonArray("content")[0].asJsonObject.get("id").asInt
    }
}
