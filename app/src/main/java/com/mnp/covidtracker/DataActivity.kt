package com.mnp.covidtracker

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.mnp.covidtracker.databinding.ActivityDataBinding
import org.json.JSONException
import org.json.JSONObject

class DataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataBinding
    var distName: String? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        distName = intent.getStringExtra("distName")
        fetchData()
    }
    private fun fetchData() {
        val url = "https://data.covid19india.org/state_district_wise.json"
        val req = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val `object` = JSONObject(response)
                    val states = `object`.keys()
                    while (states.hasNext()){
                        val state = states.next()
                        val obj1 = `object`.getJSONObject(state)
                        val obj2 = obj1.getJSONObject("districtData")
                        val districts = obj2.keys()
                        while(districts.hasNext()){
                            val dist =districts.next()
                            if(dist == distName){
                                val obj3 = obj2.getJSONObject(dist)
                                val obj4 = obj3.getJSONObject("delta")
                                val active  = obj3.getString("active")
                                val confirmed = obj3.getString("confirmed")
                                val migratedOther = obj3.getString("migratedother")
                                val deceased = obj3.getString("deceased")
                                val recovered = obj3.getString("recovered")
                                val deltaConfirmed = obj4.getString("confirmed")
                                val deltaDeceased = obj4.getString("deceased")
                                val deltaRecovered = obj4.getString("recovered")

                                binding.dataActive.text = active
                                binding.dataState.text = state
                                binding.dataDistrict.text = dist
                                binding.dataConfirm.text = confirmed
                                binding.migratedOther.text = migratedOther
                                binding.dataDeceased.text = deceased
                                binding.dataRecovered.text = recovered
                                binding.deltaConfirmed.text = deltaConfirmed
                                binding.deltaDeceased.text = deltaDeceased
                                binding.deltaRecovered.text = deltaRecovered
                                break
                            }
                        }
                    }
                }catch (e: JSONException){
                    e.printStackTrace()
                }
            }){error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        }
        val reqQueue = Volley.newRequestQueue(this)
        reqQueue.add(req)
    }
}