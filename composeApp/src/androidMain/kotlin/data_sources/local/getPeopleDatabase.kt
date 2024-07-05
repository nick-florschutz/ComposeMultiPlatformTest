package data_sources.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<PeopleDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("people.db")
    return Room.databaseBuilder<PeopleDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}