package com.devinedecrypter.wproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devinedecrypter.wproject.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {

    private var binding : ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbFinish)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.tbFinish?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnFinish?.setOnClickListener {
            finish()
        }

    }
}