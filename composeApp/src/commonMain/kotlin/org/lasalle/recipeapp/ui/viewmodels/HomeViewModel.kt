package org.lasalle.recipeapp.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.lasalle.recipeapp.data.services.KtorfitFactory
import org.lasalle.recipeapp.models.Prompt
import org.lasalle.recipeapp.models.RecipeReview
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import org.lasalle.recipeapp.data.services.Preferences
import org.lasalle.recipeapp.models.Recipe

class HomeViewModel : ViewModel() {
    private val recipeservice = KtorfitFactory.getRecipeService()
    var ingredients by mutableStateOf("")
    var generatedRecipe by mutableStateOf<RecipeReview?>(null)
    var recentRecipes by mutableStateOf<List<Recipe>>(listOf())
    var recipes by mutableStateOf<List<Recipe>>(listOf())
    val userId = Preferences.getUserId()
    var showSheet by mutableStateOf(false)
    var isLoading by mutableStateOf(false)

    var errorMessage by mutableStateOf<String?>(null)

    init {
        loadRecipes()
    }

    fun loadRecipes(){
        viewModelScope.launch {
            try {
                isLoading = true
                val result = recipeservice.getRecipeByUserId(userId)
                recipes = result
                recentRecipes = recipes.takeLast(5).reversed()
                println(result.toString())
            } catch (e: Exception){
                println(e.toString())
            }  finally {
                isLoading = false
            }
        }
    }

    // Cambia la firma de la función para recibir 'ingredientsInput'
    // Quita el parámetro "onShowSheet", no lo necesitas
    fun generatedRecipe(ingredientsInput: String){
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null // Limpiamos errores previos

                if (ingredientsInput.isBlank()) return@launch

                val prompt = Prompt(ingredientsInput)

                // 2. Llamada a la API
                val result = recipeservice.generateRecipe(prompt)

                generatedRecipe = result
                showSheet = true

            } catch (e: Exception) {
                // 3. CAPTURAMOS EL ERROR PARA LA UI
                e.printStackTrace()
                errorMessage = "Fallo: ${e.message}" // Guardamos el mensaje
            } finally {
                isLoading = false
            }
        }
    }

    fun clearError() {
        errorMessage = null
    }

    fun showModalFromList(recipe: RecipeReview){
        showSheet = true
        generatedRecipe = recipe
    }

    fun saveRecipeInDb(){
        viewModelScope.launch {
            try {
                isLoading = true

                // Verificar que tengamos un usuario válido antes de enviar
                if (userId == 0) {
                    errorMessage = "Error: No se detectó un usuario logueado."
                    isLoading = false
                    return@launch
                }

                val recipe = Recipe(
                    // id = 0,  <--- ¡BORRA O COMENTA ESTA LÍNEA!
                    // Al quitarla, se enviará como null y el servidor creará el ID nuevo.

                    category = generatedRecipe?.category ?: "Sin categoría",
                    ingredients = generatedRecipe?.ingredients ?: listOf(),
                    imageUrl = generatedRecipe?.imageUrl ?: "",
                    instructions = generatedRecipe?.instructions ?: listOf(),
                    userId = userId,
                    minutes = generatedRecipe?.minutes ?: 0,
                    stars = generatedRecipe?.stars ?: 0,
                    title = generatedRecipe?.title ?: "Receta Generada"
                )

                // Intentamos guardar
                val result = recipeservice.saveRecipeInDb(recipe)

                println("Guardado exitoso: $result")
                showSheet = false
                loadRecipes()

            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = "No se pudo guardar: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun hideModal(){
        showSheet = false
        generatedRecipe = null
    }

    fun logout(){
        Preferences.saveIsLogged(false)
    }
}