package subcommand

import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class GoalSubCommand : Subcommand("goal", "GOAL!!") {

    private fun printlnGoal() {
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


    override fun execute() {
        printlnGoal()
    }
}