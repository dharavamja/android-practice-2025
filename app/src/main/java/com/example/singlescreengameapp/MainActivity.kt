package com.example.singlescreengameapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

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
 * TASK 3 : Add Turn count.
 * TASK 4 : Add Reset button.
 * TASK 5 : Add undo button.
 */

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

    /**
     *  Create a full list of card.
     */
    val originalCards = remember {
        (images + images).mapIndexed { index, imageVector ->
            CardData(id = index, imageVector = imageVector)
        }.toMutableStateList()
    }
    val cards = remember { mutableStateListOf<CardData>() }
    var firstSelectedIndex by remember { mutableStateOf<Int?>(null) }
    var turnCount by remember { mutableIntStateOf(0) }

    var undoStack = remember { mutableStateListOf<List<CardData>>() }
    var undoCount by remember { mutableIntStateOf(5) }

    LaunchedEffect(Unit) {
        cards.clear()
        cards.addAll(originalCards.shuffled())
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }) { paddingValue ->
        Column(modifier = Modifier.padding(paddingValue)) {
            Row {
                FilledTonalButton(
                    onClick = {
                        if (undoCount > 0 && undoStack.isNotEmpty()) {
                            if (firstSelectedIndex != null) {
                                firstSelectedIndex == null
                            } else {
                                cards.clear()
                                cards.addAll(undoStack.removeAt(undoStack.lastIndex).map {
                                    CardData(it.id, it.imageVector).apply {
                                        isSelected = false
                                        isMatched = it.isMatched
                                    }
                                })
                                turnCount--
                            }
                            undoCount--
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar("Successfully Undo the last move.")
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color(0x99A0A0B2), contentColor = Color.Black
                    ),
                    enabled = undoCount > 0 && undoStack.isNotEmpty(),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Undo,
                        contentDescription = "Undo",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = "Undo : $undoCount", fontSize = 16.sp, fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Turn : $turnCount",
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                FilledTonalButton(
                    onClick = {
                        cards.clear()
                        cards.addAll(originalCards.shuffled().map {
                            // create a fresh copy
                            CardData(
                                id = it.id, imageVector = it.imageVector
                            )
                        })
                        firstSelectedIndex = null
                        turnCount = 0
                        undoCount = 5
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(message = "Game Reset Successfully!")
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color(0xFFFFCDD2), contentColor = Color.Red
                    ),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        text = "Reset", fontSize = 16.sp, fontWeight = FontWeight.Bold
                    )
                }
            }
            if (cards.size == 12) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(cards.size) { index ->
                        val card = cards[index]
                        CardButton(
                            imageVector = card.imageVector, backgroundColor = when {
                                card.isMatched -> ColorGreen
                                card.isSelected -> ColorYellow
                                else -> ColorBlue
                            }, onClick = {
                                if (card.isMatched) {
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar("Already Matched card can't be selected!")
                                    }
                                    return@CardButton
                                }
                                if (card.isSelected) {
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar("Already selected, please select another card!")
                                    }
                                    return@CardButton
                                }
                                card.isSelected = true
                                if (firstSelectedIndex == null) {
                                    //If first cars is selected, add it here!
                                    firstSelectedIndex = index
                                } else {
                                    undoStack.add(
                                        cards.map {
                                            CardData(
                                                id = it.id,
                                                imageVector = it.imageVector
                                            ).apply {
                                                isSelected = it.isSelected
                                                isMatched = it.isMatched
                                            }
                                        })
                                    val firstSelectedCard = cards[firstSelectedIndex!!]
                                    if (firstSelectedCard.imageVector == card.imageVector) {
                                        card.isMatched = true
                                        firstSelectedCard.isMatched = true
                                    } else {
                                        card.isSelected = false
                                        firstSelectedCard.isSelected = false
                                    }
                                    turnCount++
                                    firstSelectedIndex = null
                                }
                            })
                    }
                }
            }
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
