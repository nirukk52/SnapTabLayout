package com.fridayof1995.sample

import android.content.Context
import android.widget.Toast

/**
 * Created by Depression on 12-08-2018.
 */
fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
