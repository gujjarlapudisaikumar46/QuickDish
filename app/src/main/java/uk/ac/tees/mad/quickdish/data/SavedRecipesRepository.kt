package uk.ac.tees.mad.quickdish.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uk.ac.tees.mad.quickdish.model.Recipe

val Context.savedRecipesDataStore by preferencesDataStore("saved_recipes")

private val SAVED_RECIPES_KEY = stringPreferencesKey("recipes_list")

class SavedRecipesRepository(private val context: Context) {

    val savedRecipesFlow = context.savedRecipesDataStore.data.map { prefs ->
        val json = prefs[SAVED_RECIPES_KEY] ?: "[]"
        Json.decodeFromString<List<Recipe>>(json)
    }

    suspend fun saveRecipe(recipe: Recipe): Boolean {
        return try {
            // Read current list
            val current = context.savedRecipesDataStore.data
                .map { prefs ->
                    prefs[SAVED_RECIPES_KEY]?.let {
                        Json.decodeFromString<List<Recipe>>(it)
                    } ?: emptyList()
                }
                .first()

            // If already exists → succeed silently
            if (current.any { it.id == recipe.id }) return true

            // Build updated list
            val updated = current + recipe

            // Save back
            context.savedRecipesDataStore.edit { prefs ->
                prefs[SAVED_RECIPES_KEY] = Json.encodeToString(updated)
            }

            true
        } catch (e: Exception) {
            false
        }
    }


    suspend fun removeRecipe(id: String): Boolean {
        return try {
            context.savedRecipesDataStore.edit { prefs ->
                val current = prefs[SAVED_RECIPES_KEY]?.let {
                    Json.decodeFromString<List<Recipe>>(it)
                } ?: emptyList()

                prefs[SAVED_RECIPES_KEY] =
                    Json.encodeToString(current.filterNot { it.id == id })
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
