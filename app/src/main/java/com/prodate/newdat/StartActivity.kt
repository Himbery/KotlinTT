package com.prodate.newdat

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = getIntent().getStringExtra("url")
        if (url!=null){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        } else {
            showURL()
        }
    }
    fun showURL() {
        val ONE_MEGABYTE = (1024 * 1024).toLong()
        val storage = FirebaseStorage.getInstance()

        val configReference = storage.getReferenceFromUrl(getString(R.string.config_url))
        configReference.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val ConfigJson = String(bytes)
            checkConditions(ConfigJson)
        }.addOnFailureListener { exception -> exception.printStackTrace() }
    }

    private fun checkConditions(configJson: String) {
        var mustShowAds = false

        class ConfigItem {
            var URL: String? = null
            lateinit var show_in_countries: Array<String?>
            var enabled: Boolean = false
        }

        val list = ArrayList<ConfigItem>()
        try {
            val arr = JSONArray(configJson)
            for (i in 0 until arr.length()) {
                val obj = arr.get(i) as JSONObject

                val item = ConfigItem()
                item.enabled = java.lang.Boolean.parseBoolean(obj.get("enabled").toString())
                if (!item.enabled)
                    continue
                item.URL = obj.get("URL").toString()
                val countries = obj.getJSONArray("show_in_countries")
                item.show_in_countries = arrayOfNulls(countries.length())
                for (j in 0 until countries.length()) {
                    item.show_in_countries!![j] = countries.getString(j)
                }
                list.add(item)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val language = detectSIMCountry()
        if (!list.isEmpty()) {
            for (item in list) {
                if (Arrays.asList(*item.show_in_countries!!).contains(language.toUpperCase())) {
                    mustShowAds = true

                    val urlString = item.URL
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
                    startActivity(intent)

                }
            }
        }
        if (!mustShowAds) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    fun detectSIMCountry(): String {
        val telephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.simCountryIso.toUpperCase()
    }

    fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}