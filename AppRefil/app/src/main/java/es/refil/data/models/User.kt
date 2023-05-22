package es.refil.data.models

data class User(
    val uuid: String,
    val email: String?,
    val name: String?,
    val points: Int
) {
    constructor() : this("", "", "", 0)
}
