package common

import com.microsoft.playwright.*

class PlaywrightUtil {
    companion object{
        val playwright: Playwright = Playwright.create()
        val browser: Browser = playwright
            .chromium()
            .launch(
                BrowserType
                    .LaunchOptions()
            )
        val context: BrowserContext = browser.newContext()
        val page: Page = context.newPage()
    }
}