package com.armagancivelek.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.armagancivelek.model.Country
import com.armagancivelek.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryDeatailViewModel(application: Application) : BaseViewModel(application) {
    val countryLiveDoubleArray = MutableLiveData<Country>()

    val countryLiveData = MutableLiveData<Country>()
    fun getDataFromRoom(uuid: Int) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country

        }
    }


}