package com.abubakar.experiments.di

import com.abubakar.base.Experiment
import com.abubakar.experiments.firebase.ExperimentSource
import com.abubakar.experiments.firebase.FakeExperimentsImpl
import com.abubakar.experiments.firebase.RemoteExperiments
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class ExperimentModule {

    @Reusable
    @Provides
    fun bindExperiments(remoteExperiments: RemoteExperiments): Experiment = remoteExperiments

    @Provides
    fun firebaseRemoteConfig(): FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
}