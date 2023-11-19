package service

import common.FileName
import common.readFromFile
import domain.News

class NewsService {
    fun getNews() {
        val resultNews = readFromFile<MutableList<News>>(FileName.NEWS_LIST.fileName)

        for (news in resultNews) {
            println("${news.no} : ${news.title}")
            println("url: ${news.url}")
            println()
        }
    }
}