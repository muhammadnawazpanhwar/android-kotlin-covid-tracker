package com.mnp.covidtracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

open class Adapter(private val covidData: ArrayList<CovidData>, var context: Context):
RecyclerView.Adapter<Adapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.state_details, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val distName: String = covidData[position].district
        holder.district.text = distName
        holder.recovered.text= "Recovered:" + covidData[position].recovered
        holder.deceased.text= "Deceased:" + covidData[position].deceased
        holder.active.text= "Active:" + covidData[position].active

        holder.itemView.setOnClickListener{
         val intent = Intent(context, DataActivity::class.java)
            intent.putExtra("distName", distName)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return covidData.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var district: TextView = itemView.findViewById<View>(R.id.state) as TextView
        var active: TextView = itemView.findViewById<View>(R.id.total) as TextView
        var deceased: TextView = itemView.findViewById<View>(R.id.deceased) as TextView
        var recovered: TextView = itemView.findViewById<View>(R.id.recovered) as TextView

    }
}