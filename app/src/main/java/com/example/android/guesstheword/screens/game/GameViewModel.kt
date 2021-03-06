package com.example.android.guesstheword.screens.game

import android.content.ContentValues.TAG
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        //These represent different important times
        //This is when the game is over
        const val DONE = 0L
        //This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        //This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    }

    private val timer: CountDownTimer

    //Encapsulation, making internal and external versions
    private val _currentTime = MutableLiveData<Long>() //internal
    val currentTime: LiveData<Long> //external
        get() = _currentTime


    private val _word = MutableLiveData<String>()  //internal
    val word: LiveData<String>  //external
        get() = _word

    private val _score = MutableLiveData<Int>() //Internal
    val score: LiveData<Int> //internal
        get() = _score

    private val _eventGameFinish = MutableLiveData<Boolean>() //Internal
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

//    // The current word
//    var word = MutableLiveData<String>()

    // The current score
    //var score = MutableLiveData<Int>()

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel Created")
        resetList()
        nextWord()
        _score.value = 0
        _eventGameFinish.value = false

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = true
            }
        }

        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel Destroyed")
        timer.cancel()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            //gameFinished()
            // _eventGameFinish.value = true
            resetList()
        }
        _word.value = wordList.removeAt(0)


    }


    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

}