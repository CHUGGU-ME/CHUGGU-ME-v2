package common

import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import java.io.File

inline fun <reified T> readFromFile(fileName: String): T {
    val filePath = "epl-data/$fileName"
    val fileBytes = File(filePath).readBytes()

    return try {
        Cbor.decodeFromByteArray(fileBytes)
    } catch (e: Exception) {
        throw Exception("file does not exist")
    }
}

inline fun <reified T> saveToBin(data: T, fileName: String) {
    val cborBytes = Cbor.encodeToByteArray(data)
    val filePath = "epl-data/$fileName"
    File(filePath).writeBytes(cborBytes)
}

