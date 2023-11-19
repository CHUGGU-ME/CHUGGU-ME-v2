package service

import Repository.NewsRepository

class NewsService(
    val newsRepository: NewsRepository,
) {
    fun getNews() {
        val resultNews = newsRepository.getNewsInfo()
        
        for (news in resultNews) {
            println("${news.no} : ${news.title}")
            println("url: ${news.url}")
            println()
        }
    }
}