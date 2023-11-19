package subcommand

import com.microsoft.playwright.*
import com.microsoft.playwright.options.LoadState
import common.FileName
import common.PlaywrightUtil
import common.readFromFile
import domain.News
import domain.Player
import domain.PlayerCoreInfo
import kotlinx.cli.ArgType
import kotlinx.cli.ExperimentalCli
import kotlinx.cli.Subcommand
import java.util.*
import kotlin.reflect.typeOf

@OptIn(ExperimentalCli::class)
class PlayerSubCommand : Subcommand("player", "Player info") {

    val playerName by argument(ArgType.String, description = "Player Name")
    lateinit var page: Page


    override fun execute() {
        page = PlaywrightUtil.getNewPlayWrightPage()
        val playerCoreInfoList = readFromFile<MutableList<PlayerCoreInfo>>(FileName.PLAYER_CORE_INFO_LIST.fileName)
        val filteredPlayerCoreInfo = playerCoreInfoList.filter { it.playerName.startsWith(playerName.uppercase(
            Locale.getDefault())) }
        
        for(idx in 0..filteredPlayerCoreInfo.lastIndex){
            println("${idx+1} : ${filteredPlayerCoreInfo[idx].playerName}")
        }

        var number: Int

        while (true){
            println("Enter the number of the Player: ")
            val input = readLine()
            if(input == "end"){
                break
            }

            try{
                number = Integer.parseInt(input)
            }catch (e: TypeCastException){
                println("Wrong input")
                continue
            }

            if(!(1 <= number &&  number <= filteredPlayerCoreInfo.size)){
                println("Enter 1 ~ ${filteredPlayerCoreInfo.size}")
            }

            page.navigate(filteredPlayerCoreInfo[number-1].toUrl())
            PlaywrightUtil.firstStepOnPage(page)
            PlaywrightUtil.ignoreDownImage(page)

            println(Player.of(page).toString())
            println(page.url())
            break
        }
    }
}