package com.egci428.project.Data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egci428.project.R

class AttractionAdapter (private val attractionList: ArrayList<Attractions>, private val context: Context, clickListener: ClickListener): RecyclerView.Adapter<AttractionAdapter.CookieViewHolder>() {
    private var clickListener: ClickListener = clickListener
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): CookieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return CookieViewHolder(itemView)
    }
//https://youtu.be/Y2vnFSdDAeQ?t=2433
    override fun onBindViewHolder(holder: CookieViewHolder, position: Int) {//set items parameter in row
        var attractionsModel = attractionList.get(position)
        holder.txtName.text = attractionList[position].name
        holder.txtLocation.text = attractionList[position].location
        holder.txtRating.rating = attractionList[position].rating.toFloat()
        //holder.txtImage.setImageResource(context.resources.getIdentifier(attractionList[position].imageAddr,"drawable",context.packageName))

        holder.itemView.setOnClickListener{
            clickListener.clickedItem(attractionsModel)
        }
    }

    override fun getItemCount(): Int {
        return attractionList.size
    }

    interface ClickListener{
        fun clickedItem(attractionsModel: Attractions)
    }

    class CookieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {//declare items in row
        var txtName = itemView.findViewById<TextView>(R.id.txtName)
        var txtImage = itemView.findViewById<ImageView>(R.id.txtImage)
        var txtLocation = itemView.findViewById<TextView>(R.id.txtLocation)
        var txtRating = itemView.findViewById<RatingBar>(R.id.txtRating)
    }
}