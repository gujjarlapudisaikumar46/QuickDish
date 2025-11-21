package uk.ac.tees.mad.quickdish.model

data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>
)
