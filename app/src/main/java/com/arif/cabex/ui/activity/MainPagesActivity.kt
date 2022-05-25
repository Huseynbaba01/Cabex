package com.arif.cabex.ui.activity

import android.content.Intent
import androidx.navigation.Navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.navigation.NavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.arif.cabex.R
import com.arif.cabex.databinding.ActivityMainPagesBinding
import com.arif.cabex.event.ChangeNavbarVisibilityEvent
import com.arif.cabex.ui.dialog.ConfirmOrderDialog
import com.arif.cabex.ui.fragment.NewOffer
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainPagesActivity : AppCompatActivity() {
    private val TAG = "MainPagesActivity"
    private lateinit var binding: ActivityMainPagesBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setBackgroundColor(getColor(android.R.color.transparent))
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.main, R.id.map, R.id.notifications, R.id.settings))
        bottomNavigationView = binding.bottomNavigationView
        navController = findNavController(this, R.id.nav_host_fragment)
        binding.bottomNavigationView.setupWithNavController(
            findNavController(
                this,
                R.id.nav_host_fragment
            )
        )
        binding.fab.setOnClickListener {
            startActivity(Intent(this, NewOffer::class.java))
        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }
    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onChangeNavbarVisibilityEvent(event: ChangeNavbarVisibilityEvent){
        if(event.visible){
            binding.bottomAppBar.visibility = VISIBLE
            binding.fab.visibility = VISIBLE
        }
        else {
            binding.bottomAppBar.visibility = GONE
            binding.fab.visibility = GONE
        }
    }
}