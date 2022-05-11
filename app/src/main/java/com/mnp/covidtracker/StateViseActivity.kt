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
import com.mnp.covidtracker.databinding.ActivityStateviseBinding
import org.json.JSONException
import org.json.JSONObject

class StateViseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStateviseBinding

    private var covidData: CovidData? = null
    var covidList= ArrayList<CovidData>()
    private var adapter: Adapter? = null
    private var stateName: String? = null
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStateviseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        stateName = intent.getStringExtra("name")
        binding.statehead.text = stateName!!.uppercase()
        adapter= Adapter(covidList, this)
        binding.recyclerview2.adapter = adapter
        binding.recyclerview2.layoutManager = LinearLayoutManager(this)
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
                    while (keys.hasNext()) {
                        val key = keys.next()
                        if (key == stateName) {
                            val obj1 = `object`.getJSONObject(key)
                            val obj2 = obj1.getJSONObject("districtData")
                            val subKeys = obj2.keys()
                            while (subKeys.hasNext()) {
                                val subKey = subKeys.next()
                                val obj3 = obj2.getJSONObject(subKey)
                                val obj4 = obj3.getJSONObject("delta")
                                val active = obj3.getString("active")
                                val confirmed = obj3.getString("confirmed")
                                val deceased = obj3.getString("deceased")
                                val recovered = obj3.getString("recovered")
                                covidData = CovidData(subKey, active, confirmed, deceased, recovered)
                                covidList.add(covidData!!)
                            }
                        }
                    }
                    adapter = object : Adapter(covidList, this) {}
                    binding.recyclerview2.adapter = adapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error -> // In case of error it will run
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        }
        val reqQueue = Volley.newRequestQueue(this)
        reqQueue.add(req)
    }
}