package fr.ensim.android.tourismapp.service

import android.util.Log
import fr.ensim.android.tourismapp.callback.CallSearchCity
import fr.ensim.android.tourismapp.callback.CallSearchImage
import fr.ensim.android.tourismapp.callback.CallSearchImageGoogle
import fr.ensim.android.tourismapp.callback.CallSearchPlaces
import okhttp3.OkHttpClient
import okhttp3.Request

class PlaceService {

    fun searchPlaces(city: String?, lon: String?, lat: String?,
                     callback: CallSearchPlaces
    ){

        val client = OkHttpClient()

        Log.d("searchPlaces", "lon = $lon")
        Log.d("searchPlaces", "lat = $lat")

        /*val request: Request = Request.Builder()
            .url("https://api.opentripmap.com/0.1/en/places/autosuggest?" +
                    "name=$city&" +
                    "radius=1000000" +
                    "&lon=$lon" +
                    "&lat=$lat" +
                    "&apikey=5ae2e3f221c38a28845f05b618c46531cbbf6e0240730919ea2d0f5e")
            .build()*/

        val request: Request = Request.Builder()
            .url("https://api.opentripmap.com/0.1/en/places/radius?" +
                    "radius=25000" +
                    "&rate=3" +
                    "&kinds=other_hotels,museums,tourist_object,malls,restaurants" +
                    "&lon=$lon" +
                    "&lat=$lat" +
                    "&apikey=5ae2e3f221c38a28845f05b618c46531cbbf6e0240730919ea2d0f5e")
            .build()

        client.newCall(request).enqueue(callback)
    }

    fun searchPlaces(city: String?, callback: CallSearchPlaces){

        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url("https://api.opentripmap.com/0.1/en/places/autosuggest?" +
                    "name=$city&" +
                    "radius=1000000" +
                    "&lon=3.08746" +
                    "&lat=36.73225" +
                    "&apikey=5ae2e3f221c38a28845f05b618c46531cbbf6e0240730919ea2d0f5e")
            .build()

        client.newCall(request).enqueue(callback)
    }


    fun searchCity(city: String?, callback: CallSearchCity){

        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url("https://api.opentripmap.com/0.1/en/places/geoname" +
                    "?name=$city" +
                    "&apikey=5ae2e3f221c38a28845f05b618c46531cbbf6e0240730919ea2d0f5e")
            .build()

        client.newCall(request).enqueue(callback)
    }

    fun searchImage(wikiId: String?, callback: CallSearchImage){

        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url("https://www.wikidata.org/w/api.php?action=wbgetentities&format=json" +
                    "&ids=$wikiId" +
                    "&props=claims")
            .build()

        client.newCall(request).enqueue(callback)
    }

    fun searchImageGoogle(name: String?, callback: CallSearchImageGoogle){

        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url("https://www.googleapis.com/customsearch/v1" +
                    "?key=AIzaSyCBwabZQoOHfAH_qRY6rhq19BSd2j0Q3Fo" +
                    "&cx=55f82bc4dd8804faa" +
                    "&q=$name&searchType=image")
            .build()

        client.newCall(request).enqueue(callback)
    }

}