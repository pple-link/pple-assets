package link.pple.assets.domain.donation.entity

import link.pple.assets.domain.Blood
import java.util.concurrent.atomic.AtomicLong
import javax.persistence.AttributeConverter

/**
 * @Author Heli
 */
class BloodProductConverter : AttributeConverter<List<Blood.Product>, String> {

    override fun convertToDatabaseColumn(attribute: List<Blood.Product>): String {
        return attribute.joinToString(",")
    }

    override fun convertToEntityAttribute(dbData: String): List<Blood.Product> {
        return dbData.split(",").map {
            Blood.Product.from(it)
        }
    }
}

class AtomicLongConverter : AttributeConverter<AtomicLong, Long> {

    override fun convertToDatabaseColumn(attribute: AtomicLong): Long {
        return attribute.get()
    }

    override fun convertToEntityAttribute(dbData: Long): AtomicLong {
        return AtomicLong(dbData)
    }

}
