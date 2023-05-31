package fr.ensim.android.tourismapp.data

data class Feature(
    val geometry: Geometry,
    val id: String,
    val properties: Properties,
    val type: String
)