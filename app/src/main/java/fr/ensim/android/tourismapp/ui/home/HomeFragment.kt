package fr.ensim.android.tourismapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import fr.ensim.android.tourismapp.callback.CallSearchCity
import fr.ensim.android.tourismapp.R
import fr.ensim.android.tourismapp.data.City
import fr.ensim.android.tourismapp.databinding.FragmentHomeBinding
import fr.ensim.android.tourismapp.service.PlaceService


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var TAG = "HomeFragment"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val searchView: SearchView = binding.searchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG, "onQueryTextSubmit $query")


                val callSearchCity = object : CallSearchCity() {
                    override fun fireOnResponseOk(data: City) {

                        requireActivity().runOnUiThread {

                            var city = data
                            var lon = city.lon
                            var lat = city.lat

                            Log.d("HomeFragment", "lon = $lon")
                            Log.d("HomeFragment", "lat = $lat")

                            val bundle = bundleOf(
                                "place" to city.name,
                                "lon" to lon.toString(),
                                "lat" to lat.toString())
                            val navController = findNavController()
                            navController.navigate(R.id.navigation_dashboard, bundle)
                        }
                    }
                }
                val placeService = PlaceService()
                placeService.searchCity(query, callSearchCity)

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "onQueryTextChange $newText")
                return true
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}