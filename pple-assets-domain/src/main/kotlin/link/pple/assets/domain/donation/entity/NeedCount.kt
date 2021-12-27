package link.pple.assets.domain.donation.entity

/**
 * @Author Heli
 */
sealed class NeedCount {
    object Unlimited : NeedCount()
    data class Limited(val count: Int) : NeedCount()

    fun isNotExceeded(currentDonationCount: Long): Boolean {
        return when (this) {
            Unlimited -> true
            is Limited -> currentDonationCount < count
        }
    }
}
