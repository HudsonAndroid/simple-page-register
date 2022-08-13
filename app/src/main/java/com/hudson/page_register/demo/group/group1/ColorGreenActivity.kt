package com.hudson.page_register.demo.group.group1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hudson.page.annotation.PageAnnotation
import com.hudson.page_register.demo.R

@PageAnnotation("SubPage2 for other sample")
class ColorGreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_green)
    }
}