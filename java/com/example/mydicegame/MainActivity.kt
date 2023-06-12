package com.example.mydicegame
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.PopupWindow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get the receive number of wins and loses values from newGamActivity
        var humanWins = intent.getIntExtra("H_wins",0)
        var computerWins = intent.getIntExtra("C_wins",0)

        //create about button and about button onclickListener
        val aboutButton = findViewById<Button>(R.id.about_button)
        aboutButton.setOnClickListener {
            showPopup()
        }
        //create new game button and about button onclickListener
        val button02 = findViewById<Button>(R.id.new_game_button)
        button02.setOnClickListener {
            val intent = Intent(this, newGame::class.java)
            intent.putExtra("H_wins", humanWins) // pass the value in humanWins
            intent.putExtra("C_wins", computerWins) // pass the value in computerWins
            startActivity(intent)
        }
    }
    fun showPopup() {
        // inflate the layout for the popup window
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.about_popup, null)

        // create the popup window
        val popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        // set a background color for the popup window
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))

        // show the popup window
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

        // handle the close button click event
        val closeButton = popupView.findViewById<Button>(R.id.popup_button)
        closeButton.setOnClickListener {
            popupWindow.dismiss()
        }
    }
}