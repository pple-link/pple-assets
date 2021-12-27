package link.pple.assets.domain

import link.pple.assets.util.notNull
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 * @Author Heli
 */
@Embeddable
data class Blood(

    @Enumerated(EnumType.STRING)
    val abo: ABO,

    @Enumerated(EnumType.STRING)
    val rh: RH
) {

    enum class ABO {
        A, B, O, AB;

        companion object {
            private val abos = values().associateBy { it.name.lowercase() }
            fun from(abo: String): ABO =
                abos[abo.lowercase()].notNull { "Blood.ABO can not parse [$abo]" }
        }
    }

    enum class RH {
        POSITIVE, NEGATIVE;

        companion object {
            private val rhs = values().associateBy { it.name.lowercase() }
            fun from(rh: String): RH =
                rhs[rh.lowercase()].notNull { "Account.RH can not parse [$rh]" }
        }
    }

    enum class Product {
        // 전혈
        WHOLE,

        // 혈소판
        PLATELET,

        // 백혈구
        LEUKOCYTE
    }
}
