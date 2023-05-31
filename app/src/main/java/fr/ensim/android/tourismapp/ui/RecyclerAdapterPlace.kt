package fr.ensim.android.tourismapp.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.ensim.android.tourismapp.callback.CallSearchImageGoogle
import fr.ensim.android.tourismapp.R
import fr.ensim.android.tourismapp.data.Feature
import fr.ensim.android.tourismapp.data.ImageResponse
import fr.ensim.android.tourismapp.databinding.ItemPlaceBinding
import fr.ensim.android.tourismapp.service.PlaceService


class RecyclerAdapterPlace(var places: List<Feature>) : RecyclerView.Adapter<RecyclerAdapterPlace.PlaceViewHolder>() {

    companion object {
        private const val TAG = "RecyclerAdapterPlace"
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterPlace.PlaceViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceBinding.inflate(inflater, parent, false)

        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerAdapterPlace.PlaceViewHolder, position: Int) {
        Log.d(TAG, " onBindViewHolder position=$position")

        val place: Feature = places[position]
        Log.d(TAG, " place name ${place.properties.name}")
        Log.d(TAG, " place kinds ${place.properties.kinds}")

        holder.binding.itemLayout.setOnClickListener {
            Log.d(TAG, " click on ${place.properties.name}")

            val placeName = place.properties.name
            val url = "https://www.google.fr/search?q=$placeName"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            holder.itemView.context.startActivity(intent)
        }

        try {
            holder.bind(place)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading image", e)
        }

    }

    override fun getItemCount(): Int {
        return places.size
    }


    class PlaceViewHolder(val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {

        private val itemTitleTextView = itemView.findViewById<TextView>(R.id.item_title_textview)
        private val itemDescriptionTextView = itemView.findViewById<TextView>(R.id.item_description_textview)
        private val imageViewPlace = itemView.findViewById<ImageView>(R.id.imageViewPlace)
        private val itemDistanceTextView = itemView.findViewById<TextView>(R.id.item_distance_textview)

        fun bind(place: Feature) {

            itemTitleTextView.text = place.properties.name

            val elements: List<String> = place.properties.kinds.split(",")

            val stringBuilder = StringBuilder()
            for (element in elements) {
                stringBuilder.append("- ").append(element).append("\n")
            }
            val output = stringBuilder.toString().trim { it <= ' ' }

            itemDescriptionTextView.text = output //place.properties.kinds
                        itemDistanceTextView.text = place.properties.dist.toString().split(".")[0]+" metres."
            var imageUrl = ""

            val callSearchImageGoogle = object : CallSearchImageGoogle() {
                override fun fireOnResponseOk(data: ImageResponse) {

                    if (data.items[0] != null)
                        imageUrl = data.items[0].link

                    // Charger l'image avec Glide
                    itemView.post {
                        Glide.with(itemView.context)
                            //"https://upload.wikimedia.org/wikipedia/commons/3/38/Universite_Paris_I_Pantheon-Sorbonne.jpg" : image statique
                            // imageUrl : appel vers l'api google custom search
                            .load(imageUrl)
                            .error(R.drawable.image_error)
                            .into(imageViewPlace)
                    }
                }
            }
            val placeService = PlaceService()
            placeService.searchImageGoogle(place.properties.name, callSearchImageGoogle)

        }
    }
}
