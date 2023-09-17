import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.logging.Logger
import model.BirdImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import singleton.Singleton
import viewModel.BirdViewModel
import kotlin.math.log

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val viewModel = getViewModel(Unit, viewModelFactory { BirdViewModel() })

    MaterialTheme {
        birdList (viewModel)
    }
}


@Composable
fun birdList (viewModel: BirdViewModel){
    val birdUiState by viewModel.birdState.collectAsState()
    AnimatedVisibility(birdUiState.birdImage.isNotEmpty()){
        LazyVerticalGrid(
            GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize().padding(5.dp),
            content = {
                items(birdUiState.birdImage) {
                        BirdImaeCells(it)
                }
            }
        )
    }
}


@Composable
fun BirdImaeCells(birdImage: BirdImage){
    KamelImage(
        asyncPainterResource("https://sebastianaigner.github.io/demo-image-api/${birdImage.path}"),
        contentDescription = null
    )
}

