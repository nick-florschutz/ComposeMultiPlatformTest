package data_sources.local

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

fun getDatabaseBuilder(): RoomDatabase.Builder<PeopleDatabase> {
//    val dbFilePath = NSHomeDirectory() + "/people.db"
    val dbFilePath = "${fileDirectory()}/people.db"
    return Room.databaseBuilder<PeopleDatabase>(
        name = dbFilePath,
        factory =  { PeopleDatabase::class.instantiateImpl() },
    )
}
@OptIn(ExperimentalForeignApi::class)
private fun fileDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory).path!!
}
