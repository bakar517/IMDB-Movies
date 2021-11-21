package com.abubakar.features.di

import com.abubakar.base.Experiment
import com.abubakar.experiments.firebase.ExperimentKeyValue
import com.abubakar.experiments.firebase.ExperimentSource
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Inject


@Module
class FakeExperimentModule {

    @Reusable
    @Provides
    fun bindExperiments(remoteExperiments: UITestExperimentsImpl): Experiment = remoteExperiments

    @Provides
    fun experimentSource(uiTestExperimentSourceImpl: UITestExperimentSourceImpl): ExperimentSource =
        uiTestExperimentSourceImpl
}


class UITestExperimentSourceImpl @Inject constructor() : ExperimentSource {
    override suspend fun experimentConfigs(): List<ExperimentKeyValue> {
        return listOf(
            ExperimentKeyValue("new_details_ui", true)
        )
    }
}

class UITestExperimentsImpl @Inject constructor(
    private val source: ExperimentSource
) : Experiment {
    override suspend fun booleanKey(key: String, default: Boolean): Boolean {
        source.experimentConfigs().forEach {
            if (it.key == key) {
                return if (it.value is Boolean) it.value as Boolean else default
            }
        }
        return default
    }
}