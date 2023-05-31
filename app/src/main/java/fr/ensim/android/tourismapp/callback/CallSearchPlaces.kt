package fr.ensim.android.tourismapp.callback

import android.util.Log
import com.google.gson.Gson
import fr.ensim.android.tourismapp.data.Data
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

abstract class CallSearchPlaces : Callback {
    companion object {
        private const val TAG = "CallSearchPlace"
    }

    abstract fun fireOnResponseOk(data: Data)

    override fun onFailure(call: Call, e: IOException) {
        Log.e(TAG, ">>onFailure", e)
    }

    override fun onResponse(call: Call, response: Response) {
        Log.e(TAG, ">>onResponse")

        val responseData = response.body!!.string()
        Log.d(TAG, "onResponse: $responseData")

        val gson = Gson()

        val data: Data = gson.fromJson(
            responseData,
            Data::class.java)

        fireOnResponseOk(data)
    }
}