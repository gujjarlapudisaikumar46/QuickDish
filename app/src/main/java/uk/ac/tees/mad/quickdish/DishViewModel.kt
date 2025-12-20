package uk.ac.tees.mad.quickdish

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import uk.ac.tees.mad.quickdish.data.GeminiService
import uk.ac.tees.mad.quickdish.data.SavedRecipesRepository
import uk.ac.tees.mad.quickdish.model.Recipe

class DishViewModel : ViewModel() {

    val service = GeminiService(apiKey = "AIzaSyD15AqUkAVXbE_Lj6ZhSbDXNw4r6Ii7GCw")
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun onFindRecipes(ingredients: String, onSuccess: () -> Unit) {
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
                onSuccess()
            } catch (e: Exception) {
                _error.value = "Failed to get recipes: ${e.message}"
                Log.d("Recipe", e.message.toString())
            } finally {
                _loading.value = false
            }
        }
    }

    fun saveRecipe(recipe: Recipe, context: Context) {
        viewModelScope.launch {
            val datastore = SavedRecipesRepository(context)
            val success = datastore.saveRecipe(recipe)

            Toast.makeText(
                context,
                if (success) "Recipe saved!" else "Failed to save recipe",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun clearCache(context: Context) {
        viewModelScope.launch {
            val repo = SavedRecipesRepository(context)
            val ok = repo.clearAllRecipes()

            Toast.makeText(
                context,
                if (ok) "Cache cleared" else "Failed to clear cache",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}

