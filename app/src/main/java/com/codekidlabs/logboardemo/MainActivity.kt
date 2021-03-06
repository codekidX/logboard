package com.codekidlabs.logboardemo

import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.codekidlabs.logboard.Logboard
import android.content.ClipData
import android.widget.ImageView


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logboard = Logboard()
        logboard.init(true, this)

        Thread.setDefaultUncaughtExceptionHandler(logboard)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val appIcon = findViewById(R.id.app_icon) as ImageView
        appIcon.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.mipmap.ic_launcher))

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            logboard.appIcon = ContextCompat.getDrawable(applicationContext, R.mipmap.ic_launcher)
            logboard.tipIconColor = ContextCompat.getColor(applicationContext, R.color.colorPrimary)
            logboard.show()


            logboard.sendListener = object : Logboard.OnSendListener {

                // detailed log is obtained from logcat variable
                override fun onSend(logcat : String) {
                    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Logboard", logcat)
                    clipboard.primaryClip = clip

                    Toast.makeText(applicationContext, "Logboard copied to Clipboard", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // write it the kotlin way
        when(item.itemId) {
            R.id.action_settings -> return true
        }

        return super.onOptionsItemSelected(item)
    }
}
