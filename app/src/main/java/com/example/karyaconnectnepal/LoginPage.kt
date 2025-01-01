package com.example.karyaconnectnepal

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//import com.github.furkankaplan.fkblurview.FKBlurView

class LoginPage : AppCompatActivity() {

//    lateinit var glass: FKBlurView
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var radioclient: RadioButton
    lateinit var radiofreelancer: RadioButton
    lateinit var rememberMe: CheckBox
    lateinit var forgotpassword: TextView
//    var blurLevel: Int = 50
//    var isBlurred: Boolean= false // Flag to track blur state
//    var activeField: Int? = null // To track the currently active field

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)

//        glass=findViewById(R.id.glassFrame)
//        glass.setBlur(this,glass,80)



        username= findViewById(R.id.userNameText)
        password= findViewById(R.id.passText)

        radioclient=findViewById(R.id.radioClient)
        radiofreelancer= findViewById(R.id.radioFreelancer)
        rememberMe= findViewById(R.id.rememberBox)
        forgotpassword= findViewById(R.id.forgotPassword)

//        username.setOnClickListener {
//            handleFieldClick(R.id.userNameText)
//        }
//
//        password.setOnClickListener {
//            handleFieldClick(R.id.passText)
//        }


//        username.setOnFocusChangeListener{_, hasFocus->
//            if(hasFocus) showBlur()
//            else hideBlur()
//        }
//
//        password.setOnFocusChangeListener{_, hasFocus->
//            if(hasFocus) showBlur()
//            else hideBlur()
//        }

//        rememberMe.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) showBlur()
//            else hideBlur()
//        }

//        radioclient.setOnCheckedChangeListener { _, _ ->
//            showBlur() // Show blur when a radio button is selected
//        }
//        radiofreelancer.setOnCheckedChangeListener { _, _ ->
//            showBlur() // Show blur when a radio button is selected
//        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

//    private fun handleFieldClick(fieldId: Int) {
//        if (activeField != fieldId){
//            showBlur()
//            activeField = fieldId
//        }
//
//    }
//
//
//    private fun hideBlur() {
//        if (isBlurred){
//            glass.visibility = View.GONE
//            isBlurred = false
//            activeField = null
//        }
//
////        glass.visibility = View.GONE
//    }
//
//    private fun showBlur() {
//        if (!isBlurred){
//            glass.visibility = View.VISIBLE
//            glass.setBlur(this@LoginPage, glass,50)
//            isBlurred = true
//        }

//        glass.visibility = View.VISIBLE
//        glass.setBlur(this@LoginPage,glass,80)


//    }

}