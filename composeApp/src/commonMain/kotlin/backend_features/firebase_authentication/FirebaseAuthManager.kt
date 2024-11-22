package backend_features.firebase_authentication

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Manages the Firebase Authentication process and holds the current user's information.
 * This class provides methods for creating new users, signing in, and signing out.
 * This class also holds reference to the current user's Firebase User object in memory.
 */
object FirebaseAuthManager {

    private val auth = Firebase.auth

    /**
     * MutableStateFlow to hold the current user's Firebase User object.
     * Collect this value to access the users Firebase User object.
     */
    val firebaseUser = MutableStateFlow(auth.currentUser)

    /**
     * Creates a new user with the provided email and password.
     * @param email The email address of the new user.
     * @param password The password for the new user.
     */
    suspend fun createNewUser(
        email: String,
        password: String,
    ) {
        try {
            val newUser = auth.createUserWithEmailAndPassword(
                email = email,
                password = password
            )
            firebaseUser.emit(newUser.user)
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO: Handle error
        }
    }

    /**
     * Signs in a user with the provided email and password.
     * @param email The email address of the user.
     * @param password The password for the user.
     */
    suspend fun signIn(
        email: String,
        password: String,
    ) {
        try {
            val foundUser = auth.signInWithEmailAndPassword(
                email = email,
                password = password
            )

            firebaseUser.emit(foundUser.user)
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO: Handle error
        }
    }

    /**
     * Signs out the current FirebaseUser.
     */
    suspend fun signOut() {
        try {
            auth.signOut()
            firebaseUser.emit(null)
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO: Handle error
        }
    }

}