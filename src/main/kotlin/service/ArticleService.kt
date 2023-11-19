package service

import Repository.ArticleRepository
import common.OsCommand

class ArticleService {
    fun getNewsSelect(no: Int?) {
        val articleRepository = ArticleRepository()

        val newsList = articleRepository.getNewsList()
        if (no != null && no <= 10 && no > 0) {
            OsCommand.browser(newsList[no - 1].url)
        } else {
            println("Wrong Input, please try again between 1 to 10")
        }
    }
}