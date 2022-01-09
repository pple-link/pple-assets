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
                rhs[rh.lowercase()].notNull { "Blood.RH can not parse [$rh]" }
        }
    }

    enum class Product {
        // 전혈
        WHOLE,

        // 성분채혈 혈소판
        PLATELET,

        // 성분채혈 백혈구
        LEUKOCYTE,

        // 농축 적혈구
        PACKED_RED_BLOOD_CELL,

        // 백혈구제거 적혈구
        LEUKOCYTE_REDUCED_RED_BLOOD_CELL
        ;

        companion object {
            private val products = values().associateBy { it.name.lowercase() }
            fun from(product: String): Product =
                products[product.lowercase()].notNull { "Blood.Product can not parse [$product]" }
        }
    }
}
