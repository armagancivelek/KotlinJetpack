package com.armagancivelek.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.armagancivelek.Util.CustomSharedPreferences
import com.armagancivelek.model.Country
import com.armagancivelek.service.CountryAPIService
import com.armagancivelek.service.CountryDao
import com.armagancivelek.service.CountryDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val countyApiService = CountryAPIService()
    private val disposable = CompositeDisposable()//Kullanılmayan Call işlemlerini silme
    private var customPreferences: CustomSharedPreferences =
        CustomSharedPreferences(getApplication())
    private val refreshTime = 10 * 60 * 1000 * 1000 * 1000L * 0.1

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData() {


        val updateTime = customPreferences.getTime()
        if (updateTime != 0L && (System.nanoTime() - updateTime!!) < refreshTime)
            getDataFromSQLite()
        else
            getDataFromAPI()


    }

    private fun getDataFromSQLite() {
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()

            showCountries(countries)

            Toast.makeText(getApplication(), "Countries from sqlite", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getDataFromAPI() {


        disposable.add(
            countyApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                    override fun onSuccess(t: List<Country>) {
                        stroreInSQLite(t)
                        // Toast.makeText(getApplication(),"Countries from API",Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        Toast.makeText(
                            getApplication(),
                            "İnternet Bağlantınızı kontrol ediniz",
                            Toast.LENGTH_LONG
                        ).show()

                    }

                })
        )


    }

    private fun stroreInSQLite(list: List<Country>) {

        launch {

            val dao: CountryDao = CountryDatabase(getApplication()).countryDao()

            val listLong = dao.insertAll(*list.toTypedArray())

            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i++
            }
            showCountries(list)
        }

        customPreferences.saveTime(System.nanoTime())


    }

    private fun showCountries(countryList: List<Country>) {
        countries.value = countryList
        countryError.value = false
        countryLoading.value = false

    }


}