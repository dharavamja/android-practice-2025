package com.example.singlescreengameapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContentView()
        }
    }
}

val images = listOf(
    Icons.Default.Face,
    Icons.Default.Favorite,
    Icons.Default.Star,
    Icons.Default.ShoppingCart,
    Icons.Default.Home,
    Icons.Default.ThumbUp,
)

// You can update with your own data structure if needed.
// This is just an example of simplest data structure for recomposition.
val cells: SnapshotStateList<Any> = listOf<Any>().toMutableStateList()

/**
 * A simple [Composable] function that displays a grid of buttons.
 * The grid is 3 columns wide and has 4 rows.
 *
 * Important:
 *  Manually import LazyVerticalGrid and GridCells.
 *  This can happen because different versions of Compose have placed these classes in different packages.
 *
 */
@Composable
private fun ContentView() {

    remember { cells } //Recomposition is triggered after you change this object

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(12) { index ->
            CardButton()
        }
    }
}

@Composable
private fun CardButton() {
    Button(
        modifier = Modifier.aspectRatio(1.0f), colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
        ),
        shape = RoundedCornerShape(CornerSize(10.dp)),
        onClick = {}
    ) {

        Image(imageVector = images[0], "", Modifier.fillMaxSize())
    }
}

private val ColorBlue = Color.Blue.copy(red = 0.2f, blue = 0.9f, green = 0.3f, alpha = 0.8f)
private val ColorYellow = Color.Yellow.copy(red = 0.9f, blue = 0.2f, green = 0.77f, alpha = 0.9f)
private val ColorGreen = Color.Green.copy(red = 0.02f, blue = 0.16f, green = 0.70f, alpha = 0.8f)
