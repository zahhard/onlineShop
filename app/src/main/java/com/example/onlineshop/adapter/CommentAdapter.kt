package adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.adapter.showInsideOfCategory
import com.example.onlineshop.model.CommentsItem
import org.w3c.dom.Comment
//
//class gfgViewAdapter(productId: Int ,context: Context, list: ArrayList<CommentsItem>) :
//    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    companion object {
//        const val THE_FIRST_VIEW = 1
//        const val THE_SECOND_VIEW = 2
//    }
//
//    private val yourContext: Context = context
//    var list: ArrayList<CommentsItem> = list
//
//    private inner class GfgViewOne(itemView: View) :
//        RecyclerView.ViewHolder(itemView) {
//        var gfgText: TextView = itemView.findViewById(R.id.gfgTextView)
//        fun bind(position: Int) {
//            val recyclerViewModel = list[position]
//            gfgText.text = recyclerViewModel.textData
//        }
//    }
//
//    private inner class View2ViewHolder(itemView: View) :
//        RecyclerView.ViewHolder(itemView) {
//        var gfgText: TextView = itemView.findViewById(R.id.gfgTextView)
//        fun bind(position: Int) {
//            val recyclerViewModel = list[position]
//            gfgText.text = recyclerViewModel.textData
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        if (viewType == VIEW_TYPE_ONE) {
//            return GfgViewOne(
//                LayoutInflater.from(context).inflate(R.layout.gfgParentOne, parent, false)
//            )
//        }
//        return View2ViewHolder(
//            LayoutInflater.from(context).inflate(R.layout.item_view_2, parent, false)
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (list[position].product_id === VIEW_TYPE_ONE) {
//            (holder as GfgViewOne).bind(position)
//        } else {
//            (holder as View2ViewHolder).bind(position)
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return list[position].id
//    }
//}
//

class CommentAdapter(var fragment: Fragment, private var showFilmDetails: showInsideOfCategory) :
    ListAdapter<CommentsItem, CommentAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val tvName = view.findViewById<TextView>(R.id.reviewer_name)
        val tvCommentText = view.findViewById<TextView>(R.id.comment_text)
        val ratingBar = view.findViewById<RatingBar>(R.id.comment_rate)
        val tvDate = view.findViewById<TextView>(R.id.comment_date)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvName.text = getItem(position).reviewer
        holder.tvCommentText.text = HtmlCompat.fromHtml(getItem(position).review, HtmlCompat.FROM_HTML_MODE_LEGACY)
        holder.ratingBar.rating = getItem(position).rating.toFloat()
        holder.tvDate.text = getItem(position).date_created
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CommentsItem>() {
        override fun areItemsTheSame(oldItem: CommentsItem, newItem: CommentsItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CommentsItem, newItem: CommentsItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
}