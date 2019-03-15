package com.example.kanwal_laptop.lecture07_lists

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class CustomAdapter(context: Context,
                    var myFavSeasonList : MutableList<String>,
                    var myFavSeasonImages : MutableList<Int>) :
    ArrayAdapter<String>(context, R.layout.custom_layout_list, myFavSeasonList) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val rowView = layoutInflater.inflate(R.layout.custom_layout_list, parent, false)

        val textView:TextView = rowView.findViewById(R.id.display_season_names)
        val imageView: ImageView = rowView.findViewById(R.id.display_season_image)
        val ratingBar: RatingBar = rowView.findViewById(R.id.rate_season)

        textView.text = myFavSeasonList[position]
        imageView.setImageResource(myFavSeasonImages[position])
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            ratingBar.rating = rating
        }

        return rowView
    }

}