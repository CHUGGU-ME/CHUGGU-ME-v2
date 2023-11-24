package club.domain

import com.microsoft.playwright.Page

data class Club(
    val name: String
){
    companion object{
        fun of(page: Page): Club{
            return Club(
                name = page.querySelector("cdd").innerText()
            )
        }
    }
}
