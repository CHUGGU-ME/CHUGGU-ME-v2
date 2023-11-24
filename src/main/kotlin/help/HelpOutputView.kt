package help

class HelpOutputView {
    fun printCommendList() {

        val commandWord = mapOf(
            "help" to "사용 가능한 명령어와 설명을 확인할 수 있습니다.",
            "article" to "EPL 뉴스 번호를 입력 하여 해당 뉴스를 새창에 띄웁니다.",
            "news" to "EPL 뉴스 탑10의 제목, URL을 확인 합니다.",
            "goal" to "ascii-art를 사용한 간단한 애니메이션!",
            "update" to "EPL 선수 목록, 리그 성적 등의 정보를 최신으로 업데이트 합니다.",
            "schedule" to "EPL 경기 일정을 조회 합니다.",
            "player" to "EPL 선수 이름을 입력 하여 선수별 상세 정보를 조회할 수 있습니다.",
            "mom" to "시즌, 경기 날짜, 해당 팀의 최고의 선수를 조회할 수 있습니다."
        )

        println(
            " _   _      _        \n" +
            "| | | | ___| |_ __   \n" +
            "| |_| |/ _ \\ | '_ \\ \n" +
            "|  _  |  __/ | |_) |  \n" +
            "|_| |_|\\___|_| .__/  \n" +
            "            |_|         "
        )
        println()
        println("chuggu-me ")
        for ((k,v) in commandWord) {
            println("   $k : $v")
        }

    }
}