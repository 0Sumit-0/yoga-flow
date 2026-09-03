package com.yogaflow.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yogaflow.data.model.FavoritePose
import com.yogaflow.data.model.PracticeCompletion
import com.yogaflow.data.model.UserProfile
import com.yogaflow.data.model.YogaPose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserProfile::class,
        YogaPose::class,
        FavoritePose::class,
        PracticeCompletion::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class YogaDatabase : RoomDatabase() {
    abstract fun yogaDao(): YogaDao

    companion object {
        @Volatile
        private var INSTANCE: YogaDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): YogaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    YogaDatabase::class.java,
                    "yoga_flow_database"
                )
                    .addCallback(YogaDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class YogaDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
//                        populateInitialData(database.yogaDao())
                    }
                }
            }
        }

//        suspend fun populateInitialData(dao: YogaDao) {
//            dao.insertPoses(InitialData.poses)
//            // Seed a starter profile if none exists
//            dao.saveProfile(InitialData.defaultProfile)
//            // Seed 1-2 initial favorites to demonstrate custom plan
//            InitialData.initialFavorites.forEach { dao.addFavorite(it) }
//            // Seed sample past completion for calendar demonstration
//            InitialData.initialCompletions.forEach { dao.logCompletion(it) }
//        }
    }
}
