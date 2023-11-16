package common

import org.apache.commons.lang3.SystemUtils
import java.io.IOException
import java.net.URI

class OsCommand {
    companion object {
        fun browser(url: String?) {
            try {
                // 버전별로 추가 :)....
                when (true) {
                    SystemUtils.IS_OS_WINDOWS -> windows(url)
                    SystemUtils.IS_OS_MAC -> mac(url)
                    else -> unsupported()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InternalError) {
                e.printStackTrace()
            }
        }

        fun windows(url: String?) {
            val uri = URI("https://www.example.com")
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler $uri")
        }

        fun mac(url: String?) {
            val uri = URI(url)
            val processBuilder = ProcessBuilder("open", uri.toString())
            processBuilder.start()
        }

        fun unsupported() {
            println("DesktopBrowser: unsupported os")
        }
    }
}