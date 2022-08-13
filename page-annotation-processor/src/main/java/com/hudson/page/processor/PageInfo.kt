package com.hudson.page.processor

import com.squareup.javapoet.ClassName

data class PageInfo(
    val pageClazz: ClassName,
    val desc: String
)