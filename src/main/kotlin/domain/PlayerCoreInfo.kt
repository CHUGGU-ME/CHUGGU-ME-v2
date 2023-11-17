package domain

import jdk.incubator.vector.VectorOperators.Binary
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.Serializable
import kotlinx.serialization.cbor.Cbor
import java.io.FileOutputStream

@Serializable
data class PlayerCoreInfo(
    val parsedUrl: String
)

fun main() {
//    val parsedUrl = "10001/Son"
//    val playerCoreInfo = PlayerCoreInfo(parsedUrl)
//    val fileName = "player_info_url.bin"
//    val binaryFormat: BinaryFormat = Cbor {}
//    FileOutputStream(fileName).use { fileOutputStream ->
//            binaryFormat.dumpToStream(playerCoreInfo)
//    }
//    val fileName = "player_data.bin"
//    val binaryFormat: BinaryFormat = Cbor {}
}
