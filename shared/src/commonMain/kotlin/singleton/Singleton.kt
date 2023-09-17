package singleton

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

object Singleton {
    val httpClient = HttpClient {
        install(ContentNegotiation){
            json()
        }
    }
}