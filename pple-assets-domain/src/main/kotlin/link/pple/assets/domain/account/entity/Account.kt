package link.pple.assets.domain.account.entity

import link.pple.assets.configuration.jpa.BaseEntity
import link.pple.assets.domain.Blood
import link.pple.assets.domain.account.service.AccountDefinition
import link.pple.assets.util.notNull
import org.hibernate.annotations.Where
import java.time.LocalDate
import javax.persistence.*

/**
 * @Author Heli
 */
@Entity
@Table(name = "accounts")
class Account private constructor(

    @Embedded
    val key: ProviderKey,

    val email: String,

    var displayName: String,

    val birthDay: LocalDate,

    @Enumerated(EnumType.STRING)
    val gender: Gender,

    val phoneNumber: String,

    var profileImageUrl: String?,

    @Embedded
    val blood: Blood,

    @Enumerated(EnumType.STRING)
    val role: Role,

    @Where(clause = "status != 'DELETE'")
    @Enumerated(EnumType.STRING)
    var status: Status

) : BaseEntity() {

    fun update(displayName: String, profileImageUrl: String?): Account {
        this.displayName = displayName
        this.profileImageUrl = profileImageUrl

        return this
    }

    enum class Gender {
        MALE, FEMALE;

        companion object {
            private val genders = values().associateBy { it.name.lowercase() }
            fun from(gender: String): Gender =
                genders[gender.lowercase()].notNull { "Account.Gender can not parse [$gender]" }
        }
    }

    enum class Status {
        ACTIVE, DELETE
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
            displayName: String,
            birthDay: LocalDate,
            gender: Gender,
            phoneNumber: String,
            profileImageUrl: String,
            blood: Blood
        ) = Account(
            key = ProviderKey(
                type = providerType,
                id = providerAccountId
            ),
            email = email,
            displayName = displayName,
            birthDay = birthDay,
            gender = gender,
            phoneNumber = phoneNumber,
            profileImageUrl = profileImageUrl,
            blood = blood,
            role = Role.USER,
            status = Status.ACTIVE
        )

        fun from(definition: AccountDefinition): Account {
            return create(
                providerType = definition.key.type,
                providerAccountId = definition.key.id,
                email = definition.email,
                displayName = definition.displayName,
                birthDay = definition.birthDay,
                gender = definition.gender,
                phoneNumber = definition.phoneNumber,
                profileImageUrl = definition.profileImageUrl,
                blood = definition.blood
            )
        }
    }
}
