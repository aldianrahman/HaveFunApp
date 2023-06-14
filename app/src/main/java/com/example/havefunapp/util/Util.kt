package com.example.havefunapp.util

import android.content.Context
import android.net.ConnectivityManager

class Util {

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
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
    }

}