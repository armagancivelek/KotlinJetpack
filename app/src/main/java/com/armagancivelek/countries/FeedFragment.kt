package com.armagancivelek.countries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.armagancivelek.adapter.CountryAdapter
import com.armagancivelek.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {
    private val TAG = "ABC"
    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "oncreate")
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "oncreateView")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        init()
        observeLiveData()
        eventHandler()


    }

    private fun eventHandler() {
        swipeRefreshLayout.setOnRefreshListener {

            countryList.visibility = View.GONE

            swipeRefreshLayout.isRefreshing = false
            viewModel.refreshData()

        }
    }

    private fun init() {
        //syntetich
        countryList.layoutManager = LinearLayoutManager(context)
        countryList.adapter = countryAdapter
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)// view modeli almak
        viewModel.refreshData()// ilk kez verileri çekme

    }


    private fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer {

            it?.let {
                countryList.visibility = View.VISIBLE
                countryError.visibility = View.GONE
                countryAdapter.updateCountryLİst(it)
                //countryList.adapter=CountryAdapter(it as ArrayList<Country>)

            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer {

            it?.let {
                if (it) {
                    countryError.visibility = View.VISIBLE
                } else {
                    countryError.visibility = View.GONE
                }

            }
        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer
        {
            it?.let {
                if (it) {
                    countryProgressBar.visibility = View.VISIBLE
                    countryList.visibility = View.GONE
                    countryError.visibility = View.GONE

                } else {

                    countryProgressBar.visibility = View.GONE

                }
            }
        })
    }

}