package com.example.launchersandproviders

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.launchersandproviders.databinding.ActivityTestBinding

class TestActivity: AppCompatActivity() {
    lateinit var binding: ActivityTestBinding

    private var q1: Boolean = false
    private var q2: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nameOutput.text = intent.getStringExtra(MyTestActivityContract.EXTRA_NAME)
        binding.rgq1.setOnCheckedChangeListener { radioGroup, i ->
            q1 = when(i){R.id.rb3q1 -> true else ->false}
        }

        binding.rgq2.setOnCheckedChangeListener { radioGroup, i ->
            q2 = when(i){R.id.rb2q2 -> true else ->false}
        }

        binding.finishBtn.setOnClickListener {
            var grade = 0
            if(q1) grade+=50
            if(q2) grade+=50

            val intent = Intent().apply { putExtra(MyTestActivityContract.EXTRA_GRADE, grade) }
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}