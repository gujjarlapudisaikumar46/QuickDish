package uk.ac.tees.mad.quickdish.ui.screens

import android.R.attr.onClick
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.SuggestionChip
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import uk.ac.tees.mad.quickdish.DishViewModel

@Composable
fun HomeScreen(
    viewModel: DishViewModel,
) {
    var ingredientsInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo/Title
            Text(
                text = "🍽️",
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "QuickDish",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter ingredients to discover amazing recipes",
                fontSize = 14.sp,
                color = Color(0xFF757575),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Ingredients Input Field
            OutlinedTextField(
                value = ingredientsInput,
                onValueChange = {
                    ingredientsInput = it
                    errorMessage = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                label = { Text("Enter ingredients...") },
                placeholder = { Text("e.g., chicken, tomatoes, rice") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    focusedLabelColor = Color(0xFF4CAF50),
                    cursorColor = Color(0xFF4CAF50)
                ),
                shape = RoundedCornerShape(12.dp),
                maxLines = 4,
                isError = errorMessage.isNotEmpty()
            )

            // Error Message
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    color = Color(0xFFD32F2F),
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sample Ingredients Chip
            SuggestionChip(
                onClick = {
                    ingredientsInput = "chicken, garlic, olive oil, tomatoes"
                    errorMessage = ""
                },
                label = { Text("Try sample ingredients") },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = Color(0xFFE8F5E9),
                    labelColor = Color(0xFF2E7D32)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Find Recipes Button
            Button(
                onClick = {
                    if (ingredientsInput.trim().isEmpty()) {
                        errorMessage = "Please enter at least one ingredient"
                        vibrateDevice(context)
                    } else {
                        errorMessage = ""
                        viewModel.onFindRecipes(ingredientsInput.trim())
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Find Recipes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private fun vibrateDevice(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    vibrator?.let {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            it.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            it.vibrate(200)
        }
    }
}

@Preview(showBackground = true, name = "QuickDish – Home Screen")
@Composable
fun QuickDishHomeScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo + Title
        Text(text = "Dish", fontSize = 48.sp)

        Spacer(Modifier.height(8.dp))

        Text(
            text = "QuickDish",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Enter ingredients to discover amazing recipes",
            fontSize = 14.sp,
            color = Color(0xFF757575),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(40.dp))
OutlinedTextField(value = "", onValueChange = {},)
        // Input Field
        OutlinedTextField(
            value = "chicken, tomatoes, rice, garlic",
            onValueChange = {},
            label = { Text("Enter ingredients...")},
                placeholder = { Text("e.g., chicken, tomatoes, rice") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    unfocusedBorderColor = Color.LightGray,
                    focusedLabelColor = Color(0xFF4CAF50),
                    cursorColor = Color(0xFF4CAF50)
                ),
                maxLines = 4
                )

                        SuggestionChip(
                        onClick = {
                            
                        },
                    label = { Text("Try sample ingredients") },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = Color(0xFFE8F5E9),
                        labelColor = Color(0xFF2E7D32)
                    )
                )

                Spacer(Modifier.height(32.dp))

                // Find Recipes Button
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Find Recipes",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
    }