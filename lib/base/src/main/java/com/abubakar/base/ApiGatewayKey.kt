package com.abubakar.base

import javax.inject.Inject

class ApiGatewayKey @Inject constructor(
    private val appConfig: AppConfig
) : () -> String {

    override fun invoke(): String = appConfig.keys.gatewayKey
}