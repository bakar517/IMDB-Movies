package com.abubakar.features.util

import com.abubakar.base.ErrorLogger
import javax.inject.Inject

//TODO should move to a separate module
class ErrorLoggerImp @Inject constructor() : ErrorLogger {

    override fun log(ex: Throwable) {
        ex.printStackTrace()
    }

}