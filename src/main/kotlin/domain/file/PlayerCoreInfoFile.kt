package domain.file

import domain.PlayerCoreInfo

data class PlayerCoreInfoFile(
    val season: String,
    val playerCoreInfoList: MutableList<PlayerCoreInfo>,
)