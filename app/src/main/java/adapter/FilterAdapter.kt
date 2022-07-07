package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.R
import model.Color2

class FilterAdapter(var fragment: Fragment, private var showFilmDetails: showInsideOfCategory) :
    ListAdapter<Color2, FilterAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val title: CheckBox = view.findViewById(R.id.checkBox)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.title.text = getItem(position).name


        holder.title.setOnClickListener {
            showFilmDetails(getItem(position).id)
            holder.title.isChecked = true
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Color2>() {
        override fun areItemsTheSame(oldItem: Color2, newItem: Color2): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Color2, newItem: Color2): Boolean {
            return oldItem.id == newItem.id
        }
    }
}