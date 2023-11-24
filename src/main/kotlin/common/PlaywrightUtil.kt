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

        fun navigate(page: Page, url: String){
            var retry: Int = 0
            while(true){
                try{
                    page.navigate(url, Page.NavigateOptions().setTimeout(5000.0))
                    break
                }catch (timeoutErr: TimeoutError){
                    retry++
                }
                if(retry == 3){
                    throw Exception()
                    break
                }
            }
        }


        /*
        * allow cookie
        * skip ad
        * */
        fun firstStepOnPage(page: Page){
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