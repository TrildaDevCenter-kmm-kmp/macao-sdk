package com.macaosoftware.component.demo

import com.macaosoftware.app.InitializationError
import com.macaosoftware.plugin.MacaoApplicationCallback
import com.macaosoftware.app.PluginFactory
import com.macaosoftware.app.PluginManager
import com.macaosoftware.app.PluginManagerInitializer
import com.macaosoftware.util.MacaoResult

class JvmPluginManagerInitializer(
    private val onExitJvmApplication: () -> Unit
) : PluginManagerInitializer {

    override suspend fun initialize(): MacaoResult<PluginManager, InitializationError> {

        val macaoAppCallbackPlugin = object : MacaoApplicationCallback {
            override fun onExit() {
                onExitJvmApplication()
            }
        }

        val macaoAppCallbackPluginFactory = object : PluginFactory<MacaoApplicationCallback> {
            override fun getPlugin(): MacaoApplicationCallback? {
                return macaoAppCallbackPlugin
            }
        }

        val pluginManager = PluginManager().apply {
            addFactory(macaoAppCallbackPluginFactory)
        }

        return MacaoResult.Success(pluginManager)
    }
}
