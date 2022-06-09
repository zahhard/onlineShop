package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import model.SliderItem

class SliderAdapter (var fragment: Fragment, var urlList: ArrayList<String>, var viewPager2: ViewPager2)
    : RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageSlider)

        fun setImage(url: String, imageViewItem: ImageView){
            Glide.with(fragment)
                .load(url)
                .placeholder(R.drawable.ic_baseline_error_outline_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(imageViewItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
    return SliderViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.slide_item,
            parent,
            false
        )
    )}

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
    holder.setImage(urlList[position] ,holder.imageView)}

    override fun getItemCount(): Int {
    return urlList.size}


}