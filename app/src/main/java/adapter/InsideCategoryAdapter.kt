package adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import model.ProduceItem

class InsideCategoryAdapter(var fragment: Fragment, private var showFilmDetails: showInsideOfCategory) :
    ListAdapter<ProduceItem, InsideCategoryAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val tvName = view.findViewById<TextView>(R.id.item_name)
        val tvRating = view.findViewById<TextView>(R.id.rating)
        val tvPrice = view.findViewById<TextView>(R.id.price)
        val imageView = view.findViewById<ImageView>(R.id.item_category)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.inside_category_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            Glide.with(fragment)
                .load(getItem(position).images[0].src)
                .placeholder(R.drawable.ic_baseline_error_outline_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(holder.imageView)
        }catch (e: Exception){
            Log.d("ddd", "exeption")
        }

        holder.tvName.text = getItem(position).name
        holder.tvPrice.text = getItem(position).price
        holder.tvRating.text = getItem(position).average_rating.toString()
        holder.itemView.setOnClickListener {
            showFilmDetails(getItem(position).id)
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<ProduceItem>() {
        override fun areItemsTheSame(oldItem: ProduceItem, newItem: ProduceItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ProduceItem, newItem: ProduceItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
}