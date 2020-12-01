package com.example.msg.ui.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.msg.R
import com.example.msg.base.BaseActivity
import com.example.msg.databinding.ActivityMainBinding
import com.example.msg.ui.view.inbox.InboxMsgListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    private val PERMISSION_SEND_SMS = 123

    override
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestSmsPermission()
    }

    private fun requestSmsPermission() {
        // check permission is given
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS
                ),
                PERMISSION_SEND_SMS
            )
        } else {
            // permission already granted run sms send
            replaceFragmentTask()
        }
    }

    private fun replaceFragmentTask() {
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, InboxMsgListFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_SEND_SMS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    replaceFragmentTask()
                } else {
                    // permission denied
                    showToast(getString(R.string.permission_required))
                }
                return
            }
        }
    }
}
