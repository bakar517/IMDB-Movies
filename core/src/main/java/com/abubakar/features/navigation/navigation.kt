package com.abubakar.features.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import com.abubakar.base.Navigator
import com.abubakar.features.di.Retained
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject

const val NO_ACTION = -1

@Retained
class RealNavigation @Inject constructor() : Navigator {

    val channel = Channel<NavDirections>()

    override fun navigate(direction: NavDirections) {
        channel.trySend(direction)
    }

}

class PopBackStack : NavDirections {
    override fun getActionId() = NO_ACTION

    override fun getArguments() = bundleOf()
}

fun Navigator.popBackStack() = navigate(PopBackStack())