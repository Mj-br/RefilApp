package es.refil.presentation.auth.registration

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import es.refil.R
import es.refil.data.network.await
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

//TODO: NEED TO DO MVVM, ALL OF THIS IS HAS TO BE IN AUTH REPOSITORY AND THEN IMPL


//Class to sign in and to sign out with Google and also to get the user information about the user that is signed in
class GoogleAuthUiClient @Inject constructor(
    private val context: Context,
    //The oneTapClient show us the dialog to sign in
    private val oneTapClient: SignInClient,
    ) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()

        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun signInWithIntent(intent: Intent): SignInResult{
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        uuid = uid,
                        email = email,
                        name = displayName,
                        points = 0, // TODO: (POINTS DATA) CHANGE IF OVERWRITES OTHER POINTS
                        profilePictureUrl = photoUrl.toString()
                    )
                },
                errorMessage = null
            )

        } catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message

            )
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun signOut(){
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception){
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser():UserData? = auth.currentUser?.run {
        UserData(
            uuid = uid,
            name = displayName, //TODO: (DO THE FUNCTION TO SEPARATE @ FROM NAME)
            email = email,
            points = 0, //TODO: (OVERWRITE TO 0??)
            profilePictureUrl = photoUrl?.toString()
        )
    }


    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}