package com.codekidlabs.logboard

import android.app.Activity
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast


/**
 * @class Loboard class for receiving uncaught exceptions
 */

open class Logboard : Thread.UncaughtExceptionHandler {

    open var TAG : String = javaClass.name

    var showDialogOnCrash : Boolean = false
    var activity : Activity? = null
    var appIcon: Drawable? = null
    var tipIconColor : Int = 0

    var sendListener : Logboard.OnSendListener? = null

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        if(!showDialogOnCrash)
            throwable.printStackTrace()
        else
            show()
    }

    fun show() {

        Dialog().with(activity!!).setDeveloperName(activity!!.getString(R.string.app_name)).show(this)
    }



    companion object {

        private var exceptionHandler: Thread.UncaughtExceptionHandler? = null

    }

    fun init(showDialogOnCrash : Boolean, activity: Activity) {
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler()


        // initializers
        this.showDialogOnCrash = showDialogOnCrash
        this.activity = activity
    }

    /*
    * @interface
    * */

    interface OnSendListener {
        fun onSend(logcat : String)
    }



    private class Dialog {

        private var dialog: AlertDialog? = null
        private var activity : Activity? = null
        private var cancelable : Boolean = true
        private var  devName: String = ""
        var logcat : Logcat? = null


        fun with(activity: Activity) : Dialog {
            this.activity = activity

            return this
        }


        fun setDeveloperName(devName : String) : Dialog {
            this.devName = devName

            return this
        }

        fun isCancelable(cancelable : Boolean) : Dialog {
            this.cancelable = cancelable

            return this
        }


        private fun buildDialog(v: View) : AlertDialog {

            val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
                    .setView(v)
                    .setCancelable(cancelable)

            dialog = builder.create()

            return dialog!!

        }



        private fun inflateView(logboard: Logboard): View {
            val inflator : LayoutInflater = LayoutInflater.from(activity)
            val myView : View = inflator.inflate(R.layout.logboard_dialog, null)

            initUi(myView, logboard)

            return myView
        }

        private fun initUi(myView: View, logboard: Logboard) {
            val appicon : ImageView = myView.findViewById(R.id.app_icon)
            val tipIcon : ImageView = myView.findViewById(R.id.tip_image)
            val sendButton : Button = myView.findViewById(R.id.send_button)
            val desc : EditText = myView.findViewById(R.id.problem_description)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tipIcon.imageTintList = ColorStateList.valueOf(logboard.tipIconColor)
            }

            if(logboard.appIcon != null) {
                appicon.setImageDrawable(logboard.appIcon)
                appicon.visibility = View.VISIBLE
            }

            // LOGCAT
            logcat = Logcat()
            logcat!!.description = desc.text.toString()

            sendButton.setOnClickListener({

                // if not send listener set call getDefaultSendListener()
                if(logboard.sendListener == null) {
                    Toast.makeText(activity!!, "You need to set-up OnSendListener.", Toast.LENGTH_SHORT).show()
                } else {
                    logboard.sendListener!!.onSend(logcat!!.getLogcat())
                }

                dialog!!.cancel()
            })


        }

        fun show(logboard: Logboard) {
            buildDialog(inflateView(logboard)).show()
        }
    }
}
