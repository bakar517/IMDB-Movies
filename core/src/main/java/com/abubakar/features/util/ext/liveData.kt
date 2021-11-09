package com.abubakar.features.util.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

val <T> MutableLiveData<T>.latest get() = value!!

val <T> LiveData<T>.latest get() = value!!
