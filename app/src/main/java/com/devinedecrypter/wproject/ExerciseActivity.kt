package com.devinedecrypter.wproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.devinedecrypter.wproject.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbExercise)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.tbExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setRestProgressBar()
    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        binding?.tvTitle?.text = "GET READY"
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.max = 10
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                setupRestView()
                startExerciseTimer()
            }

        }.start()
    }

    private fun startExerciseTimer() {
        binding?.tvTitle?.text = "EXERCISE NAME"
        restTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.max = 30
                binding?.progressBar?.progress = 30 - restProgress
                binding?.tvTimer?.text = (30 - restProgress).toString()
            }

            override fun onFinish() {
                setupRestView()
                setRestProgressBar()
            }
        }.start()
    }

    private fun setupRestView() {
        if(restTimer != null) {
            restTimer?.cancel()
            restTimer = null
            restProgress = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding = null
    }

}