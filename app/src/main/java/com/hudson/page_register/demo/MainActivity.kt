package com.hudson.page_register.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.hudson.apt.DefaultHostPageRegister
import com.hudson.apt.TextSampleRegister
import com.hudson.page_register.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // Register for color group
        DefaultHostPageRegister.getPages().forEach {
            binding.llSampleGroup1.addView(
                Button(this).apply {
                    text = it.value
                    setOnClickListener { _->
                        startActivity(Intent(this@MainActivity, it.key))
                    }
                }
            )
        }

        // Register for text group
        TextSampleRegister.getPages().forEach {
            binding.llSampleGroup2.addView(
                Button(this).apply {
                    text = it.value
                    setOnClickListener { _->
                        startActivity(Intent(this@MainActivity, it.key))
                    }
                }
            )
        }
    }
}