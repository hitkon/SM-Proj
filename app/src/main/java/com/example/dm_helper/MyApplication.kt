package com.example.dm_helper

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // No PDFBox-specific setup is needed for iText.
    }
}
