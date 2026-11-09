package com.example.bizcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bizcards.ui.theme.BizCardsTheme
import kotlinx.coroutines.launch

/** The single activity that hosts the whole app. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BizCardsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BizCardsApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

/** Connects the database to the screens and decides which screen is showing. */
@Composable
fun BizCardsApp(modifier: Modifier = Modifier) {
    // The navigation controller moves the user between screens.
    val navController = rememberNavController()

    // Open the database and get the DAO, which lets us read and save cards.
    val context = LocalContext.current
    val dao = remember { CardDatabase.getInstance(context).cardDao() }

    // A scope lets us run database work in the background, off the main screen thread.
    val scope = rememberCoroutineScope()

    // Read all cards from the database. The list updates by itself when a card is saved.
    val cardsFlow = remember { dao.getAllCards() }
    val cards by cardsFlow.collectAsState(initial = emptyList())

    // The first time the app runs, the database is empty, so save three sample cards.
    LaunchedEffect(Unit) {
        if (dao.countCards() == 0) {
            dao.insert(FlashCard("Inflation", "A general rise in prices over time"))
            dao.insert(FlashCard("Asset", "Something a business owns that has value"))
            dao.insert(FlashCard("Profit", "Revenue minus costs"))
        }
    }

    // NavHost lists the screens. The app starts on the "decks" screen.
    NavHost(navController = navController, startDestination = "decks", modifier = modifier) {
        composable("decks") {
            DeckListScreen(
                cards = cards,
                onAddCard = { card -> scope.launch { dao.insert(card) } },
                onStudyClick = { navController.navigate("study") }
            )
        }
        composable("study") {
            StudyScreen(
                cards = cards,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

/** Screen 1: shows all cards and a small form to add a new one. */
@Composable
fun DeckListScreen(
    cards: List<FlashCard>,
    onAddCard: (FlashCard) -> Unit,
    onStudyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                // Only save the card if both fields have text, then clear the fields.
                if (term.isNotBlank() && definition.isNotBlank()) {
                    onAddCard(FlashCard(term.trim(), definition.trim()))
                    term = ""
                    definition = ""
                }
            }) {
                Text("Add card")
            }
            // Opens the Study screen. Disabled when there are no cards to study.
            OutlinedButton(onClick = onStudyClick, enabled = cards.isNotEmpty()) {
                Text("Study cards")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Scrollable list showing one Card per flashcard.
        LazyColumn {
            items(cards, key = { it.id }) { card ->
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

/** Screen 2: study the cards one at a time, tap a card to flip it, and keep a score. */
@Composable
fun StudyScreen(
    cards: List<FlashCard>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var index by remember { mutableStateOf(0) }              // which card we are on
    var showDefinition by remember { mutableStateOf(false) } // false = showing the term
    var score by remember { mutableStateOf(0) }              // how many "I knew it" answers

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Study", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (index >= cards.size) {
            // All cards done: show the final score and a button to start again.
            Text(text = "Finished!", style = MaterialTheme.typography.titleLarge)
            Text(text = "Score: $score of ${cards.size}")
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                index = 0
                score = 0
                showDefinition = false
            }) {
                Text("Study again")
            }
        } else {
            val card = cards[index]
            Text(text = "Card ${index + 1} of ${cards.size}")
            Spacer(modifier = Modifier.height(8.dp))

            // Tapping the card flips between the term and the definition.
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { showDefinition = !showDefinition }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (showDefinition) card.definition else card.term,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = if (showDefinition) "(definition - tap to flip)" else "(term - tap to flip)")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    score += 1            // add one point
                    index += 1            // go to the next card
                    showDefinition = false
                }) {
                    Text("I knew it")
                }
                OutlinedButton(onClick = {
                    index += 1            // no point, just go to the next card
                    showDefinition = false
                }) {
                    Text("Review again")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Score: $score")
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(onClick = onBack) {
            Text("Back to cards")
        }
    }
}