package com.hen.presentation.ui.fixture_details

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FixtureDetailsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun givenFixtureId_whenScreenShown_thenTextVisible_andBackWorks() {
        // given
        val fixtureId = 777
        val viewModel = composeRule.getHiltTestViewModel<FixtureDetailsViewModel>(fixtureId)

        var backClicked = false

        composeRule.setContent {
            MaterialTheme {
                FixtureDetailsScreen(
                    viewModel = viewModel,
                    onBackClicked = { backClicked = true },
                )
            }
        }

        // then: текст с ID отобразился
        composeRule.onNodeWithText("Fixture Details for ID: $fixtureId")
            .assertIsDisplayed()

        // when: кликаем Back
        composeRule.onNodeWithText("Back").performClick()

        // then: колбэк вызван
        assert(backClicked)
    }
}

@EntryPoint
@InstallIn(ActivityComponent::class)
interface FixtureDetailsVmFactoryEntryPoint {
    fun fixtureDetailsViewModelFactory(): FixtureDetailsViewModel.Factory
}

inline fun <reified T : ViewModel> AndroidComposeTestRule<ActivityScenarioRule<ComponentActivity>, ComponentActivity>.getHiltTestViewModel(
    vararg params: Any
): T {
    val activity = this.activity
    return when (T::class) {
        FixtureDetailsViewModel::class -> {
            // 1) Получаем Factory из Hilt
            val entryPoint = EntryPointAccessors.fromActivity(
                activity,
                FixtureDetailsVmFactoryEntryPoint::class.java
            )
            val factory = entryPoint.fixtureDetailsViewModelFactory()

            // 2) Достаём fixtureId из params[0]
            val fixtureId = params.getOrNull(0) as? Int
                ?: error("Pass fixtureId to getHiltTestViewModel<FixtureDetailsViewModel>(fixtureId)")

            // 3) Создаём инстанс через Factory
            val vm = factory.create(fixtureId)

            // 4) Регистрируем свою Factory, чтобы ViewModelProvider возвращал нужный vm
            val provider = ViewModelProvider(activity, object : ViewModelProvider.Factory {
                override fun <U : ViewModel> create(modelClass: Class<U>): U {
                    if (modelClass.isAssignableFrom(FixtureDetailsViewModel::class.java)) {
                        return vm as U
                    }
                    throw IllegalArgumentException("Unknown model class $modelClass")
                }
            })

            provider[FixtureDetailsViewModel::class.java] as T
        }

        else -> error("getHiltTestViewModel: Unsupported VM type ${T::class.qualifiedName}")
    }
}