package com.armagancivelek.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.armagancivelek.countries.FeedFragmentDirections
import com.armagancivelek.countries.databinding.ItemCountryBinding
import com.armagancivelek.model.Country
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(private val countryList: ArrayList<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {


    class CountryViewHolder(var view: ItemCountryBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_country, parent, false)
        //  val binding=DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        val binding: ItemCountryBinding = ItemCountryBinding.inflate(inflater, parent, false)



        return CountryViewHolder(binding)


    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.country = countryList[position]
        holder.view.listener = this


    }

    fun updateCountryLİst(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return countryList.size

    }

    override fun onClicked(v: View) {
        val uuid = v.uuidtext.text.toString().toInt()
        val action = FeedFragmentDirections.navigateToCountryDetailFragment(uuid)
        Navigation.findNavController(v).navigate(action)

    }


}