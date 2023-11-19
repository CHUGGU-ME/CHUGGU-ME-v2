package service

import common.FileName
import common.OsCommand
import common.readFromFile
import domain.News

class ArticleService {
    fun getNewsSelect(no: Int?) {
        val newsList = readFromFile<MutableList<News>>(FileName.NEWS_LIST.fileName)
        if (no != null && no <= 10 && no > 0) {
            OsCommand.browser(newsList[no - 1].url)
        } else {
            println("Wrong Input, please try again between 1 to 10")
        }
    }
}