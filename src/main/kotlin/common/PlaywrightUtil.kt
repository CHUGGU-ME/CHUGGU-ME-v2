package common

import com.microsoft.playwright.*

class PlaywrightUtil {
    companion object{
        fun getNewPlayWrightPage(): Page {
            val playwright: Playwright = Playwright.create()
            val browser: Browser = playwright
                .chromium()
                .launch(
                    BrowserType
                        .LaunchOptions()
                        .setHeadless(true)
                )
            val context: BrowserContext = browser.newContext()
            val page: Page = context.newPage()
            return page
        }
    }
}