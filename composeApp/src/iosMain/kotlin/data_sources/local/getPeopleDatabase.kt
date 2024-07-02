package data_sources.local

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<PeopleDatabase> {
    val dbFilePath = NSHomeDirectory() + "/people.db"
    return Room.databaseBuilder<PeopleDatabase>(
        name = dbFilePath,
        factory =  { PeopleDatabase::class.instantiateImpl() },
    )
}