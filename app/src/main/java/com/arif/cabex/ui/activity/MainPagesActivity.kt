package com.arif.cabex.ui.activity

import androidx.navigation.Navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.navigation.NavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.arif.cabex.R
import com.arif.cabex.databinding.ActivityMainPagesBinding
import com.arif.cabex.ui.dialog.ConfirmOrderDialog
import com.google.android.material.bottomnavigation.BottomNavigationView

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
            val dialog = ConfirmOrderDialog(this@MainPagesActivity)
            dialog.show()
            dialog.window?.setLayout(MATCH_PARENT, WRAP_CONTENT)

        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}