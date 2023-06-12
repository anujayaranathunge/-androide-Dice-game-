package com.example.mydicegame

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import kotlin.random.Random
import java.util.*

class newGame : AppCompatActivity() {

    private val random = Random
    private lateinit var throwButton: Button
    private lateinit var scoreButton: Button
    private lateinit var targetButton: Button
    private lateinit var scoreView: TextView
    private lateinit var targetView: TextView
    private lateinit var winsView: TextView

    private lateinit var hDice1: ImageView
    private lateinit var hDice2: ImageView
    private lateinit var hDice3: ImageView
    private lateinit var hDice4: ImageView
    private lateinit var hDice5: ImageView

    private var hDice1Selected = 0
    private var hDice2Selected = 0
    private var hDice3Selected = 0
    private var hDice4Selected = 0
    private var hDice5Selected = 0

    private var stopCdice1 =0
    private var stopCdice2 =0
    private var stopCdice3 =0
    private var stopCdice4 =0
    private var stopCdice5 =0

    private var humanDice1 = 0
    private var humanDice2 = 0
    private var humanDice3 = 0
    private var humanDice4 = 0
    private var humanDice5 = 0
    private var computerDice1 = 0
    private var computerDice2 = 0
    private var computerDice3 = 0
    private var computerDice4 = 0
    private var computerDice5 = 0
    private var humanTotal = 0
    private var computerTotal = 0
    private var rollCount = 0
    private var rerollCount = 0
    private var humanScore = 0
    private var computerScore = 0
    private var CRollsCount = 0
    private var newTarget = 101
    private var humanWins = 0
    private var computerWins = 0
    private var CRerolls = 0


    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        outState.putInt("humanTotal",humanTotal)
        outState.putInt("computerTotal",computerTotal)
        outState.putInt("newTarget",newTarget)
        outState.putInt("humanWins",humanWins)
        outState.putInt("computerWins",computerWins)

        outState.putInt("humanDice1",humanDice1)
        outState.putInt("humanDice2",humanDice2)
        outState.putInt("humanDice3",humanDice3)
        outState.putInt("humanDice4",humanDice4)
        outState.putInt("humanDice5",humanDice5)

        outState.putInt("computerDice1",computerDice1)
        outState.putInt("computerDice2",computerDice2)
        outState.putInt("computerDice3",computerDice3)
        outState.putInt("computerDice4",computerDice4)
        outState.putInt("computerDice5",computerDice5)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle){
        super.onRestoreInstanceState(savedInstanceState)
        humanTotal = savedInstanceState.getInt("humanTotal")
        computerTotal = savedInstanceState.getInt("computerTotal")
        scoreView.text = "Human     :$humanTotal\nComputer :$computerTotal"
        newTarget = savedInstanceState.getInt("newTarget")
        targetView.setText("Target \n"+" "+" "+newTarget)
        humanWins = savedInstanceState.getInt("humanWins")
        computerWins = savedInstanceState.getInt("computerWins")
        winsView.setText("H: "+humanWins+" / "+"C: "+computerWins)
        humanDice1 = savedInstanceState.getInt("humanDice1")
        humanDice2 = savedInstanceState.getInt("humanDice2")
        humanDice3 = savedInstanceState.getInt("humanDice3")
        humanDice4 = savedInstanceState.getInt("humanDice4")
        humanDice5 = savedInstanceState.getInt("humanDice5")

        computerDice1 = savedInstanceState.getInt("computerDice1")
        computerDice2 = savedInstanceState.getInt("computerDice2")
        computerDice3 = savedInstanceState.getInt("computerDice3")
        computerDice4 = savedInstanceState.getInt("computerDice4")
        computerDice5 = savedInstanceState.getInt("computerDice5")
        //getDiceImageResource(humanDice1)

        val humanDice1Image = findViewById<ImageView>(R.id.imageView)
        val humanDice2Image = findViewById<ImageView>(R.id.imageView2)
        val humanDice3Image = findViewById<ImageView>(R.id.imageView3)
        val humanDice4Image = findViewById<ImageView>(R.id.imageView4)
        val humanDice5Image= findViewById<ImageView>(R.id.imageView5)

        humanDice1Image.setImageResource(getDiceImageResource(humanDice1))
        humanDice2Image.setImageResource(getDiceImageResource(humanDice2))
        humanDice3Image.setImageResource(getDiceImageResource(humanDice3))
        humanDice4Image.setImageResource(getDiceImageResource(humanDice4))
        humanDice5Image.setImageResource(getDiceImageResource(humanDice5))

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        humanWins = intent.getIntExtra("H_wins",0)
        computerWins = intent.getIntExtra("C_wins",0)
        throwButton = findViewById(R.id.throw_button)
        scoreButton = findViewById(R.id.score_button)
        targetButton = findViewById(R.id.target_button)
        scoreView = findViewById(R.id.Humanscore)
        targetView = findViewById(R.id.your_targent)
        winsView = findViewById(R.id.wins_text)
        hDice1 = findViewById(R.id.imageView)
        hDice2 = findViewById(R.id.imageView2)
        hDice3 = findViewById(R.id.imageView3)
        hDice4 = findViewById(R.id.imageView4)
        hDice5 = findViewById(R.id.imageView5)

        wins()

        throwButton.setOnClickListener {

            //after the clicking throw button firstly check its normal throw or reroll
            if (hDice1Selected == 0&&hDice2Selected == 0&&hDice3Selected == 0&&hDice4Selected == 0&&hDice5Selected == 0){
            rerollCount = 0
            rollCount++
            if (rollCount == 1) { //display game rules to the human player
                val builder = AlertDialog.Builder(this)
                builder.setMessage("You should be able to select which dice you would like to keep for that roll.After\n" +
                        "selecting this, should press the Throw button again and the dice which have not\n" +
                        "been selected for keeping should be rerolled.\n" +
                        "If not, you can continue your game after pressing score button and seeing your score")
                    .setTitle("You Have 2 Optional Rerolls for every Throw !")
                    .setPositiveButton("OK", null)
                val dialog = builder.create()
                dialog.show()
            }
                diceRoller()
            }
            if (hDice1Selected == 1||hDice2Selected == 1||hDice3Selected == 1||hDice4Selected == 1||hDice5Selected == 1){
                rerollCount++ //check reroll count >0
                if (rerollCount>0){

                    val humanDice1Image = findViewById<ImageView>(R.id.imageView)
                    val humanDice2Image = findViewById<ImageView>(R.id.imageView2)
                    val humanDice3Image = findViewById<ImageView>(R.id.imageView3)
                    val humanDice4Image = findViewById<ImageView>(R.id.imageView4)
                    val humanDice5Image = findViewById<ImageView>(R.id.imageView5)

                    if (hDice1Selected==0){
                        humanDice1 =  (1..6).random()
                        humanDice1Image.setImageResource(getDiceImageResource(humanDice1))
                    }
                    if (hDice2Selected==0){
                        humanDice2 =  (1..6).random()
                        humanDice2Image.setImageResource(getDiceImageResource(humanDice2))
                    }
                    if (hDice3Selected==0){
                        humanDice3 =  (1..6).random()
                        humanDice3Image.setImageResource(getDiceImageResource(humanDice3))
                    }
                    if (hDice4Selected==0){
                        humanDice4 =  (1..6).random()
                        humanDice4Image.setImageResource(getDiceImageResource(humanDice4))
                    }
                    if (hDice5Selected==0){
                        humanDice5 =  (1..6).random()
                        humanDice5Image.setImageResource(getDiceImageResource(humanDice5))
                    }
                    // set a total
                    humanTotal -= humanScore
                    humanScore = humanDice1 + humanDice2 + humanDice3 + humanDice4 + humanDice5
                    humanTotal += humanScore

                    if (rerollCount==2){
                        updateScore()

                    }

                }

            }
            hDice1.setOnClickListener {
                if (rerollCount == 2) { // if reroll count > 2 user can't used reroll
                    val toast = Toast.makeText(
                        applicationContext,
                        "You Used All Rerolls",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    hDice1Selected = 0
                }
                else{
                    if (hDice1Selected == 0) { // if user select dice1 hDice1Selected = 1
                                               // if user not select dice1 hDice1Seleted = 0
                        val toast = Toast.makeText(
                            applicationContext,
                            "Dice 1 is Selected",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                        hDice1Selected = 1
                    } else {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Dice 1 is not Selected",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                        hDice1Selected = 0
                    }
                }
            }

            hDice2.setOnClickListener {
                if (rerollCount == 2) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "You Used All Rerolls",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    hDice2Selected = 0
                }
                else{
                    if (hDice2Selected == 0) {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Dice 2 is Selected",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                        hDice2Selected = 1
                    } else {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Dice 2 is not Selected",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                        hDice2Selected = 0
                    }
                }
            }
            hDice3.setOnClickListener {
                if (rerollCount == 2) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "You Used All Rerolls",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    hDice3Selected = 0
                }
                else{
                    if (hDice3Selected == 0) {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Dice 3 is Selected",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                        hDice3Selected = 1
                    } else {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Dice 3 is not Selected",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                        hDice3Selected = 0
                    }
                }
            }

            hDice4.setOnClickListener {
                if (rerollCount == 2) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "You Used All Rerolls",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    hDice4Selected = 0
                }
                else{
                    if (hDice4Selected == 0) {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Dice 4 is Selected",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                        hDice4Selected = 1
                    } else {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Dice 4 is not Selected",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                        hDice4Selected = 0
                    }
                }
            }
            hDice5.setOnClickListener {
                if (rerollCount == 2) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "You Used All Rerolls",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    hDice5Selected = 0
                }
                else{
                    if (hDice5Selected == 0) {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Dice 5 is Selected",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                        hDice5Selected = 1
                    } else {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Dice 5 is not Selected",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                        hDice5Selected = 0
                    }
                }
            } // all dice set to 0
            hDice1Selected = 0
            hDice2Selected = 0
            hDice3Selected = 0
            hDice4Selected = 0
            hDice5Selected = 0
        }
        //set onclicklistener to score button
        scoreButton.setOnClickListener {
        //user after the clicking score button user cant select image again
        // if user try to click some image he got toast msg
            hDice1.setOnClickListener{
                    val toast = Toast.makeText(
                        applicationContext,
                        "You Can't Reroll after Clicking Score Button",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                    hDice5Selected = 0
            }
            hDice2.setOnClickListener{
                val toast = Toast.makeText(
                    applicationContext,
                    "You Can't Reroll after Clicking Score Button",
                    Toast.LENGTH_LONG
                )
                toast.show()
                hDice5Selected = 0

            }
            hDice3.setOnClickListener{
                val toast = Toast.makeText(
                    applicationContext,
                    "You Can't Reroll after Clicking Score Button",
                    Toast.LENGTH_LONG
                )
                toast.show()
                hDice5Selected = 0

            }
            hDice4.setOnClickListener{
                val toast = Toast.makeText(
                    applicationContext,
                    "You Can't Reroll after Clicking Score Button",
                    Toast.LENGTH_LONG
                )
                toast.show()
                hDice5Selected = 0

            }
            hDice5.setOnClickListener{
                val toast = Toast.makeText(
                    applicationContext,
                    "You Can't Reroll after Clicking Score Button",
                    Toast.LENGTH_LONG
                )
                toast.show()
                hDice5Selected = 0

            }
            ComputerDiceRoller()
            updateScore()
            hDice1Selected = 0
            hDice2Selected = 0
            hDice3Selected = 0
            hDice4Selected = 0
            hDice5Selected = 0
        }

        targetButton.setOnClickListener{
            targetSet()
        }
    }

    private fun ComputerDiceRoller() {
//This is computer random strategy
        if (rollCount >= 1){
        CRerolls = (1..2).random()
        if (CRerolls==1){
            while ( CRollsCount<3){
                CRollsCount++
                //if CRollsCount<3 randomly stop some dice
                stopCdice1 = (0..1).random()
                stopCdice2 = (0..1).random()
                stopCdice3 = (0..1).random()
                stopCdice4 = (0..1).random()
                stopCdice5 = (0..1).random()

                val computerDice1Image = findViewById<ImageView>(R.id.imageView6)
                val computerDice2Image = findViewById<ImageView>(R.id.imageView7)
                val computerDice3Image = findViewById<ImageView>(R.id.imageView8)
                val computerDice4Image = findViewById<ImageView>(R.id.imageView9)
                val computerDice5Image = findViewById<ImageView>(R.id.imageView10)
                //if dice not stop there are rolling and displayed soe random values
                if (stopCdice1 == 0){
                    computerDice1 = (1..6).random()
                    computerDice1Image.setImageResource(getDiceImageResource(computerDice1))
                }
                if (stopCdice2 == 0){
                    computerDice2 = (1..6).random()
                    computerDice2Image.setImageResource(getDiceImageResource(computerDice2))
                }
                if (stopCdice3 == 0){
                    computerDice3 = (1..6).random()
                    computerDice3Image.setImageResource(getDiceImageResource(computerDice3))
                }
                if (stopCdice4 == 0){
                    computerDice4 = (1..6).random()
                    computerDice4Image.setImageResource(getDiceImageResource(computerDice4))
                }
                if (stopCdice5 == 0){
                    computerDice5 = (1..6).random()
                    computerDice5Image.setImageResource(getDiceImageResource(computerDice5))
                }
                computerTotal -= computerScore
                computerScore  =  computerDice1 + computerDice2 + computerDice3 + computerDice4 + computerDice5
                computerTotal += computerScore
//calculate total score
            }
        }
            if (humanTotal>computerTotal){
                CRerolls = 1
                if (CRerolls==1){
                    while ( CRollsCount<3){
                        CRollsCount++
                        //if CRollsCount<3 randomly stop some dice
                        stopCdice1 = (0..1).random()
                        stopCdice2 = (0..1).random()
                        stopCdice3 = (0..1).random()
                        stopCdice4 = (0..1).random()
                        stopCdice5 = (0..1).random()

                        val computerDice1Image = findViewById<ImageView>(R.id.imageView6)
                        val computerDice2Image = findViewById<ImageView>(R.id.imageView7)
                        val computerDice3Image = findViewById<ImageView>(R.id.imageView8)
                        val computerDice4Image = findViewById<ImageView>(R.id.imageView9)
                        val computerDice5Image = findViewById<ImageView>(R.id.imageView10)
                        //if dice not stop there are rolling and displayed soe random values
                        if (stopCdice1 == 0){
                            computerDice1 = (1..6).random()
                            computerDice1Image.setImageResource(getDiceImageResource(computerDice1))
                        }
                        if (stopCdice2 == 0){
                            computerDice2 = (1..6).random()
                            computerDice2Image.setImageResource(getDiceImageResource(computerDice2))
                        }
                        if (stopCdice3 == 0){
                            computerDice3 = (1..6).random()
                            computerDice3Image.setImageResource(getDiceImageResource(computerDice3))
                        }
                        if (stopCdice4 == 0){
                            computerDice4 = (1..6).random()
                            computerDice4Image.setImageResource(getDiceImageResource(computerDice4))
                        }
                        if (stopCdice5 == 0){
                            computerDice5 = (1..6).random()
                            computerDice5Image.setImageResource(getDiceImageResource(computerDice5))
                        }
                        computerTotal -= computerScore
                        computerScore  =  computerDice1 + computerDice2 + computerDice3 + computerDice4 + computerDice5
                        computerTotal += computerScore
//calculate total score
                    }
                }
            }
        }
    }

    private fun diceRoller(){ // when user throw the dice this methode called
        humanDice1 =  (1..6).random()
        humanDice2 =  (1..6).random()
        humanDice3 =  (1..6).random()
        humanDice4 =  (1..6).random()
        humanDice5 =  (1..6).random()
// in this method generate random numbers 1 to 6
// in this method generate dice image randomly
        val humanDice1Image = findViewById<ImageView>(R.id.imageView)
        val humanDice2Image = findViewById<ImageView>(R.id.imageView2)
        val humanDice3Image = findViewById<ImageView>(R.id.imageView3)
        val humanDice4Image = findViewById<ImageView>(R.id.imageView4)
        val humanDice5Image= findViewById<ImageView>(R.id.imageView5)

        humanDice1Image.setImageResource(getDiceImageResource(humanDice1))
        humanDice2Image.setImageResource(getDiceImageResource(humanDice2))
        humanDice3Image.setImageResource(getDiceImageResource(humanDice3))
        humanDice4Image.setImageResource(getDiceImageResource(humanDice4))
        humanDice5Image.setImageResource(getDiceImageResource(humanDice5))

        computerDice1 =  (1..6).random()
        computerDice2 =  (1..6).random()
        computerDice3 =  (1..6).random()
        computerDice4 =  (1..6).random()
        computerDice5 =  (1..6).random()
//by this method computer also generate 1 to 6 random numbers
// also computer roll there dice using this method
        val computerDice1Image = findViewById<ImageView>(R.id.imageView6)
        val computerDice2Image = findViewById<ImageView>(R.id.imageView7)
        val computerDice3Image = findViewById<ImageView>(R.id.imageView8)
        val computerDice4Image = findViewById<ImageView>(R.id.imageView9)
        val computerDice5Image = findViewById<ImageView>(R.id.imageView10)

        computerDice1Image.setImageResource(getDiceImageResource(computerDice1))
        computerDice2Image.setImageResource(getDiceImageResource(computerDice2))
        computerDice3Image.setImageResource(getDiceImageResource(computerDice3))
        computerDice4Image.setImageResource(getDiceImageResource(computerDice4))
        computerDice5Image.setImageResource(getDiceImageResource(computerDice5))
//Calculate total score human and computer both
        humanScore = humanDice1 + humanDice2 + humanDice3 + humanDice4 + humanDice5
        humanTotal += humanScore
        computerScore = computerDice1 + computerDice2 + computerDice3 + computerDice4 + computerDice5
        computerTotal += computerScore
    }

//generat dice image
    private fun getDiceImageResource(number: Int): Int {
        return when (number) {
            1 -> R.drawable.dice1
            2 -> R.drawable.dice2
            3 -> R.drawable.dice3
            4 -> R.drawable.dice4
            5 -> R.drawable.dice5
            else -> R.drawable.dice6
        }
    }

    private fun updateScore() {
        scoreView.text = "Human     :$humanTotal\nComputer :$computerTotal"
//this is update score method
        if (humanTotal>=newTarget && humanTotal>computerTotal){
            show_WinPopup()
            humanWins +=1
            throwButton.isEnabled = false
            scoreButton.isEnabled = false
        }
//if player won player can see popup window "You win!"
        if (computerTotal>=newTarget && computerTotal>humanTotal){
            show_losePopup()
            computerWins +=1
            throwButton.isEnabled = false
            scoreButton.isEnabled = false
        }
//if player lose player can see popup window "You Lose"
        if (humanTotal==computerTotal){
            diceRoller()
        }
//if player score and computer score are tie (the 2 players keep rolling until
//the tie is broken - no rerolls in this case
        winsView.setText("H: "+humanWins+" / "+"C: "+computerWins)

    }
    @SuppressLint("MissingInflatedId")
    fun show_WinPopup() {
        // inflate the layout for the popup window
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.win_popup, null)

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
        val closeButton = popupView.findViewById<Button>(R.id.popup_buttonwin)
        closeButton.setOnClickListener {
            popupWindow.dismiss()
        }
    }
    fun show_losePopup() {
        // inflate the layout for the popup window
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.lose_popup, null)

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
        val closeButton = popupView.findViewById<Button>(R.id.popup_buttonlose)
        closeButton.setOnClickListener {
            popupWindow.dismiss()
        }
    }

    private fun wins(){
        winsView.setText("H: "+humanWins+" / "+"C: "+computerWins)

    }

    private fun targetSet(){
// set user target
        val targetView = findViewById<TextView>(R.id.your_targent)
// user can set the target by clicking target button
// user can set the target before start the game
        if (rollCount == 0) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Please Enter Your Target.Default Target is 101")
            builder.setTitle("Set Your Target")
            builder.setCancelable(false)

            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_NUMBER
            builder.setView(input)
            builder.setPositiveButton("OK") { dialog, which ->
                val txt = input.text.toString()
                if (txt.isEmpty()) {
                    val toast = Toast.makeText(
                        applicationContext,
                        "You haven't Set Your Target",
                        Toast.LENGTH_LONG
                    )
                    toast.show()
                } else {
                    val target = Integer.parseInt(txt)
                    newTarget = target
                    targetView.setText("Target \n"+" "+" "+target)
                }
            }//set user target and default value is 101
            builder.setNegativeButton("Cancel") { dialog, which ->
                newTarget = 101
                dialog.cancel()
            }
            builder.show()

        }else{
                val toast = Toast.makeText(
                    applicationContext,
                    "You Can't set your Target Now! ",
                    Toast.LENGTH_LONG
                )//if user start the game he cant change the target
                toast.show()
            }
    }
    //after the game is over, user can't play anymore when the user click the back button
    //will start the main activity and wincounts also pass to the main activity
    override fun onBackPressed() {
        super.onBackPressed()
        val act = Intent(this,MainActivity::class.java)
        act.putExtra("H_wins",humanWins)
        act.putExtra("C_wins",computerWins)
        startActivity(act)
    }
}

/*Computer Strategy
 * computer score update by randomly and I update that random strategy what I do I set when human Total Score
 * is grater than(>) Computer score then computer get all rerolls with out selecting randomly
 * I believe that as an advantage here, if the scores are not enough under the computer, you can use the reroll opportunities to get scores.
 * Also, as a disadvantage of this, using meme strategies makes it quite difficult for the human player to play
 * */