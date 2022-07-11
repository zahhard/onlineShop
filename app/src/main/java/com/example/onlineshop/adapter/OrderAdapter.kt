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
import com.example.onlineshop.R
import com.example.onlineshop.model.LineItem


class OrderAdapter(var name: String, var url : String, var fragment: Fragment, private var showFilmDetails: showInsideOfCategory) :
    ListAdapter<LineItem, OrderAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val  imageViewItemCategory = view.findViewById<ImageView>(R.id.item_category)
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

//        holder.title.text = getItem(position).line_items[0]
        holder.count.text = getItem(position).quantity.toString()
//        holder.price.text = getItem(position).quantity

        holder.plus.setOnClickListener {

        }
        holder.plus.setOnClickListener {

        }

//        Glide.with(fragment)
//            .load(getItem(position).image.src)
//            .placeholder(R.drawable.ic_baseline_error_outline_24)
//            .error(R.drawable.ic_baseline_error_outline_24)
//            .into(holder.imageViewItemCategory)

        holder.itemView.setOnClickListener {
            showFilmDetails(getItem(position).product_id)
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<com.example.onlineshop.model.LineItem>() {
        override fun areItemsTheSame(oldItem: com.example.onlineshop.model.LineItem, newItem: com.example.onlineshop.model.LineItem): Boolean {
            return oldItem.product_id == newItem.product_id
        }

        override fun areContentsTheSame(oldItem: com.example.onlineshop.model.LineItem, newItem: com.example.onlineshop.model.LineItem): Boolean {
            return oldItem.product_id == newItem.product_id
        }
    }
}