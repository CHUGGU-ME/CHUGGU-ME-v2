package Repository

import common.FileName
import common.readFromFile
import domain.News

class NewsRepository {
    fun getNewsInfo(): MutableList<News> {
        return readFromFile<MutableList<News>>(FileName.NEWS_LIST.fileName)
    }

}