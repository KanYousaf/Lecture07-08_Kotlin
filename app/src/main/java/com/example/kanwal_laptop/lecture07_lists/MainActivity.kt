package com.example.kanwal_laptop.lecture07_lists

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ScrollView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_layout_list.*
import kotlinx.android.synthetic.main.custom_layout_list.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {
    //    private var myListAdapter: ArrayAdapter<String>? = null
    private val myFavSeasonList: MutableList<String> = mutableListOf(
        "Silicon Valley",
        "Game of Thrones",
        "Big Bang Theory",
        "Prison Break",
        "Citizen Khan",
        "Divinci Demons",
        "Mr. Robot",
        "House of Cards",
        "Sherlock Holmes"
    )
    private final val myFavSeasonImage: MutableList<Int> = mutableListOf(
        R.drawable.siliconvalley,
        R.drawable.gameofthrones,
        R.drawable.bigbangtheory,
        R.drawable.prisonbreak,
        R.drawable.citizenkhan,
        R.drawable.divincidemons,
        R.drawable.mrrobot,
        R.drawable.houseofcards,
        R.drawable.sherlockholmes
    )

    //initialize custom adapter
    private lateinit var myCustomAdapter: CustomAdapter
/*
    //use list initialization to store GRE words
//    private var wordList : List<String> = listOf()

        private var myListAdapter: ArrayAdapter<String>? = null
    //or initialize array list for storing GRE words
    private var wordList = ArrayList<String>()
    private var DefinitionList = ArrayList<String>()
    private var wordToDefinition = HashMap<String, String>()
    private var wordToDisplay: String = ""
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            loadSeasonData(this)
        }


//        seasonListWithAdapter()
        seasonListWithCustomAdapter()
        seasonListRemove()
//        readFromGREFile()
//        setUpEntries()
//        list_of_seasons.setOnItemClickListener { parent, view, position, id ->
//            val selectedDefinition: String = parent.getItemAtPosition(position).toString()
//            if (selectedDefinition.equals(wordToDefinition[wordToDisplay])) {
//                Toast.makeText(this, "You selected Right", Toast.LENGTH_SHORT).show()
//                readFromGREFile()
//                setUpEntries()
//            }
//        }

    }

/*
    fun seasonListWithAdapter() {
        // Pre-defined adapter properties
        myListAdapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1,
            myFavSeasonList
        )
        //attached adapter with list view
        list_of_seasons.adapter = myListAdapter
        //selecting any item from list to move to next activity with passing
        //season name to Details Activity
        list_of_seasons.setOnItemClickListener { parent, _, position, _ ->
            val season_name: String = parent.getItemAtPosition(position).toString()
            val myIntent: Intent = Intent(this, DetailsActivity::class.java)
            myIntent.putExtra("SeasonName", season_name)
            startActivity(myIntent)
        }
    }
*/

    fun seasonListWithCustomAdapter() {
        // Custom adapter properties
        myCustomAdapter = CustomAdapter(this, myFavSeasonList, myFavSeasonImage)
        //attached custom adapter with list view
        list_of_seasons.adapter = myCustomAdapter
        //selecting any item from list to move to next activity with passing
        //season name to Details Activity
        list_of_seasons.setOnItemClickListener { parent, view, position, _ ->
            val season_name: String = parent.getItemAtPosition(position).toString()
            val myIntent: Intent = Intent(this, DetailsActivity::class.java)
            myIntent.putExtra("SeasonName", season_name)
            myIntent.putExtra("SeasonRate", view.rate_season.rating)
            startActivity(myIntent)
        }
    }

    fun seasonListRemove() {
        //remove list item
        list_of_seasons.setOnItemLongClickListener { parent, view, position, id ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Message")
            builder.setMessage("Do You Want to Delete it" + parent.getItemAtPosition(position).toString())
            builder.setPositiveButton("No"){dialog, which ->
                dialog.dismiss()
            }
            builder.setNegativeButton("Yes"){dialog, which ->
                myFavSeasonList.removeAt(position)
                myFavSeasonImage.removeAt(position)
                // predefined adapter
//            myListAdapter!!.notifyDataSetChanged()
                // notify custom adapter about data change
                myCustomAdapter.notifyDataSetChanged()
            }

            builder.create().show()

            return@setOnItemLongClickListener true
        }
    }

    override fun onPause() {
        saveSeasonArrayData()
        Log.i("Test", "On Pause Called")
        super.onPause()
    }

    override fun onStop() {
        saveSeasonArrayData()
        Log.i("Test", "On Stop Called")
        super.onStop()
    }

    fun saveSeasonArrayData(): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("mySeasonDataFile",
                                                                        Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        //send the size of lists
        editor.putInt("seasonNameListSize", myFavSeasonList.size)
        editor.putInt("seasonImageListSize", myFavSeasonImage.size)

        for (i in 0..myFavSeasonList.size - 1) {
            editor.remove("seasonName$i")
            editor.putString("seasonName", myFavSeasonList.get(i))
        }

        for (j in 0..myFavSeasonImage.size - 1) {
            editor.remove("seasonImage$j")
            editor.putInt("seasonImage", myFavSeasonImage.get(j))
        }

        return editor.commit()
    }

    fun loadSeasonData(context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val seasonNameSize = sharedPreferences.getInt("seasonNameListSize", 0)
        val seasonImageSize = sharedPreferences.getInt("seasonImageListSize", 0)

        for (i in 0..seasonNameSize) {
            myFavSeasonList.add(sharedPreferences.getString("seasonName$i", null))
        }

        for (j in 0..seasonImageSize) {
            myFavSeasonImage.add(sharedPreferences.getInt("seasonImage$j", 0))
        }
    }
/*
    fun readFromGREFile() {
        val scanner: Scanner = Scanner(resources.openRawResource(R.raw.grewords))
        while (scanner.hasNextLine()) {
            val line: String = scanner.nextLine()
            val pieces = line.split("\t")
            if (pieces.size >= 2) {
                wordList.add(pieces[0])
                wordToDefinition.put(pieces[0], pieces[1])
            }
        }
    }

    fun setUpEntries() {
        val wordRandomIndex: Int = Random().nextInt(wordList.size)
        wordToDisplay = wordList[wordRandomIndex]
        wordToDisplayTV.text = wordToDisplay

        //clear the definition array list
        DefinitionList.clear()
        DefinitionList.add(wordToDefinition[wordToDisplay]!!)
        wordList.shuffle()
        for (otherWords in wordList.subList(0, 4)) {
            if (otherWords == wordToDisplay || DefinitionList.size == 5) {
                continue
            }
            DefinitionList.add(wordToDefinition[otherWords]!!)
        }
        myListAdapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1,
            DefinitionList
        )
        list_of_seasons.adapter = myListAdapter
    }

    */
}
