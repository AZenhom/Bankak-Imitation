package com.a2z.bankak.core.utils

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.a2z.bankak.R

fun AppCompatActivity.registerPermissions(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: (errorMessages: List<String>) -> Unit
) = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) { permissions ->
    if (permissions.entries.isPermissionsGranted())
        onPermissionGranted()
    else
        onPermissionDenied(permissions.entries.getDeniedPermissionsMessages(this))
}

fun Fragment.registerPermissions(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: (errorMessages: List<String>) -> Unit
) = registerForActivityResult(
    ActivityResultContracts.RequestMultiplePermissions()
) { permissions ->
    if (permissions.entries.isPermissionsGranted())
        onPermissionGranted()
    else
        onPermissionDenied(permissions.entries.getDeniedPermissionsMessages(requireContext()))
}

fun <K, V> Set<Map.Entry<K, V>>.isPermissionsGranted(): Boolean {
    var granted = true
    this.forEach {
        if (it.key == Manifest.permission.MANAGE_EXTERNAL_STORAGE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            granted = Environment.isExternalStorageManager()
        else if (it.value == false)
            granted = false
    }
    return granted
}

fun <K, V> Set<Map.Entry<K, V>>.getDeniedPermissionsMessages(context: Context): List<String> {
    val errorMessage: MutableList<String> = mutableListOf()
    this.forEach {
        if (it.value == false) {
            errorMessage.add(
                with(context) {
                    when (it.key) {
                        Manifest.permission.CAMERA ->
                            getString(R.string.permissions_err)

                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        ->
                            getString(R.string.permissions_err)
                        Manifest.permission.RECORD_AUDIO ->
                            getString(R.string.permissions_err)
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION ->
                            getString(R.string.permissions_err)
                        Manifest.permission.READ_CONTACTS ->
                            getString(R.string.permissions_err)
                        else -> getString(R.string.permissions_err)
                    }
                }

            )
        }
    }
    return errorMessage.toList()
}