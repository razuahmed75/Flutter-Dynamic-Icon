package com.example.flutter_application_1

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log

class IconSwitcher(private val context: Context) {
    companion object {
        private const val TAG = "IconSwitcher"
    }

    fun changeIcon(iconName: String) {
        try {
            val pm = context.packageManager
            val packageName = context.packageName
            
            // Get current enabled component to avoid unnecessary changes
            val currentEnabled = getCurrentEnabledComponent(pm, packageName)
            
            // Define all possible components
            val components = mapOf(
                "default" to ComponentName(packageName, "$packageName.MainActivity"),
                "nature" to ComponentName(packageName, "$packageName.NatureIcon"),
                "game" to ComponentName(packageName, "$packageName.GameIcon")
            )
            
            // Skip if already enabled
            if (currentEnabled == components[iconName]) {
                Log.d(TAG, "Icon already set to $iconName")
                return
            }
            
            // Enable new component first (important for some launchers)
            components[iconName]?.let { newComponent ->
                pm.setComponentEnabledSetting(
                    newComponent,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP
                )
                Log.d(TAG, "Enabled component: ${newComponent.className}")
            }
            
            // Disable all other components
            components.forEach { (name, component) ->
                if (name != iconName && component == currentEnabled) {
                    pm.setComponentEnabledSetting(
                        component,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP
                    )
                    Log.d(TAG, "Disabled component: ${component.className}")
                }
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error changing icon", e)
            throw e
        }
    }
    
    private fun getCurrentEnabledComponent(pm: PackageManager, packageName: String): ComponentName? {
        val components = listOf(
            ComponentName(packageName, "$packageName.MainActivity"),
            ComponentName(packageName, "$packageName.NatureIcon"),
            ComponentName(packageName, "$packageName.GameIcon")
        )
        
        return components.firstOrNull { component ->
            pm.getComponentEnabledSetting(component) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        }
    }
}


