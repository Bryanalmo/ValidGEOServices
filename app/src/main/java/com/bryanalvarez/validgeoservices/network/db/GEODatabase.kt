package com.bryanalvarez.validgeoservices.network.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bryanalvarez.validgeoservices.model.Artist
import com.bryanalvarez.validgeoservices.model.DataConverter
import com.bryanalvarez.validgeoservices.model.Track
import com.bryanalvarez.validgeoservices.network.db.dao.ArtistDao
import com.bryanalvarez.validgeoservices.network.db.dao.TrackDao

@Database(entities = arrayOf(Artist::class, Track::class), version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class GEODatabase : RoomDatabase() {

    abstract fun artistDao(): ArtistDao
    abstract fun trackDao(): TrackDao

    companion object {

        @Volatile
        private var INSTANCE: GEODatabase? = null

        fun getDatabase(context: Context): GEODatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GEODatabase::class.java,
                    "geo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}