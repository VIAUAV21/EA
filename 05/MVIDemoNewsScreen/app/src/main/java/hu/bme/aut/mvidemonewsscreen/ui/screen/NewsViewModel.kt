package hu.bme.aut.mvidemonewsscreen.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class NewsState {
    object Init : NewsState()
    object Loading : NewsState()
    data class Error(val error: Throwable) : NewsState()
    data class Success(val newsText: String) : NewsState()
}

class NewsViewModel : ViewModel() {

    private val _state = MutableStateFlow<NewsState>(
        NewsState.Init)

    val state = _state.asStateFlow()

    fun loadNews() {
        viewModelScope.launch {
            try {
                _state.value = NewsState.Loading
                delay(3000)
                _state.value = NewsState.Success(
                    newsText = "Here are the latest news....")
            } catch (e: Exception) {
                _state.value = NewsState.Error(e)
            }
        }
    }


}