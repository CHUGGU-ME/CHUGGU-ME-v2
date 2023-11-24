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
                        .setHeadless(false)
                )
            val context: BrowserContext = browser.newContext()
            val page: Page = context.newPage()
            return page
        }


        /*
        * allow cookie
        * skip ad
        * */
        fun firstStepOnPage(page: Page){
            page.waitForLoadState(LoadState.LOAD)
            if (page.querySelector("#onetrust-banner-sdk > div") != null) {
                page.querySelector("#onetrust-accept-btn-handler").click()
            }
            if(page.querySelector("#advertClose") != null){
                page.querySelector("#advertClose").click()
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