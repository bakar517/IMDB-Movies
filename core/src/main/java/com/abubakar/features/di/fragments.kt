package com.abubakar.features.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.abubakar.features.details.MovieDetailsFragment
import com.abubakar.features.movies.discovery.DiscoveryFragment
import com.abubakar.features.movies.search.SearchMovieFragment
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)


@Module
abstract class FragmentModule {

    @Binds
    abstract fun factory(factory: CustomFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @FragmentKey(DiscoveryFragment::class)
    abstract fun bindMoviesFragment(discoveryFragment: DiscoveryFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(MovieDetailsFragment::class)
    abstract fun bindMovieDetailFragment(movieDetailFragment: MovieDetailsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SearchMovieFragment::class)
    abstract fun bindSearchMovieFragment(searchMovieFragment: SearchMovieFragment): Fragment

}

class CustomFragmentFactory @Inject constructor(
    private val creators: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val modelClass = loadFragmentClass(classLoader, className)
        val provider = creators[modelClass]
            ?: creators.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
        return provider?.get() ?: super.instantiate(classLoader, className)
    }
}

