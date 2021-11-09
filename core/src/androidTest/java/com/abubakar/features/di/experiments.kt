package com.abubakar.features.di

import com.abubakar.base.Experiment
import com.abubakar.experiments.firebase.ExperimentKeyValue
import com.abubakar.experiments.firebase.ExperimentSource
import com.abubakar.experiments.firebase.FakeExperimentsImpl
import com.abubakar.experiments.firebase.RemoteExperiments
import dagger.Binds
import dagger.Module
import javax.inject.Inject


@Module
interface FakeExperimentModule {
    @Binds
    fun bindExperiments(remoteExperiments: RemoteExperiments): Experiment

    @Binds
    fun experimentSource(uiTestExperimentsImpl: UITestExperimentsImpl): ExperimentSource
}


class UITestExperimentsImpl @Inject constructor() : ExperimentSource {
    override suspend fun experimentConfigs(): List<ExperimentKeyValue> {
        return listOf(
            ExperimentKeyValue("new_details_ui", true)
        )
    }
}