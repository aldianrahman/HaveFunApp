package com.example.havefunapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

class Util {

    companion object {
        val popular: String = "pupular"
        val topRated: String = "top_rated"
        val upComing: String = "up_coming"
        val nowPlaying: String = "now_playing"
        val NamePref: String? = "pref"
        const val appName: String = "Have Fun App"
        val RememberME: String? = "remember_me"
        val emailUser: String? = "email_user"
        val nameUser: String? = "name_user"
        val idlUser: String? = "id_user"

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }

        fun exitToApp(
            exitApp:(Boolean)-> Unit
        ){

        }

        fun getBaseUrl(
            function: String
        ): String? {
            val baseUrl = "https://ap-southeast-1.aws.data.mongodb-api.com/app/data-oukpf/endpoint/data/v1"
            val findOne = "/action/findOne"
            val updateOne = "/action/updateOne"
            val API_KEY = "6438f8553f4da8ec3781253b"

            return when (function) {
                "findOne" -> {
                    baseUrl+findOne
                }
                "updateOne" -> {
                    baseUrl+updateOne
                }
                else -> ({}).toString()
            }

//            return if (FlavorsConstant.TYPE === FlavorsConstant.Type.DEVELOPMENT || FlavorsConstant.TYPE === FlavorsConstant.Type.DEMO) {
//                "http://202.152.41.162:5580/raksa.claim/index.php/api/service/mobileService"
//            } else if (FlavorsConstant.TYPE === FlavorsConstant.Type.STAGING) {
//                "https://raksa-service.com/raksa.claim-testing/index.php/api/service/mobileService"
//            } else {
//                "https://raksa-service.com/raksa.claim/index.php/api/service/mobileService"
//            }
        }

        fun toastToText(context: Context, s: String) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
        }

        fun getFilm(
            type: String,
            i: Int,
            query: String?
        ): String {
            var url =""
            if (type == popular && i != 0){
                url = "https://api.themoviedb.org/3/movie/popular?api_key=8af32cafa0a61c0821df743b90b4d56e&page=$i"
            }else if(type == topRated) run {
                url = "https://api.themoviedb.org/3/movie/top_rated?api_key=b8344cb11b1112f5b64098c48b4e1b7d&page=$i"
            }else if(type == upComing){
                url ="https://api.themoviedb.org/3/movie/upcoming?api_key=b8344cb11b1112f5b64098c48b4e1b7d&page=$i"
            }
            else if(type == nowPlaying){
                url ="https://api.themoviedb.org/3//movie/now_playing?api_key=b8344cb11b1112f5b64098c48b4e1b7d&page=$i"
            }
            else{
                url = "https://api.themoviedb.org/3/search/movie?api_key=b8344cb11b1112f5b64098c48b4e1b7d&query=$query"
            }



            return url
        }
    }

}