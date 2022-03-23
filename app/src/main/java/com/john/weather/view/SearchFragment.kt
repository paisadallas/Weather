package com.john.weather.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.john.weather.R
import com.john.weather.databinding.FragmentSearchBinding
import com.john.weather.viewmodel.ResultState

//import com.john.jetpack.databinding.FragmentOneBinding
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val binding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var nameCity = "Passing data"

        binding.btSearch.setOnClickListener {
          //  findNavController().navigate(R.id.action_searchFragment_to_listFragment)
            Log.d("CLICK",binding.etSearch.text.toString())
            weatherViewModel.cityForecast.observe(viewLifecycleOwner,::handleState)
            weatherViewModel.getForecast(binding.etSearch.text.toString())

        }
        return binding.root
    }

    private fun handleState(resultState: ResultState?) {
        var found:Boolean = false
        var city = ""
        when(resultState){
            is ResultState.LOADING ->{
                found =false
                binding.tvCityFound.text ="Searching ..."
                Toast.makeText(requireContext(),"LAODING....", Toast.LENGTH_SHORT).show()
            }
            is ResultState.SUCCESS ->{
               // weatherAdapter.update(resultState.results.list)
                found=true
                binding.tvCityFound.text = binding.etSearch.text.toString()
                binding.tvWeatherFound.text = "Found"
                Toast.makeText(requireContext(),"SUCCESSFUL....", Toast.LENGTH_SHORT).show()
            }
            is ResultState.ERROR ->{
                found =false
                binding.tvCityFound.text= "City no found :("
                Log.e("FORCAST",resultState.error.localizedMessage,resultState.error)
                Toast.makeText(requireContext(),resultState.error.localizedMessage, Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(),"ERROR....", Toast.LENGTH_SHORT).show()
            }
        }
        binding.cvCity.setOnClickListener {
            if (found){
                Log.d("CLICK","CLICK_FOUND")
                city = binding.etSearch.text.toString()
             //   findNavController().navigate(R.id.action_searchFragment_to_listFragment)
                val intention = SearchFragmentDirections.actionSearchFragmentToListFragment(city)
                findNavController().navigate(intention)
            }else{
                Log.d("CLICK","CLICK_NO_FOUND")
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}