package adapter

import android.graphics.Color
import android.provider.ContactsContract
import android.util.Log
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
import data.network.NetworkParams
import model.Category
import model.ProduceItem

class EachItemAdapter(var fragment: Fragment, private var showFilmDetails: showInsideOfCategory) :
    ListAdapter<ProduceItem, EachItemAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val imageView = view.findViewById<ImageView>(R.id.imageview)
        val tvPrice = view.findViewById<TextView>(R.id.tvprice)
        val tvName = view.findViewById<TextView>(R.id.tv_name)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)

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