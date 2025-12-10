package uk.ac.tees.mad.quickdish.data

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

class GeminiService(apiKey: String) {

    private val model = GenerativeModel(
        modelName = "gemini-2.5-flash",
        apiKey = apiKey
    )

    suspend fun generateRecipes(ingredients: String): String {
        val prompt = """
            You are a recipe generator. Based ONLY on these ingredients:
            $ingredients

            Generate 3 recipes in JSON array format:

            [
              {
                "id": "string",
                "title": "string",
                "description": "string",
                "ingredients": ["list"],
                "steps": ["list"]
              }
            ]

            No extra text. Only valid JSON.
        """.trimIndent()

        val response = model.generateContent(
            content { text(prompt) }
        )

        return response.text ?: ""
    }
}
