package common

import com.microsoft.playwright.*
import com.microsoft.playwright.options.LoadState

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

        /*
        * allow cookie
        * skip ad
        * */
        fun firstStepOnPage(page: Page) {
            if (page.querySelector("#onetrust-banner-sdk > div") != null) {
                page.querySelector("#onetrust-accept-btn-handler").click()
            }
            val advertClose = page.querySelector("#advertClose") ?: return
            if (advertClose.isHidden) return
            try {
                advertClose.click()
            } catch (e: Throwable) {
                return
            }
        }

        /*
        * ignore image
        * */
        fun ignoreDownImage(page: Page){
            page.route("**/*.{png,jpg,jpeg}") { route: Route -> route.abort() }

        }
    }
}