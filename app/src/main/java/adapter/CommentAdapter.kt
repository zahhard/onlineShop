//package adapter
//
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.RatingBar
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.onlineshop.R
//import model.Comments
//import model.ProduceItem
//import org.w3c.dom.Comment
//
//
//class CommentAdapter() :
//    ListAdapter<Comment, CommentAdapter.ViewHolder>(DiffCallback) {
//
//
//    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//
//        val tvName = view.findViewById<TextView>(R.id.reviewer_name)
//        val tvCommentText = view.findViewById<TextView>(R.id.comment_text)
//        val ratingBar = view.findViewById<RatingBar>(R.id.comment_rate)
//        val tvDate = view.findViewById<TextView>(R.id.comment_date)
//    }
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.comment_item, parent, false)
//
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
////        holder.tvName.text = getItem(position).
//
//    }
//
//    companion object DiffCallback : DiffUtil.ItemCallback<ProduceItem>() {
//        override fun areItemsTheSame(oldItem: ProduceItem, newItem: ProduceItem): Boolean {
//            return oldItem.name == newItem.name
//        }
//
//        override fun areContentsTheSame(oldItem: ProduceItem, newItem: ProduceItem): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }
//}