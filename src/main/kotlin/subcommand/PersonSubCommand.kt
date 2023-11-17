package subcommand

import domain.person.Person
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import java.io.File



@OptIn(ExperimentalCli::class)
class PersonSubCommand: Subcommand("person", "test File IO") {

    override fun execute() {
        val person = Person("John Doe", 40)
        saveToBin(person, "person.bin")
        val result: Person = readFromFile("person.bin")
        println("result: ${result.name}")
        println("result: ${result.age}")
    }

    private fun saveToBin(person: Person, fileName: String) {
        val cborBytes = Cbor.encodeToByteArray(person)
        File(fileName).writeBytes(cborBytes)
    }

    fun readFromFile(fileName: String): Person {
        val fileBytes = File(fileName).readBytes()
        return try {
            Cbor.decodeFromByteArray(fileBytes)
        } catch (e: Exception) {
            throw Exception("file does not exist")
        }
    }

}