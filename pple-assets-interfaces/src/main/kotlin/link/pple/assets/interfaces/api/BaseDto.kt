package link.pple.assets.interfaces.api

import link.pple.assets.configuration.jpa.BaseAuditingEntity
import link.pple.assets.configuration.jpa.BaseEntity
import link.pple.assets.configuration.jpa.requiredId
import link.pple.assets.util.lateInit
import org.springframework.data.domain.Page
import java.time.LocalDateTime

/**
 * @Author Heli
 */

data class PagedDto<T>(
    val content: List<T>,
    val page: PageDto
)

data class PageDto(
    val number: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val first: Boolean,
    val last: Boolean,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)

inline fun <T, R> Page<T>.pagedDto(convert: (T) -> R): PagedDto<R> {
    return PagedDto(
        content = content.map(convert),
        page = toDto()
    )
}

fun Page<*>.toDto() = PageDto(
    number = number,
    size = size,
    totalElements = totalElements,
    totalPages = totalPages,
    first = isFirst,
    last = isLast,
    hasNext = hasNext(),
    hasPrevious = hasPrevious()
)

abstract class AuditingEntityDto {
    var id: Long? = lateInit()
    var uuid: String? = lateInit()
    var createdAt: LocalDateTime? = lateInit()
    var createdBy: Long? = lateInit()
    var modifiedAt: LocalDateTime? = lateInit()
    var modifiedBy: Long? = lateInit()
}

inline fun <reified T : AuditingEntityDto> T.entityData(entity: BaseAuditingEntity): T {
    id = entity.requiredId
    uuid = entity.uuid.toString()
    createdAt = entity.createdAt
    createdBy = entity.createdBy.accountId
    modifiedAt = entity.modifiedAt
    modifiedBy = entity.modifiedBy.accountId
    return this
}

abstract class EntityDto {
    var id: Long? = lateInit()
    var uuid: String? = lateInit()
    var createdAt: LocalDateTime? = lateInit()
    var modifiedAt: LocalDateTime? = lateInit()
}

inline fun <reified T : EntityDto> T.entityData(entity: BaseEntity): T {
    id = entity.requiredId
    uuid = entity.uuid.toString()
    createdAt = entity.createdAt
    modifiedAt = entity.modifiedAt
    return this
}
