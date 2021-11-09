package com.abubakar.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.abubakar.features.di.retainedComponent
import com.abubakar.features.navigation.PopBackStack
import com.abubakar.features.navigation.RealNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val fragmentManager get() = supportFragmentManager
    @Inject
    lateinit var navigator: RealNavigation
    @Inject
    fun fragmentFactory(factory: FragmentFactory) {
        fragmentManager.fragmentFactory = factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        retainedComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        navigator
            .channel
            .receiveAsFlow()
            .onEach(::handleNavigation)
            .launchIn(scope)
    }

    private fun handleNavigation(direction: NavDirections) {
        val controller = findNavController(R.id.nav_host)

        if (direction !is PopBackStack && controller.hasAction(direction.actionId)) {
            controller.navigate(direction)
            return
        }

        val handled = controller.popBackStack()
        if (!handled) finish()
    }


    private fun NavController.hasAction(actionId: Int) =
        currentDestination?.getAction(actionId) != null

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}