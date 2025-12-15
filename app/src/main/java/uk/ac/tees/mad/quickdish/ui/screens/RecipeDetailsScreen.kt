package uk.ac.tees.mad.quickdish.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.quickdish.model.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    recipe: Recipe? = null,
    onBackClick: () -> Unit = {},
    onRecipeSave: (recipe : Recipe, context : Context) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Recipe Details",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = @androidx.compose.runtime.Composable {
                    Row() {
                        IconButton(
                            onClick = {
                                recipe?.let {
                                    onRecipeSave(recipe, context)
                                }
                            }) {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = "Copy Recipe"
                            )
                        }
                        IconButton(
                            onClick = {
                                recipe?.let {
                                    copyRecipeToClipboard(context, it)
                                }
                            }) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy Recipe"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        if (recipe == null) {
            // Empty state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "😕",
                    fontSize = 48.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Recipe not found",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFAFAFA))
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                // Recipe Title Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = recipe.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        if (recipe.description.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = recipe.description,
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row {
                            Text(
                                text = "🥗 ${recipe.ingredients.size} ingredients",
                                fontSize = 12.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                            Text(
                                text = "📝 ${recipe.steps.size} steps",
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }
                    }
                }
                // Ingredients Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🥗",
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                            Text(
                                text = "Ingredients",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        recipe.ingredients.forEachIndexed { index, ingredient ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp)
                            ) {
                                Text(
                                    text = "•",
                                    fontSize = 16.sp,
                                    color = Color(0xFF4CAF50),
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Text(
                                    text = ingredient,
                                    fontSize = 16.sp,
                                    color = Color(0xFF212121),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Instructions Section
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "📝",
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                            Text(
                                text = "Instructions",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF9800)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        recipe.steps.forEachIndexed { index, step ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Card(
                                    modifier = Modifier.padding(end = 12.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFFFF9800)
                                    )
                                ) {
                                    Text(
                                        text = "${index + 1}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 4.dp
                                        )
                                    )
                                }
                                Text(
                                    text = step,
                                    fontSize = 16.sp,
                                    color = Color(0xFF212121),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

private fun copyRecipeToClipboard(context: Context, recipe: Recipe) {
    val recipeText = buildString {
        appendLine("${recipe.title}\n")

        if (recipe.description.isNotEmpty()) {
            appendLine("${recipe.description}\n")
        }

        appendLine("INGREDIENTS:")
        recipe.ingredients.forEach { ingredient ->
            appendLine("• $ingredient")
        }

        appendLine("\nINSTRUCTIONS:")
        recipe.steps.forEachIndexed { index, step ->
            appendLine("${index + 1}. $step")
        }
    }
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Recipe", recipeText)
    clipboard.setPrimaryClip(clip)

    Toast.makeText(context, "Recipe copied to clipboard!", Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsScreenPreview() {
    val sampleRecipe = Recipe(
        id = "1",
        title = "Garlic Butter Chicken",
        description = "A delicious and easy chicken dish with garlic butter sauce, perfect for weeknight dinners.",
        ingredients = listOf(
            "500g chicken breast, cut into pieces",
            "4 cloves garlic, minced",
            "3 tbsp butter",
            "2 tbsp olive oil",
            "Salt and pepper to taste",
            "Fresh parsley for garnish"
        ),
        steps = listOf(
            "Season chicken pieces with salt and pepper.",
            "Heat olive oil in a large skillet over medium-high heat.",
            "Add chicken and cook for 5-7 minutes until golden brown.",
            "Add butter and minced garlic, cook for 2 minutes until fragrant.",
            "Reduce heat and cook for another 3-4 minutes until chicken is cooked through.",
            "Garnish with fresh parsley and serve hot."
        )
    )

    RecipeDetailsScreen(recipe = sampleRecipe, onRecipeSave = {recipe, context -> }, onBackClick = {})
}