package com.example.flutter_application_1

import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val CHANNEL = "app.icon.change"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "changeIcon" -> {
                    try {
                        val iconName = call.argument<String>("iconName") ?: run {
                            result.error("ARGUMENT_ERROR", "Icon name not provided", null)
                            return@setMethodCallHandler
                        }
                        
                        IconSwitcher(this).changeIcon(iconName)
                        
                        // Delay the result to allow the system to process the change
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            result.success(true)
                        }, 300) // 300ms delay
                        
                    } catch (e: Exception) {
                        result.error("ICON_CHANGE_FAILED", e.message, null)
                    }
                }
                else -> result.notImplemented()
            }
        }
    }
}