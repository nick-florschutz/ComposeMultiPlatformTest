package data_sources.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

//fun getPeopleDatabase(context: Context): PeopleDatabase {
//    val dbFile = context.getDatabasePath("people.db")
//    return Room.databaseBuilder<PeopleDatabase>(
//        context = context,
//        name = dbFile.absolutePath,
//    ).setDriver(BundledSQLiteDriver())
//        .build()
//}

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<PeopleDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("people.db")
    return Room.databaseBuilder<PeopleDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}