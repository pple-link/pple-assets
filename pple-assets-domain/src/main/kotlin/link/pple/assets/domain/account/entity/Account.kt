package link.pple.assets.domain.account.entity

import link.pple.assets.configuration.jpa.BaseEntity
import link.pple.assets.domain.Blood
import link.pple.assets.domain.account.service.AccountCreateDefinition
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

    @Enumerated(EnumType.STRING)
    val role: Role,

    @Where(clause = "status != 'DELETE'")
    @Enumerated(EnumType.STRING)
    var status: Status,

    // ====== optional ======
    var birthDay: LocalDate? = null,

    @Enumerated(EnumType.STRING)
    var gender: Gender? = null,

    var phoneNumber: String? = null,

    var profileImageUrl: String? = null,

    @Embedded
    var blood: Blood? = null

) : BaseEntity() {

    enum class Gender {
        MALE, FEMALE;

        companion object {
            private val genders = values().associateBy { it.name.lowercase() }
            fun from(gender: String): Gender =
                genders[gender.lowercase()].notNull { "Account.Gender can not parse [$gender]" }
        }
    }

    enum class Status {
        TEMP, ACTIVE, DELETE
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

    fun update(
        displayName: String,
        profileImageUrl: String?
    ): Account {
        this.displayName = displayName
        this.profileImageUrl = profileImageUrl

        return this
    }

    fun apply(
        birthDay: LocalDate,
        gender: Gender,
        phoneNumber: String,
        blood: Blood
    ): Account = this.apply {
        this.birthDay = birthDay
        this.gender = gender
        this.phoneNumber = phoneNumber
        this.blood = blood
        this.status = Status.ACTIVE
    }
    
    companion object {

        fun create(
            providerType: ProviderType,
            providerAccountId: String,
            email: String,
            displayName: String,
            profileImageUrl: String?
        ) = Account(
            key = ProviderKey(
                type = providerType,
                id = providerAccountId
            ),
            email = email,
            displayName = displayName,
            profileImageUrl = profileImageUrl,
            role = Role.USER,
            status = Status.TEMP,
            birthDay = null,
            gender = null,
            phoneNumber = null,
            blood = null
        )

        fun from(definition: AccountCreateDefinition): Account {
            return create(
                providerType = definition.key.type,
                providerAccountId = definition.key.id,
                email = definition.email,
                displayName = definition.displayName,
                profileImageUrl = definition.profileImageUrl
            )
        }
    }
}
