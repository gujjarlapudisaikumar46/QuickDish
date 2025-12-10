package uk.ac.tees.mad.quickdish

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import uk.ac.tees.mad.quickdish.data.GeminiService
import uk.ac.tees.mad.quickdish.model.Recipe

class DishViewModel : ViewModel() {

    val service = GeminiService(apiKey = "AIzaSyDHEnJVvg97j44iksiaKbypiiaKXq28UhM")
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun onFindRecipes(ingredients: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val json = service.generateRecipes(ingredients)
                val clean = json.replace("```json", "")
                    .replace("```", "")
                    .trim()

                val parsed = Json.decodeFromString<List<Recipe>>(clean)

                _recipes.value = parsed
                Log.d("Recipe", parsed.toString())

            } catch (e: Exception) {
                _error.value = "Failed to get recipes: ${e.message}"
                Log.d("Recipe", e.message.toString())
            } finally {
                _loading.value = false
            }
        }
    }
}

