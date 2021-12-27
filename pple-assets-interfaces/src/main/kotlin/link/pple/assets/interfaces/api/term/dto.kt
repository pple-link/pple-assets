package link.pple.assets.interfaces.api.term

import link.pple.assets.domain.term.entity.Term
import link.pple.assets.interfaces.api.EntityDto
import link.pple.assets.interfaces.api.entityData

/**
 * @Author Heli
 */
data class TermDto(
    val title: String,
    val content: String,
    val required: Boolean,
    val seq: Long,
    val status: String
) : EntityDto()

// ===============================

internal fun Term.toDto() = TermDto(
    title = title,
    content = content,
    required = required,
    seq = seq,
    status = status.name
).entityData(this)
