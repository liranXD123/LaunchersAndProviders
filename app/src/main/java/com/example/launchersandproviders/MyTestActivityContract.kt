package com.example.launchersandproviders

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
class MyTestActivityContract : ActivityResultContract<String, Int?>() {

    companion object{
        const val EXTRA_NAME = "name"
        const val EXTRA_GRADE = "grade"
    }

    override fun createIntent(
        context: Context,
        input: String
    ): Intent {
        return Intent(context, TestActivity::class.java)
            .putExtra(EXTRA_NAME, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.getIntExtra(EXTRA_GRADE, 0)
    }

    override fun getSynchronousResult(context: Context, input: String): SynchronousResult<Int?>? {
        return if(input.isNullOrEmpty()) SynchronousResult(0) else null
    }
}