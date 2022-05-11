package com.mnp.covidtracker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

open class StatesAdapter(private val context: Context,
                         private val states: ArrayList<StatesList>):
RecyclerView.Adapter<StatesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.stateviserow, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentState = states[position].states
        holder.state.text = currentState
        holder.itemView.setOnClickListener{
            val intent = Intent(context, StateViseActivity::class.java)
            intent.putExtra("name", currentState)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return states.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val state = itemView.findViewById<View>(R.id.text1) as TextView
    }
}