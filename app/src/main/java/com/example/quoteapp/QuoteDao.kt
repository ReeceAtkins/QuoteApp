package com.example.quoteapp

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface QuoteDao {
    @Upsert
    fun upsert(quote: Quote)

    @Query("SELECT COUNT(*) FROM Quote")
    fun getRowCount(): Int

    @Query("SELECT * FROM Quote WHERE quote.favorite == true Limit 1")
    fun getFavoriteQuote(): Quote

    @Query("SELECT * FROM quote ORDER BY RANDOM() LIMIT 1")
    fun getRandomQuote(): Quote
}
