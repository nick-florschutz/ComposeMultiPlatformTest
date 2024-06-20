package data_sources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "people"
)
data class Person(
    @PrimaryKey(autoGenerate = true) val localId: Long = 0,
    val firstName: String?,
    val secondName: String?,
) {
}