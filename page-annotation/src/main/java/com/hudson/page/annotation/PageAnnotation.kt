package com.hudson.page.annotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class PageAnnotation(
    /**
     * Desc for page register on [targetHostPage] page.
     */
    val pageDesc: String,

    /**
     * The name of the host page which you want to register on.
     */
    val targetHostPage: String = "DefaultHostPage"
)