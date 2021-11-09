package com.abubakar.base

class AppConfig(
    val isDebug: Boolean,
    val env: Environment,
    val buildInfo: BuildInfo,
    val keys: Keys,
)

data class BuildInfo(
    val version: String,
    val versionCode: Int
)

data class Keys(
    val gatewayKey: String
)

enum class Environment(val baseUrl: String) {
    STAGING(""),
    PROD("https://api.themoviedb.org/3/")
}