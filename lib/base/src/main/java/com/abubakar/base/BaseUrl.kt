package com.abubakar.base

import javax.inject.Inject

class BaseUrl @Inject constructor(
    private val appConfig: AppConfig
) : () -> String {

    override fun invoke(): String = appConfig.env.baseUrl
}