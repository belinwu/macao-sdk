package com.ncl.intro

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.ncl.common.Constants
import com.ncl.common.domain.screen.ScreenCoordinator
import com.ncl.common.domain.screen.ScreenCoordinatorImpl
import com.ncl.coordinator.Coordinator
import com.ncl.coordinator.CoordinatorProvider


class IntroActivity : AppCompatActivity(), CoordinatorProvider {

    lateinit var appCoordinator : AppCoordinator
    lateinit var screenCoordinator: ScreenCoordinator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val viewContainer: ViewGroup = findViewById(R.id.introActivityViewContainer)

        screenCoordinator = ScreenCoordinatorImpl(supportFragmentManager, viewContainer)

        appCoordinator = AppCoordinator(Constants.APP_COORDINATOR_ID, screenCoordinator)

        appCoordinator.start()

    }


    // Bellow region is useful if we want to handle Activity Lifecycle in our coordinators
    // region: Activity Lifecycle

    override fun onResume() {
        super.onResume()
        appCoordinator.onResume()
    }

    override fun onPause() {
        super.onPause()
        appCoordinator.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        appCoordinator.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        appCoordinator.onDestroy()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        appCoordinator.onBackPressed()
    }

    // endregion


    override fun <C : Coordinator> getCoordinatorById(coordinatorId: String): C? {
        return appCoordinator.depthFirstSearchById(coordinatorId)
    }

}
