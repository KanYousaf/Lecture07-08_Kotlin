package com.example.kanwal_laptop.lecture07_lists

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    private var myFavSeasonDetails : MutableList<String> = mutableListOf("Silicon Valley","Silicon Valley is about Richard Henricks and his company named pied piper",
        "Game of Thrones","Game of Thrones is a fantasy drama",
        "Big Bang Theory","Big Bang Theory is scientific sci-fi drama",
        "Prison Break","Prison Break is about the story of Micheal Scofield and his brother",
        "Citizen Khan","Citizen Khan: Mr. Khan , a pakistani citizen living abroad in UK",
        "Divinci Demons","Divinci Demons: Story about Leonardo Divinci",
        "Mr. Robot","Mr. Robot is about hacker's story and how he wants to take revenge on society",
        "House of Cards","House of Cards is about underwood and his compaign to become president of USA",
        "Sherlock Holmes","Sherlock Holmes: Detective to solve crimes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val seasonNameReceived = intent.getStringExtra("SeasonName")
        val seasonRateReceived = intent.getFloatExtra("SeasonRate", 0.0f)
        season_name.append(seasonNameReceived + " with the Rating of : "+ seasonRateReceived)

        for(i in 0..myFavSeasonDetails.size-1){
            if(myFavSeasonDetails[i].equals(seasonNameReceived)){
                displaySeasonDetails.text = myFavSeasonDetails[i+1]
            }
        }
    }
}