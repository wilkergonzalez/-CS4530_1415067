package com.example.helloandroid

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.window.OnBackInvokedDispatcher

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        initializeViews()
        displayPassedButtonText()
        setupBackButton()
    }
    private lateinit var tvSelectedButton: TextView
    private lateinit var backButton: Button

    fun initializeViews() {
        tvSelectedButton = findViewById<TextView>(R.id.tvSelectedButton)
        backButton = findViewById<Button>(R.id.btnBack)
    }
    fun displayPassedButtonText() {
        val buttonText = intent.getStringExtra("BUTTON_TEXT")
        tvSelectedButton.text = buttonText ?: "Wrong"
    }
    fun setupBackButton() {
        backButton.setOnClickListener {
            finish()
        }
    }
}