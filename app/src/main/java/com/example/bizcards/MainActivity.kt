package com.example.bizcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bizcards.ui.theme.BizCardsTheme

/** One flashcard: a business term and its definition. */
data class FlashCard(val term: String, val definition: String)

/** The single activity that hosts the app's screens. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BizCardsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DeckListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

/** Deck List screen: shows all cards and a small form to add a new one. */
@Composable
fun DeckListScreen(modifier: Modifier = Modifier) {
    // The list of cards. mutableStateListOf makes the screen update when a card is added.
    val cards = remember {
        mutableStateListOf(
            FlashCard("Inflation", "A general rise in prices over time"),
            FlashCard("Asset", "Something a business owns that has value"),
            FlashCard("Profit", "Revenue minus costs")
        )
    }
    // The text the user is typing in the two input fields.
    var term by remember { mutableStateOf("") }
    var definition by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "BizCards", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = term,
            onValueChange = { term = it },
            label = { Text("Term") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = definition,
            onValueChange = { definition = it },
            label = { Text("Definition") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            // Only add the card if both fields have text, then clear the fields.
            if (term.isNotBlank() && definition.isNotBlank()) {
                cards.add(FlashCard(term.trim(), definition.trim()))
                term = ""
                definition = ""
            }
        }) {
            Text("Add card")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable list showing one Card per flashcard.
        LazyColumn {
            items(cards) { card ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(text = card.term, style = MaterialTheme.typography.titleMedium)
                        Text(text = card.definition)
                    }
                }
            }
        }
    }
}

/** Lets Android Studio show this screen in the preview panel. */
@Preview(showBackground = true)
@Composable
fun DeckListPreview() {
    BizCardsTheme {
        DeckListScreen()
    }
}