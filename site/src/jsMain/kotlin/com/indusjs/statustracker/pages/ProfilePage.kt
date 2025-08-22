package com.indusjs.statustracker.pages

import android.os.Bundle
import androidx.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.hellopage.ui.theme.HelloPageTheme // Replace with your theme
import com.indusjs.statustracker.utils.Cursor.Companion.Text
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import org.jetbrains.compose.web.dom.Text

@Composable
fun HelloPageTheme(content: @Composable () -> Unit) {
    TODO("Not yet implemented")
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloPageTheme { // Your app's theme
                // A Box composable to center the text
                Box(
                    modifier = Modifier.fillMaxSize(), // Fill the entire screen
                    contentAlignment = Alignment.Center // Align content to the center
                ) {
                    HelloMessage()
                }
            }
        }
    }
}


@Composable
fun HelloMessage() {
    Text(
        text = "Hello!",
        fontSize = 32.sp,
        value = TODO() // Make the text a bit larger
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
internal fun DefaultPreview() {
    HelloPageTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            HelloMessage()
        }
    }
}

