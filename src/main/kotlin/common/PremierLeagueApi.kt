package common

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonArray
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

object PremierLeagueApi {

    private const val BASE_URL = "https://footballapi.pulselive.com"
    private const val ORIGIN = "https://www.premierleague.com"

    private val client: HttpClient = HttpClient.newHttpClient()
    private val gson: Gson = Gson()

    private fun get(path: String): String {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$BASE_URL$path"))
            .header("Origin", ORIGIN)
            .header("Accept", "application/json")
            .GET()
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() != 200) {
            throw RuntimeException("API request failed: ${response.statusCode()} - $path")
        }
        return response.body()
    }

    fun getJson(path: String): JsonObject {
        return gson.fromJson(get(path), JsonObject::class.java)
    }

    fun getJsonArray(path: String, field: String): JsonArray {
        return getJson(path).getAsJsonArray(field)
    }
}
