package com.ibrahim.examples.topnyt.domain.features.articles

import com.ibrahim.examples.topnyt.common.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPeriodsUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : suspend () -> List<Periods> {


    override suspend fun invoke(): List<Periods> =
        withContext(ioDispatcher) {
            Periods.values().asList()
        }


}