package viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.BirdImage
import singleton.Singleton


data class BirdUiState(
    val birdImage: List<BirdImage> = emptyList()
)

class BirdViewModel : ViewModel() {


    var _birdState:MutableStateFlow<BirdUiState> = MutableStateFlow<BirdUiState>(BirdUiState())
    val birdState:StateFlow<BirdUiState> = _birdState.asStateFlow()

    init {
        viewModelScope.launch {
            _birdState.update {
                it.copy(getBirds())
            }
        }
    }

    override fun onCleared() {
        Singleton.httpClient.close()
    }

    suspend fun getBirds() : List<BirdImage>{
        val client = Singleton.httpClient.get("https://sebastianaigner.github.io/demo-image-api/pictures.json")
        val data:List<BirdImage>  = client.body()
        if(data.size != 0){
            return data
            println( data)
        }else{
            return emptyList()
        }
    }
}