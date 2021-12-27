package link.pple.assets.domain.patient.repository

import link.pple.assets.domain.patient.entity.Patient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

/**
 * @Author Heli
 */
interface PatientRepository : JpaRepository<Patient, Long>, PatientRepositoryCustom

interface PatientRepositoryCustom {

}

class PatientRepositoryImpl : QuerydslRepositorySupport(Patient::class.java), PatientRepositoryCustom {

}
