package com.example.havefunapp.transport

import android.content.Context
import android.util.Log
import com.example.havefunapp.util.Util
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.koushikdutta.async.future.Future
import com.koushikdutta.ion.Ion

class MainTransport : IonMaster() {
    private val sendObject = JsonObject()


        fun getData(context: Context?, callback: IonMaster.IonCallback?): java.util.concurrent.Future<JsonObject>? {


            sendObject.addProperty("dataSource", "Cluster0")
            sendObject.addProperty("database", "todo")
            sendObject.addProperty("collection", "have_fun")

            val returnObj: Future<JsonObject> = Ion.with(context)
                .load(Util.getBaseUrl("findOne"))
                .setLogging("IONLOG", Log.DEBUG)
                .setHeader("Content-Type", "application/json")
                .setHeader("Access-Control-Request-Headers", "*")
                .setHeader("Accept", "application/json")
                .setJsonObjectBody(sendObject)
                .asJsonObject()
            returnObj.setCallback(getJsonFutureCallback(context!!, callback!!))
            return returnObj
        }

        fun updateUserSignUp(username:String,password:String,context: Context?, callback: IonMaster.IonCallback?): java.util.concurrent.Future<JsonObject>?{

            sendObject.addProperty("dataSource", "Cluster0")
            sendObject.addProperty("database", "todo")
            sendObject.addProperty("collection", "have_fun")
            val gson = Gson()

            // Buat JSON string
            val json = "{ \"_id\": { \"\$oid\": \"64898977a24739106ee829a4\" } }"

            // Parse JSON string menjadi JsonObject
            val jsonObject: JsonObject = gson.fromJson(json, JsonObject::class.java)

            // Ubah JsonObject menjadi JsonElement
            val jsonParser = JsonParser()
            val jsonElement: JsonElement = jsonParser.parse(json)

            // Gunakan jsonElement dalam objek sendObject

            sendObject.add("filter", jsonElement)


            // Buat JSON string
            val jsonString = "{ \"\$push\": { \"user\": { \"userId\": \"baru nih\", \"userName\": \"$username\",\"password\":\"$password\" } } }"

            // Buat JsonElement menggunakan JsonParser
            val jsonParsers = JsonParser()
            val jsonElements = jsonParsers.parse(jsonString)

            // Gunakan jsonElement sesuai kebutuhan
            // Contoh: Menggunakan dalam JsonObject sendObject


            sendObject.add("update", jsonElements.asJsonObject)
            Log.i("JSONUPDATE", "updateUserSignUp: "+sendObject)



            val returnObj: Future<JsonObject> = Ion.with(context)
                .load(Util.getBaseUrl("updateOne"))
                .setLogging("IONLOG", Log.DEBUG)
                .setHeader("Content-Type", "application/json")
                .setHeader("Access-Control-Request-Headers", "*")
                .setHeader("api-key", "6438f8553f4da8ec3781253b")
                .setJsonObjectBody(sendObject)
                .asJsonObject()


            return returnObj
        }


}