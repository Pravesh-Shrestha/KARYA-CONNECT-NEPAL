package com.example.karyaconnectnepal

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.furkankaplan.fkblurview.FKBlurView

class LoginPage : AppCompatActivity() {

    private lateinit var glass: FKBlurView
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var radioclient: RadioButton
    private lateinit var radiofreelancer: RadioButton
    private lateinit var rememberMe: CheckBox
    private lateinit var forgotpassword: TextView

    private var isBlurred: Boolean = false // Flag to track blur state
    private var activeField: Int? = null // To track the currently active field

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

//        // Initialize views
//        glass = findViewById(R.id.glassFrame)
//
//// Limit the dimensions of the FKBlurView
//        glass.layoutParams = glass.layoutParams.apply {
//            width = resources.displayMetrics.widthPixels
//            height = resources.displayMetrics.heightPixels / 2 // Half the screen height
//        }

        username = findViewById(R.id.userNameText)
        password = findViewById(R.id.passText)
        radioclient = findViewById(R.id.radioClient)
        radiofreelancer = findViewById(R.id.radioFreelancer)
        rememberMe = findViewById(R.id.rememberBox)
        forgotpassword = findViewById(R.id.forgotPassword)

        // Handle field clicks
        username.setOnClickListener {
            handleFieldClick(R.id.userNameText)
        }
        password.setOnClickListener {
            handleFieldClick(R.id.passText)
        }

        // Adjust window insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleFieldClick(fieldId: Int) {
        if (activeField != fieldId) {
            showBlur()
            activeField = fieldId
        }
    }

    private fun hideBlur() {
        if (isBlurred) {
            glass.visibility = View.GONE
            isBlurred = false
            activeField = null
            Log.d("LoginPage", "Blur hidden")
        }
    }
    private fun showBlur() {
        if (!isBlurred) {
            glass.visibility = View.VISIBLE

            // Apply optimized blur effect
            try {
                glass.setBlur(this@LoginPage, glass, 25) // Adjust blur level
                glass.scaleX // Optional: Scale bitmap
            } catch (e: Exception) {
                Log.e("LoginPage", "Error applying blur: ${e.message}")
            }

            isBlurred = true
        }
    }



}
