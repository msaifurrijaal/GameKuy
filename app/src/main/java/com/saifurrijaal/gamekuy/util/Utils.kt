package com.saifurrijaal.gamekuy.util

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.saifurrijaal.gamekuy.databinding.DisplayErrorBinding

fun showDialogError(context: Context, message: String){
    val binding = DisplayErrorBinding.inflate(LayoutInflater.from(context))
    binding.tvMessage.text = message

    AlertDialog
        .Builder(context)
        .setView(binding.root)
        .setCancelable(true)
        .create()
        .show()
}