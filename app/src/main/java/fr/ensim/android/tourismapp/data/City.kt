package fr.ensim.android.tourismapp.data

data class City(
    val country: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val population: Int,
    val status: String,
    val timezone: String
)