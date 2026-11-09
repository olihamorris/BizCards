package com.example.bizcards

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

/** One flashcard. The @Entity label tells Room to make a database table for it. */
@Entity(tableName = "flashcards")
data class FlashCard(
    val term: String,
    val definition: String,
    // The database creates the id automatically for each new card.
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

/** The DAO lists the actions the app can do on the flashcards table. */
@Dao
interface CardDao {
    // Get every card. A Flow keeps sending the latest list whenever the table changes.
    @Query("SELECT * FROM flashcards ORDER BY id")
    fun getAllCards(): Flow<List<FlashCard>>

    // Count the cards, so we know whether the table is empty.
    @Query("SELECT COUNT(*) FROM flashcards")
    suspend fun countCards(): Int

    // Save one new card.
    @Insert
    suspend fun insert(card: FlashCard)
}

/** The database itself. Version 1 means this is the first layout of the table. */
@Database(entities = [FlashCard::class], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao

    companion object {
        @Volatile
        private var instance: CardDatabase? = null

        // Makes sure the whole app shares just one copy of the database.
        fun getInstance(context: Context): CardDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    CardDatabase::class.java,
                    "bizcards.db"
                ).build().also { instance = it }
            }
        }
    }
}