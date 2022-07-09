package data.network

import android.graphics.Color
import com.example.onlineshop.R

class NetworkParams {
    companion object {
        const val BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/"
        const val API_KEY = "b8fb74a7f7ebe3f2402f6de80059d5a5"
        const val consumer_key = "ck_63f4c52da932ddad1570283b31f3c96c4bd9fd6f"
        const val consumer_secret = "cs_294e7de35430398f323b43c21dd1b29f67b5370b"
        var  colorId = 0
        var cartList = ArrayList<Int>()
    }
}