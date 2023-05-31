package fr.ensim.android.tourismapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import fr.ensim.android.tourismapp.callback.CallSearchPlaces
import fr.ensim.android.tourismapp.data.Data
import fr.ensim.android.tourismapp.databinding.FragmentDashboardBinding
import fr.ensim.android.tourismapp.service.PlaceService
import fr.ensim.android.tourismapp.ui.RecyclerAdapterPlace

class DashboardFragment : Fragment() {

    private val TAG = "DashboardFragment"

    private var place: String? = null
    private var lon: String? = null
    private var lat: String? = null

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var placesAdapter: RecyclerAdapterPlace

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        arguments?.let {
            place = it.getString("place")
            lon = it.getString("lon")
            lat = it.getString("lat")
        }

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewPlace.visibility = View.GONE

        super.onViewCreated(view, savedInstanceState)

        placesAdapter = RecyclerAdapterPlace(emptyList())

        binding.recyclerViewPlace.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = placesAdapter
        }

        val callSearchPlaces = object : CallSearchPlaces() {
            override fun fireOnResponseOk(data: Data) {

                requireActivity().runOnUiThread {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerViewPlace.visibility = View.VISIBLE
                    placesAdapter.places = data.features
                    placesAdapter.notifyDataSetChanged()
                }

            }
        }

        val placeService = PlaceService()

        Log.d("DashboardFragment", "lon = $lon")
        Log.d("DashboardFragment", "lat = $lat")

        placeService.searchPlaces(place, lon, lat, callSearchPlaces)

    }
}