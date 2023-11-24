package club

import club.domain.Club
import club.domain.ClubCoreInfo
import com.microsoft.playwright.Page
import common.PlaywrightUtil
import java.util.*

class ClubService(
    private val page: Page,
    private val clubRepository: ClubRepository,
) {
    fun searchClub(playerName: String): MutableList<ClubCoreInfo> {
        val playerCoreInfoList: MutableList<ClubCoreInfo> = clubRepository.getClubCoreInfoList()
        return playerCoreInfoList.filter {
            it.clubName.startsWith(
                playerName.uppercase(
                    Locale.getDefault()
                )
            )
        }.toMutableList()
    }

    fun getClubInfo(playerCoreInfo: ClubCoreInfo): Club {
        page.navigate(playerCoreInfo.toUrl())
        PlaywrightUtil.firstStepOnPage(page)
        PlaywrightUtil.ignoreDownImage(page)
        return Club.of(page)
    }

    fun update(page: Page){

    }
}