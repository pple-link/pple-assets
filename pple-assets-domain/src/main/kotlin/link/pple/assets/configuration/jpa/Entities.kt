package link.pple.assets.configuration.jpa

import link.pple.assets.util.lateInit
import link.pple.assets.util.notNull
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @Author Heli
 */
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

/**
 * @Author Heli
 */
@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseAuditingEntity : BaseEntity() {

    @AttributeOverride(name = "accountId", column = Column(name = "createdAccountId", updatable = false))
    @CreatedBy
    var createdBy: Auditor = lateInit()

    @AttributeOverride(name = "accountId", column = Column(name = "modifiedAccountId"))
    @LastModifiedBy
    var modifiedBy: Auditor = lateInit()
}
