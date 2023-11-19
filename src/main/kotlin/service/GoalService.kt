package service

class GoalService {
    fun printlnGoal() {
        val gLetter = arrayOf(
            "  GGGG     OOOO        A      L         !!     !!  ",
            " G    G   O    O      A A     L        !!!!   !!!! ",
            " G       O      O    A   A    L        !!!!    !!  ",
            " G  GGG  O      O   AAAAAAA   L         !!     !!  ",
            " G    G   O    O   A       A  L                    ",
            "  GGGG     OOOO    A       A  LLLLLLL   !!     !!  ",
        )
        for (line in gLetter) {
            println(line)
        }
    }
}