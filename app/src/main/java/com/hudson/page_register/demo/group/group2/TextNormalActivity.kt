package com.hudson.page_register.demo.group.group2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hudson.page.annotation.PageAnnotation
import com.hudson.page_register.demo.R

@PageAnnotation("Normal text page", targetHostPage = "TextSample")
class TextNormalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_normal)
    }
}