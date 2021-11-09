package com.abubakar.experiments.firebase

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

data class ExperimentKeyValue(
    val key: String,
    val value: Any
)

interface ExperimentSource {
    suspend fun experimentConfigs(): List<ExperimentKeyValue>
}

//This is just fake implementation
//just implementing on Client for proof of concept
@Singleton
class FakeExperimentsImpl @Inject constructor() : ExperimentSource {

    private val experimentGroup = mutableMapOf<String, Boolean>()
    private val experiments = listOf("new_details_ui")

    init {
        randomDistribution()
    }

    private fun randomDistribution() {
        //so the random destruction idea is we group all users in enable group whose random
        // number is generated even else we put user in controlled group

        val isEven = Random.nextInt() % 2 == 0
        experiments.forEach {
            experimentGroup[it] = isEven
        }
    }

    override suspend fun experimentConfigs(): List<ExperimentKeyValue> {
        //create fake experiments
        val list = mutableListOf<ExperimentKeyValue>()
        list += booleanExperiments()
        return list
    }

    private fun booleanExperiments(): List<ExperimentKeyValue> {
        val list = mutableListOf<ExperimentKeyValue>()
        experiments.forEach {
            val value = experimentGroup[it]
            if (value != null) {
                list += ExperimentKeyValue(
                    key = it,
                    value = value
                )
            }
        }
        return list
    }
}