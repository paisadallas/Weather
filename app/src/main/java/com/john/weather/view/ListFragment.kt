package com.john.weather.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.john.weather.R
import com.john.weather.adapter.ClickAdapterWeather
import com.john.weather.adapter.WeatherAdapter
import com.john.weather.databinding.FragmentListBinding
import com.john.weather.viewmodel.ResultState

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : BaseFragment(),ClickAdapterWeather{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

   // private lateinit var clickAdapterWeather: ClickAdapterWeather

    val binding by lazy {
        FragmentListBinding.inflate(layoutInflater)
    }

    val args : ListFragmentArgs by navArgs()
//    private val weatherAdapter by lazy {
//        WeatherAdapter(clickAdapterWeather)
//    }

    private lateinit var  weatherAdapter : WeatherAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
      //  clickAdapterWeather = activity as ClickAdapterWeather

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        weatherAdapter = WeatherAdapter(this)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.rvItem.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = weatherAdapter
        }

        weatherViewModel.cityForecast.observe(viewLifecycleOwner, ::handleState)
        args.city?.let { weatherViewModel.getForecast(it) }?:"No info"

        return binding.root
    }

    private fun handleState(resultState: ResultState?) {
        when(resultState){
            is ResultState.LOADING ->{
                Toast.makeText(requireContext(),"LAODING....",Toast.LENGTH_SHORT).show()
            }
            is ResultState.SUCCESS ->{
                weatherAdapter.update(resultState.results.list)
                Toast.makeText(requireContext(),"SUCCESSFUL....",Toast.LENGTH_SHORT).show()
            }
            is ResultState.ERROR ->{
                Log.e("FORCAST",resultState.error.localizedMessage,resultState.error)
                Toast.makeText(requireContext(),resultState.error.localizedMessage, Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(),"ERROR....",Toast.LENGTH_SHORT).show()
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
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun checkWeather(temp:String,weather:String,description:String) {
        Log.d("CLICK","clik")

        val data= Array<String>(4){""}
        data[0]= args.city.toString()
        data[1]=temp
        data[2]=weather
        data[3]=description

        val intention = ListFragmentDirections.actionListFragmentToCityFragment(data)
        findNavController().navigate(intention)

      //  findNavController().navigate(R.id.action_listFragment_to_cityFragment)

    }


}