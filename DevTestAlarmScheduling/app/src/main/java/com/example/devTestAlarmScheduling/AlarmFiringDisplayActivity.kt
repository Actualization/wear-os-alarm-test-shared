package com.example.devTestAlarmScheduling

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Text

class AlarmFiringDisplayActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmFiredHelloWorld()
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1){
            this.setTurnScreenOn(true)
        }
    }

    @Composable
    fun AlarmFiredHelloWorld() {
        Box(
            contentAlignment = androidx.compose.ui.Alignment.Center,
            modifier = androidx.compose.ui.Modifier.fillMaxSize()
        ){
            Text(text = "Alarm Display Says Hello World.\n  (Alarm has fired and launched this activity).",
                textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }

}

