package club

import club.view.ClubInputView
import club.view.ClubOutputView
import com.microsoft.playwright.Page
import common.PlaywrightUtil
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand

@OptIn(ExperimentalCli::class)
class ClubSubCommand : Subcommand("club", "Club Info") {

    val clubName by argument(ArgType.String, description = "Club Name")
    lateinit var page: Page
    lateinit var clubService: ClubService
    lateinit var clubInputView: ClubInputView
    lateinit var clubOutputView: ClubOutputView


    private fun init() {
        page = PlaywrightUtil.getNewPlayWrightPage()
        clubService = ClubService(
            page = page,
            clubRepository = ClubRepository()
        )
    }

    override fun execute() {
        init()
        val searchedClub = clubService.searchClub(clubName)
        val chosedClub = clubInputView.chooseClub(searchedClub)
        val fullClubInfo = clubService.getClubInfo(chosedClub)
        clubOutputView.print(fullClubInfo)
    }
}