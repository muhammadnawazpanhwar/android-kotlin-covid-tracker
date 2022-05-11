package com.mnp.covidtracker

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mnp.covidtracker.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var statesadapter: StatesAdapter? = null
    var state: StatesList? = null
    var stateList = ArrayList<StatesList>()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        statesadapter = StatesAdapter(this, stateList)
        binding.recyclerview.adapter = statesadapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        fetchData()

    }

    private fun fetchData() {
        val url = "https://data.covid19india.org/state_district_wise.json"
        val req = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val `object` = JSONObject(response)
                    val keys = `object`.keys()
                    var i = 1
                    while (keys.hasNext()) {
                        val key = keys.next()
                        if (i == 1) {
                            i = 0
                            continue
                        }
                        state = StatesList(key)
                        stateList.add(state!!)
                        i = 0
                    }
                    statesadapter = object : StatesAdapter(this@MainActivity, stateList) {}
                    binding.recyclerview.adapter = statesadapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error -> // In case of error it will run
            Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
        }
        val reqQueue = Volley.newRequestQueue(this)
        reqQueue.add(req)
    }
}