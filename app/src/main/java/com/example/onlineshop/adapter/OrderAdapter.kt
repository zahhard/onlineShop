package com.example.onlineshop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.data.network.ApiService
import com.example.onlineshop.model.LineItem
import com.example.onlineshop.model.ProduceItem
import java.math.BigDecimal

typealias updateOrder = (Int, Int, BigDecimal) -> Unit

class OrderAdapter(var list : ArrayList<Int> ,var name: String, var url : String, var fragment: Fragment, private var showFilmDetails: updateOrder) :
    ListAdapter<ProduceItem, OrderAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val  imageViewItemCategory = view.findViewById<ImageView>(R.id.imageView)
        val  cardView = view.findViewById<CardView>(R.id.cardView)
        val title = view.findViewById<TextView>(R.id.nnnnname)
        val count = view.findViewById<TextView>(R.id.count)
        val price = view.findViewById<TextView>(R.id.tv_price)
        val plus = view.findViewById<TextView>(R.id.plus)
        val mines = view.findViewById<TextView>(R.id.mines)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var count = 1  //list[position]

        holder.title.text = getItem(position).name
        holder.count.text = count.toString()
        holder.price.text = getItem(position).price
        if (count == 1){
            holder.mines.text = "🗑️"
        }
        else{
            holder.mines.text = "➖"
        }
        holder.plus.text = "➕"
        holder.plus.setOnClickListener {
            count ++
            holder.count.text = count.toString()
            holder.price.text =( holder.price.text.toString().toBigDecimal() + getItem(position).price.toBigDecimal() ).toString()
            showFilmDetails(getItem(position).id, count, getItem(position).price.toBigDecimal())
            if (count == 1){
                holder.mines.text = "🗑️"
            }
            else{
                holder.mines.text = "➖"
            }
        }
        holder.mines.setOnClickListener {
            count --
            holder.count.text = count.toString()
            holder.price.text =( holder.price.text.toString().toBigDecimal()  - getItem(position).price.toBigDecimal()  ).toString()
            showFilmDetails(getItem(position).id, count, getItem(position).price.toBigDecimal())
            if (count == 1){
                holder.mines.text = "🗑️"
            }
            else{
                holder.mines.text = "➖"
            }
        }

        Glide.with(fragment)
            .load(getItem(position).images[0].src)
            .placeholder(R.drawable.ic_baseline_downloading_24)
            .error(R.drawable.ic_baseline_error_outline_24)
            .into(holder.imageViewItemCategory)

        holder.itemView.setOnClickListener {
//            showFilmDetails(getItem(position).id, count, getItem(position).price.toBigDecimal())
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<ProduceItem>() {
        override fun areItemsTheSame(oldItem: ProduceItem, newItem: ProduceItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProduceItem, newItem:ProduceItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
}