package service

import Repository.NewsRepository

class NewsService {
    fun getNews() {
        val newsRepository = NewsRepository()
        val resultNews = newsRepository.getNewsInfo()
        
        for (news in resultNews) {
            println("${news.no} : ${news.title}")
            println("url: ${news.url}")
            println()
        }
    }
}