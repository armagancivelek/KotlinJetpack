package com.armagancivelek.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.armagancivelek.model.Country

@Dao
interface CountryDao {
    //Data Access Object
    @Insert
    suspend fun insertAll(vararg countries: Country): List<Long>
    //insertall->INSERT INTO

    //suspend->coroutine,pause&resume
    @Query("Select * FROM  country")
    suspend fun getAllCountries(): List<Country>

    @Query("Select * FROM  Country WHERE uuid=:countryId")
    suspend fun getCountry(countryId: Int): Country

}