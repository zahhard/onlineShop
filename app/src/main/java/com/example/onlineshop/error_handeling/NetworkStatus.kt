package com.example.onlineshop.error_handeling

import android.view.View
import com.example.onlineshop.R
import com.example.onlineshop.databinding.ErrorHandelingBinding
import com.example.onlineshop.model.Status


class NetworkStatusViewHandler(
    status: Status?,
    viewMain: View,
    viewStatus: ErrorHandelingBinding ,
    onRefreshPageClick: (() -> Unit),
    message: String
) {
    init {
        when (status) {
            Status.LOADING -> {
                viewMain.visibility = View.GONE
                viewStatus.lStatus.visibility = View.GONE
                viewStatus.imageViewStatus.let { imageView ->
                    imageView.visibility = View.VISIBLE
//                    imageView.setImageResource(R.drawable.)
                }
            }
            Status.NETWORK_ERROR -> {
                viewMain.visibility = View.GONE
                viewStatus.tvStatusMessage.text = message
                viewStatus.imageViewStatus.let { imageView ->
                    imageView.visibility = View.VISIBLE
//                    imageView.setImageResource(R.drawable.ic_no_internet)
                }
                viewStatus.lStatus.let { tv ->
                    tv.visibility = View.VISIBLE
                    tv.setOnClickListener {
                        onRefreshPageClick()
                    }
                }
            }
            Status.DONE -> {
                viewMain.visibility = View.VISIBLE
                viewStatus.root.visibility = View.GONE
            }
            else -> {
                viewMain.visibility = View.GONE
                viewStatus.tvStatusMessage.text = message
                viewStatus.lStatus.visibility = View.VISIBLE
                viewStatus.imageViewStatus.let { imageView ->
                    imageView.visibility = View.VISIBLE
//                    imageView.setImageResource(
////                        if (message == REQUEST_NOT_FOUND)
////                            R.drawable.ic_search_off
////                        else R.drawable.ic_server_error
//                    )
                }
                viewStatus.lStatus.let { tv ->
                    tv.visibility = View.VISIBLE
                    tv.setOnClickListener {
                        onRefreshPageClick()
                    }
                }
            }
        }
    }
}