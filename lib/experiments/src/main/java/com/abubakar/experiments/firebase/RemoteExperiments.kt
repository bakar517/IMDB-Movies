package com.abubakar.experiments.firebase

import com.abubakar.base.Experiment
import javax.inject.Inject

//Right now I am just implementing proof of concept for experiment
//but we can use actual firebase remote configs in order to use real A/B testing

class RemoteExperiments @Inject constructor(
    private val source: ExperimentSource
) : Experiment {

    override suspend fun booleanKey(key: String, default: Boolean): Boolean {
        source.experimentConfigs().forEach {
            if (it.key == key) {
                return if (it.value is Boolean) it.value else default
            }
        }
        return default
    }

}