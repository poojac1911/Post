package com.example.post

import android.app.Application
import com.example.post.data.AppContainer
import com.example.post.data.AppDataContainer

class PostApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
