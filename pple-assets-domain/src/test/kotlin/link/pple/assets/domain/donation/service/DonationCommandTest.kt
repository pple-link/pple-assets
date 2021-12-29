package link.pple.assets.domain.donation.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import link.pple.assets.domain.Blood
import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.repository.DonationRepository
import link.pple.assets.domain.patient.entity.Patient
import link.pple.assets.domain.patient.service.PatientCommand
import link.pple.assets.domain.patient.service.PatientDefinition
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import java.time.LocalDate

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class DonationCommandTest {

    @MockK
    private lateinit var donationRepository: DonationRepository

    @MockK
    private lateinit var patientCommand: PatientCommand

    @InjectMockKs
    private lateinit var sut: DonationCommand

    @Test
    fun `Donation 을 생성할 수 있다`() {
        // given
        val patientBirthDay = LocalDate.now()
        val definition = DonationDefinition(
            title = "title",
            content = "content",
            patient = PatientDefinition(
                name = "name",
                birthDay = patientBirthDay,
                blood = Blood(abo = Blood.ABO.B, rh = Blood.RH.POSITIVE),
                registrationIdentifier = "identifier",
                medicalFacilityName = "facilityName",
                medicalFacilityRoom = "facilityRoom"
            ),
            needCount = 5L
        )
        val patient = Patient.create(
            name = definition.patient.name,
            birthDay = definition.patient.birthDay,
            blood = definition.patient.blood,
            registrationIdentifier = definition.patient.registrationIdentifier,
            medicalFacilityName = definition.patient.medicalFacilityName,
            medicalFacilityRoom = definition.patient.medicalFacilityRoom
        )
        val donation = Donation.create(
            title = definition.title,
            content = definition.content,
            patient = patient,
            needCount = definition.needCount
        )
        every { patientCommand.create(definition.patient) } returns patient
        every { donationRepository.save(any()) } returns donation

        // when
        val actual = sut.create(definition = definition)

        // then
        expectThat(actual) {
            get { title } isEqualTo "title"
            get { content } isEqualTo "content"
            get { patient }.isNotEqualTo(null).and {
                get { name } isEqualTo "name"
                get { birthDay } isEqualTo patientBirthDay
                get { blood } isEqualTo Blood(abo = Blood.ABO.B, rh = Blood.RH.POSITIVE)
                get { registrationIdentifier } isEqualTo "identifier"
                get { medicalFacilityName } isEqualTo "facilityName"
                get { medicalFacilityRoom } isEqualTo "facilityRoom"
                get { status } isEqualTo Patient.Status.ACTIVE
            }
            get { needCount } isEqualTo 5L
        }
    }
}
