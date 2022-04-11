package kmm.rickandmorty.app.android.presentation.components.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kmm.rickandmorty.app.android.presentation.components.locations.mappers.toPresentation
import kmm.rickandmorty.app.android.presentation.components.locations.model.LocationsUiState
import kmm.rickandmorty.app.android.presentation.components.locations.model.LocationsUiState.Loading
import kmm.rickandmorty.app.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationsViewModel(
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {
    private val charactersUiState: MutableStateFlow<LocationsUiState> =
        MutableStateFlow(Loading)
    val characters = charactersUiState.asStateFlow()

    fun fetchLocations() {
        viewModelScope.launch {
            val result = rickAndMortyRepository.fetchLocations()
                .map { character -> character.toPresentation() }
            charactersUiState.value = LocationsUiState.Success(result)
        }
    }
}
