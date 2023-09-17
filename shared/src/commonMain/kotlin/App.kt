import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.logging.Logger
import model.BirdImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import singleton.Singleton
import kotlin.math.log

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val listBird = mutableListOf<List<BirdImage>>()
    LaunchedEffect(Unit,{
       println( getBirdsImages())
        listBird.add( getBirdsImages())
    })
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }
        LazyColumn (Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            items(listBird.size) {
                KamelImage(
                    asyncPainterResource("")
                )
            }

        }
    }

}
suspend fun getBirdsImages() :List<BirdImage>{
    val client = Singleton.httpClient.get("https://sebastianaigner.github.io/demo-image-api/pictures.json")
    val data:List<BirdImage>  = client.body()
    if(data.size != 0){
        return data
    }else{
        return emptyList()
    }
}

expect fun getPlatformName(): String