package com.abubakar.experiments.firebase

import com.abubakar.base.Experiment
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject


class RemoteExperiments @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
) : Experiment {

    init {
        firebaseRemoteConfig.fetchAndActivate()
    }

    override suspend fun booleanKey(key: String, default: Boolean): Boolean {
        return firebaseRemoteConfig.getBoolean(key)
    }

}