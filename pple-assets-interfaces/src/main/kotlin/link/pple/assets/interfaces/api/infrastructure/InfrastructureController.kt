package link.pple.assets.interfaces.api.infrastructure

import link.pple.assets.infrastructure.service.ProfileImageUploader
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


/**
 * @Author Heli
 */
@RestController
class InfrastructureController(
    private val profileImageUploader: ProfileImageUploader
) {

    @PostMapping(
        value = ["/infrastructure/api/v1/profile/image"],
        consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadProfileImage(
        @RequestPart multipartFile: MultipartFile
    ): S3UploadResponseDto {

        val s3Response = profileImageUploader.upload(
            multipartFile = multipartFile
        )

        return s3Response.toDto()
    }
}
