package es.refil.data.models

data class SignInResult (
    val data: User?,
    val errorMessage: String?
)
data class User(
    val uid: String,
    val email: String?,
    val name: String?,
    val points: Int,
    val profilePictureUrl: String? //TODO: QUITAR EL PROFILE PORQUE DIONY PORQUE DIONY LE ROMPE LA APP
) {
    constructor() : this("", "", "", 0, "")
}
