package link.pple.assets.domain.donation.entity

import link.pple.assets.configuration.jpa.BaseEntity
import link.pple.assets.domain.account.entity.Account
import javax.persistence.*

/**
 * @Author Heli
 */
@Entity
@Table(name = "donation_histories")
class DonationHistory private constructor(

    @JoinColumn(name = "donationId", referencedColumnName = "id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val donation: Donation,

    @JoinColumn(name = "donorAccountId", referencedColumnName = "id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val donor: Account,

    @Enumerated(EnumType.STRING)
    val step: Step

) : BaseEntity() {

    enum class Step {
        CONNECT, OFFER, CONFIRM
    }
}
