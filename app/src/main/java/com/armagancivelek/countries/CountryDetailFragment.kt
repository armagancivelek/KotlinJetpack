package com.armagancivelek.countries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.armagancivelek.countries.databinding.FragmentCountryDetailBinding
import com.armagancivelek.viewmodel.CountryDeatailViewModel


class CountryDetailFragment : Fragment() {
    private val TAG = "ABC"
    private lateinit var viewModel: CountryDeatailViewModel
    private var countryUuid = 0
    private lateinit var binding: FragmentCountryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "oncreate")
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(TAG, "oncreateView")

        binding = FragmentCountryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")
        viewModel = ViewModelProviders.of(this).get(CountryDeatailViewModel::class.java)

        arguments?.let {
            countryUuid = CountryDetailFragmentArgs.fromBundle(it).Uuid
            viewModel.getDataFromRoom(countryUuid)
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->

            binding.selectedCountry = country
//
//              country?.let {
//                  countryName.text=country.countryName
//                  countryCapitol.text=country.countryCapitol
//                  countryCurrency.text=country.countryCurrency
//                  countryLanguage.text=country.countryLanguage
//                  countryRegion.text=country.countryLanguage
//                  context?.let {
//                      countryImage.downloadFromUrl(country.countryImageUrl,
//                          placeholderProgressBar(it))
//                  }
//
//               }
        })

    }


}