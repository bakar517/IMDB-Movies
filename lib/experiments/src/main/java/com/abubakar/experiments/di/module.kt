package com.abubakar.experiments.di

import com.abubakar.base.Experiment
import com.abubakar.experiments.firebase.ExperimentSource
import com.abubakar.experiments.firebase.FakeExperimentsImpl
import com.abubakar.experiments.firebase.RemoteExperiments
import dagger.Binds
import dagger.Module

@Module
interface ExperimentModule {
    @Binds
    fun bindExperiments(remoteExperiments: RemoteExperiments): Experiment

    @Binds
    fun experimentSource(fakeExperiments: FakeExperimentsImpl): ExperimentSource
}