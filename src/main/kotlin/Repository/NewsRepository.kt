package Repository

import common.FileName
import common.readFromFile
import common.saveToBin
import domain.News

class NewsRepository {
    fun getNewsInfo(): MutableList<News> {
        return readFromFile<MutableList<News>>(FileName.NEWS_LIST.fileName)
    }

    fun saveNewsInfo(saveNewsList: MutableList<News>){
        saveToBin(saveNewsList, FileName.NEWS_LIST.fileName)
    }
}