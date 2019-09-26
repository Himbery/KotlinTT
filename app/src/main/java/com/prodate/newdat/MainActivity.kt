package com.prodate.newdat

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

//    lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        toggle.setHomeAsUpIndicator(R.drawable.menu)

        navView.setNavigationItemSelectedListener(this)

        val radioGr: View = findViewById(R.id.rgroup)
        val rb1: RadioButton = findViewById(R.id.one_rb)
        val rb2: RadioButton = findViewById(R.id.two_rb)
        val rb3: RadioButton = findViewById(R.id.three_rb)
        val img: ImageView = findViewById(R.id.image)

        rb1.setOnClickListener(this)
        rb2.setOnClickListener(this)
        rb3.setOnClickListener(this)
        img.setImageResource(R.drawable.main_photo_1)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val oneIntent : Intent = Intent(this, Activity_One::class.java)
                startActivity(oneIntent)
            }
            R.id.nav_gallery -> {
                val twoIntent = Intent(this, Activity_Two::class.java)
                startActivity(twoIntent)
            }
            R.id.nav_slideshow -> {
                val threeIntent = Intent(this, Activity_Three::class.java)
                startActivity(threeIntent)
            }
            R.id.nav_tools -> {
                val fourIntent = Intent(this, Activity_Four::class.java)
                startActivity(fourIntent)
            }
            R.id.nav_share -> {
                val fiveIntent = Intent(this, Activity_Five::class.java)
                startActivity(fiveIntent)
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onClick(v: View?) {
            when(v!!.id) {
                R.id.one_rb -> {
                    image.setImageResource(R.drawable.main_photo_1)
                }
                R.id.two_rb -> {
                    image.setImageResource(R.drawable.main_photo_2)
                }
                R.id.three_rb -> {
                    image.setImageResource(R.drawable.main_photo_3)
                }
            }
    }

}
