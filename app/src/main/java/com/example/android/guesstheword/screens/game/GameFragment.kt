
package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding


/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        //Get the ViewModel
        Log.i("GameFragment", "onCreateView: Called ViewModel Provider")
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        binding.gameViewModel = viewModel

//        binding.correctButton.setOnClickListener{
//            viewModel.onCorrect()
//        }
//
//        binding.skipButton.setOnClickListener {
//            viewModel.onSkip()
//        }

        //viewModel.score.value = 1000


        viewModel.score.observe(this, Observer {
            newScore -> binding.scoreText.text = newScore.toString()
        })

        viewModel.word.observe(this, Observer {
            newWord -> binding.wordText.text = newWord
        })

        viewModel.currentTime.observe(this, Observer {
            newTime -> binding.timerText.text = DateUtils.formatElapsedTime(newTime)
        })

        viewModel.eventGameFinish.observe(this, Observer {
            hasFinished ->
                if (hasFinished){
                    gameFinished()
                    viewModel.onGameFinishComplete()
                }
        })

        return binding.root

    }


    /**
     * Called when the game is finished
     */
    fun gameFinished() {
        val currentScore = viewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameToScore(currentScore)
        findNavController(this).navigate(action)
        Toast.makeText(this.activity, "Game has finished", Toast.LENGTH_SHORT).show()
    }


}
