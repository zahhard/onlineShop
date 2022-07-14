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
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.R
import com.example.onlineshop.adapter.showInsideOfCategory
import com.example.onlineshop.model.CommentsItem
import org.w3c.dom.Comment

class CommentAdapter(var email : String , var fragment: Fragment, private var showFilmDetails: showInsideOfCategory) :
    ListAdapter<CommentsItem, CommentAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val tvName = view.findViewById<TextView>(R.id.reviewer_name)
        val tvCommentText = view.findViewById<TextView>(R.id.comment_text)
        val ratingBar = view.findViewById<RatingBar>(R.id.comment_rate)
        val tvDate = view.findViewById<TextView>(R.id.comment_date)
        val remove = view.findViewById<TextView>(R.id.remove)
        val eddit = view.findViewById<TextView>(R.id.edit)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (getItem(position).reviewer_email == email){
            holder.remove.isGone = false
            holder.eddit.isGone = false
        }
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