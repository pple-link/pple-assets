package link.pple.assets.domain.account.entity

import link.pple.assets.domain.configuration.jpa.BaseEntity
import link.pple.assets.util.notNull
import javax.persistence.*

@Entity
@Table(name = "accounts")
class Account(

    @Embedded
    val key: ProviderKey,

    var email: String,

    var displayName: String,

    @Enumerated(EnumType.STRING)
    var role: Role,

    @Enumerated(EnumType.STRING)
    var status: Status
) : BaseEntity() {

    enum class Status {
        ACTIVE
    }

    enum class Role {
        USER, ADMIN
    }

    enum class ProviderType {
        KAKAO;

        companion object {
            private val types = values().associateBy { it.name.lowercase() }
            fun from(type: String): ProviderType =
                types[type.lowercase()].notNull { "Provider Type can not parse [$type]" }
        }
    }

    @Embeddable
    data class ProviderKey(

        @Enumerated(EnumType.STRING)
        @Column(name = "providerType")
        val type: ProviderType,

        @Column(name = "providerAccountId")
        val id: String
    )

    companion object {

        fun create(
            providerType: ProviderType,
            providerAccountId: String,
            email: String,
            displayName: String
        ) = Account(
            key = ProviderKey(
                type = providerType,
                id = providerAccountId
            ),
            email = email,
            displayName = displayName,
            role = Role.USER,
            status = Status.ACTIVE
        )
    }
}
