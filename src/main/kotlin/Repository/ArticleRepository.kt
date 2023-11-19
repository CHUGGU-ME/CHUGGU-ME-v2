package Repository

import common.FileName
import common.readFromFile
import domain.News

class ArticleRepository {
    fun getNewsList(): MutableList<News> {
        return readFromFile<MutableList<News>>(FileName.NEWS_LIST.fileName)
    }
}