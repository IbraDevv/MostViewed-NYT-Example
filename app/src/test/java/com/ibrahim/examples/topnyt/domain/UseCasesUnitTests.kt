package com.ibrahim.examples.topnyt.domain

import com.ibrahim.examples.topnyt.domain.features.articles.GetPeriodsUseCase
import com.ibrahim.examples.topnyt.domain.features.articles.Periods
import org.junit.Test

class UseCasesUnitTests {


    @Test
    fun `Retrieve Periods enum as list using GetPeriodsUseCase`() {
        val getPeriodsUseCase: GetPeriodsUseCase = GetPeriodsUseCase()

        val listOfPeriodsAsString = listOf<String>("1", "7", "30")
        val periods = getPeriodsUseCase()
        periods.forEach { period ->
            assert(listOfPeriodsAsString.contains(period.value))
        }

    }

}