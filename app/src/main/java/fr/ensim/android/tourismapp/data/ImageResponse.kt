package fr.ensim.android.tourismapp.data

data class ImageResponse(
    val context: Context,
    val items: List<Item>,
    val kind: String,
    val queries: Queries,
    val searchInformation: SearchInformation,
    val spelling: Spelling,
    val url: Url
)