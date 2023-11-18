package common.fileioexample.subcommand

import common.readFromFile
import common.saveToBin
import common.fileioexample.person.Person
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand


@OptIn(ExperimentalCli::class)
class PersonSubCommand: Subcommand("person", "test File IO") {

    override fun execute() {

        val person = Person("John Doe", 40)
        saveToBin(person, "person.bin")
        val result: Person = readFromFile("person.bin")

        println("result: ${result.name}")
        println("result: ${result.age}")
    }
}