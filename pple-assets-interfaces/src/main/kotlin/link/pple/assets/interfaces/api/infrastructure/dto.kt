package link.pple.assets.interfaces.api.infrastructure

import link.pple.assets.infrastructure.service.S3UploadResponse

/**
 * @Author Heli
 */

data class S3UploadResponseDto(
    val entityTag: String,
    val fileName: String
)

// ===============================

internal fun S3UploadResponse.toDto() = S3UploadResponseDto(
    entityTag = entityTag,
    fileName = fileName
)
