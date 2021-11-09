package com.abubakar.features.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abubakar.analytics.di.AnalyticsModule
import com.abubakar.base.di.BaseComponent
import com.abubakar.experiments.di.ExperimentModule
import com.abubakar.features.MainActivity
import com.abubakar.networking.NetworkModule
import dagger.Component
import dagger.Subcomponent
import javax.inject.Scope
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RetrofitModule::class,
        InternalModule::class,
        ServiceModule::class,
        ExperimentModule::class,
        AnalyticsModule::class,
    ], dependencies = [BaseComponent::class]

)
interface CoreComponent {
    val retainedComponentFactory: RetainedComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(baseComponent: BaseComponent): CoreComponent
    }

}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class Retained

@Retained
@Subcomponent(
    modules = [
        FragmentModule::class,
        ViewModelModule::class,
        RetainedModule::class,
    ]
)
abstract class RetainedComponent : ViewModel() {

    abstract fun inject(activity: MainActivity)

    @Subcomponent.Factory
    abstract class Factory : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>) = create() as T

        abstract fun create(): RetainedComponent
    }
}