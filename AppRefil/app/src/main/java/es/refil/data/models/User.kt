package es.refil.data.models

data class SignInResult (
    val data: User?,
    val errorMessage: String?
)
data class User(
    val uid: String = "",
    val email: String? = "",
    val name: String? = "",
    val points: Int = 0,
    val bottles: Int = 0,
    val favorites: List<String> = emptyList()
)
