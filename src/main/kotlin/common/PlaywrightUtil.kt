package common

import com.microsoft.playwright.*

class PlaywrightUtil {
    fun playWrightUp(): Page {
        val playwright: Playwright = Playwright.create()
        val browser: Browser = playwright
            .chromium()
            .launch(
                BrowserType
                    .LaunchOptions()
            )
        val context: BrowserContext = browser.newContext()
        val page: Page = context.newPage()
        return page
    }
}