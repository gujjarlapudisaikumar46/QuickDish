package uk.ac.tees.mad.quickdish.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>
)
