package com.example.helloandroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private lateinit var btnRed: Button
    private lateinit var btnBlue: Button
    private lateinit var btnGreen: Button
    private lateinit var btnYellow: Button
    private lateinit var btnPurple: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeButtons()
        setUpButtonClickEventListener()
    }

        fun initializeButtons() {
            btnRed = findViewById<Button>(R.id.btnRed)
            btnPurple = findViewById<Button>(R.id.btnPurple)
            btnYellow = findViewById<Button>(R.id.btnYellow)
            btnGreen = findViewById<Button>(R.id.btnGreen)
            btnBlue = findViewById<Button>(R.id.btnBlue)
        }
        /***
         *Function that sets click Listeners on each button
         */
        fun setUpButtonClickEventListener() {
            btnRed.setOnClickListener { navigateToSecondActivity(btnRed.text.toString()) }
            btnBlue.setOnClickListener { navigateToSecondActivity(btnBlue.text.toString()) }
            btnGreen.setOnClickListener { navigateToSecondActivity(btnGreen.text.toString()) }
            btnYellow.setOnClickListener { navigateToSecondActivity(btnYellow.text.toString()) }
            btnPurple.setOnClickListener { navigateToSecondActivity(btnPurple.text.toString()) }
        }

        /**
         * Navigate to SecondActivity and pass the button text as extra data
         * @param buttonText The text from the clicked button ( which is the color clicked)
         */
         fun navigateToSecondActivity(buttonText: String) {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("BUTTON_TEXT", buttonText)
            //This starts the SecondActivity
            startActivity(intent)
        }
    }



