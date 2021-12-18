package link.pple.assets.domain.configuration.jpa

import link.pple.assets.util.lateInit
import link.pple.assets.util.notNull
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = lateInit()

    @Column(updatable = false)
    var createdAt: LocalDateTime = lateInit()

    var modifiedAt: LocalDateTime = lateInit()

    @PrePersist
    fun prePersist() {
        val now = LocalDateTime.now()
        createdAt = now
        modifiedAt = now
    }

    @PreUpdate
    fun preUpdate() {
        modifiedAt = LocalDateTime.now()
    }

    companion object {
        val COMPARATOR_PK_ASC: Comparator<BaseEntity> = compareBy(BaseEntity::requiredId)
    }
}

val BaseEntity.requiredId: Long get() = id.notNull { "Entity(${javaClass.simpleName}) id is null" }
