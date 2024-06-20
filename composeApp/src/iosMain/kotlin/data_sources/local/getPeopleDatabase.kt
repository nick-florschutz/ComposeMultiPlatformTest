package data_sources.local

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

//fun getPeopleDatabase(): PeopleDatabase {
//    val dbFile = NSHomeDirectory() + "/people.db"
//    return Room.databaseBuilder<PeopleDatabase>(
//        name = dbFile,
//        factory = { PeopleDatabase::class.instantiateImpl() },
//    ).setDriver(BundledSQLiteDriver())
//        .build()
//}

fun getDatabaseBuilder(): RoomDatabase.Builder<PeopleDatabase> {
    val dbFilePath = NSHomeDirectory() + "/people.db"
    return Room.databaseBuilder<PeopleDatabase>(
        name = dbFilePath,
        factory =  { PeopleDatabase::class.instantiateImpl() },
    )
}