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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

/**
 * TASK 1 : Display each image exactly twice in a grid, on each run, in a random order.
 * TASK 2 : Match the images when clicked.If matched, green, not matched, Blue. If selected, yellow.
 *
 */

// You can update with your own data structure if needed.
// This is just an example of simplest data structure for recomposition.
val cells: SnapshotStateList<Any> = listOf<Any>().toMutableStateList()

class CardData(
    val id: Int, val imageVector: ImageVector
) {
    // To trigger compose, these values has to be under compose state tracking.
    var isSelected by mutableStateOf(false)
    var isMatched by mutableStateOf(false)
}

/**
 * A simple [Composable] function that displays a grid of buttons.
 * The grid is 3 columns wide and has 4 rows.
 */
@Composable
private fun ContentView() {

    remember { cells } //Recomposition is triggered after you change this object

    /**
     *  Create a full list of card.
     */
    val cards = remember {
        (images + images).shuffled().mapIndexed { index, imageVector ->
            CardData(id = index, imageVector = imageVector)
        }.toMutableStateList()
    }
    var firstSelectedIndex by remember { mutableStateOf<Int?>(null) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(12) { index ->
            val card = cards[index]
            CardButton(
                imageVector = card.imageVector, backgroundColor = when {
                    card.isMatched -> ColorGreen
                    card.isSelected -> ColorYellow
                    else -> ColorBlue
                }, onClick = {
                    if (card.isMatched || card.isSelected) {
                        //Matched items and Already selected items can't get selected
                        return@CardButton
                    }
                    card.isSelected = true
                    if (firstSelectedIndex == null) {
                        //If first cars is selected, add it here!
                        firstSelectedIndex = index
                    } else {
                        val firstSelectedCard = cards[firstSelectedIndex!!]
                        if (firstSelectedCard.imageVector == card.imageVector) {
                            card.isMatched = true
                            firstSelectedCard.isMatched = true
                        } else {
                            card.isSelected = false
                            firstSelectedCard.isSelected = false
                        }
                        firstSelectedIndex = null
                    }
                })
        }
    }
}

@Composable
private fun CardButton(
    imageVector: ImageVector, backgroundColor: Color = ColorBlue, onClick: () -> Unit = {}
) {
    Button(
        modifier = Modifier.aspectRatio(1.0f), colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor, contentColor = Color.White
        ), shape = RoundedCornerShape(CornerSize(10.dp)), onClick = onClick
    ) {
        Image(imageVector = imageVector, "", Modifier.fillMaxSize())
    }
}

private val ColorBlue = Color.Blue.copy(red = 0.2f, blue = 0.9f, green = 0.3f, alpha = 0.8f)
private val ColorYellow = Color.Yellow.copy(red = 0.9f, blue = 0.2f, green = 0.77f, alpha = 0.9f)
private val ColorGreen = Color.Green.copy(red = 0.02f, blue = 0.16f, green = 0.70f, alpha = 0.8f)
