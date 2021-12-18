package link.pple.assets.domain.account.entity

class Account(
    val email: String,
    val password: String,
    val role: ROLE
) {

    enum class ROLE {
        USER, ADMIN
    }
}
