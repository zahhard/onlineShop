package adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import data.network.NetworkParams
import model.Category

typealias showInsideOfCategory = () -> Unit


class CategoryAdapter(var fragment: Fragment, private var showFilmDetails: showInsideOfCategory) :
    ListAdapter<Category, CategoryAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val  imageViewItemCategory = view.findViewById<ImageView>(R.id.item_category)
        val  cardView = view.findViewById<CardView>(R.id.cardView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ccategory_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(fragment)
            .load(getItem(position).image.src)
            .placeholder(R.drawable.ic_baseline_error_outline_24)
            .error(R.drawable.ic_baseline_error_outline_24)
            .into(holder.imageViewItemCategory)

        val  colorList = arrayListOf("#E2B646", "#9DE246", "#46E2A1", "#468EE2", "#E24646")


        if (NetworkParams.colorId < 5){
            holder.cardView.setCardBackgroundColor(Color.parseColor(colorList[NetworkParams.colorId]));
            NetworkParams.colorId ++
        }
        else
            NetworkParams.colorId = 0


        holder.itemView.setOnClickListener {
            showFilmDetails()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }
    }
}