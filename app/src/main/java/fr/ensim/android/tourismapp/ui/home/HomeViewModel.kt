package fr.ensim.android.tourismapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Veuillez entrez une ville ou une region..."
    }
    val text: LiveData<String> = _text
}
