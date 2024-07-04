package data_sources.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import data_sources.local.entities.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {

//    @Upsert
//    suspend fun upsert(person: Person)

    @Insert
    suspend fun insert(person: Person)

    @Query("""SELECT * FROM people""")
    fun getAllPeople(): Flow<List<Person>>

    @Query("""SELECT * FROM people WHERE localId = :id""")
    fun getPersonById(id: Long): Flow<Person>

    @Delete
    suspend fun deletePerson(person: Person)

}