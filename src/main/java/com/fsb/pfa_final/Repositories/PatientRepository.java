package com.fsb.pfa_final.Repositories;

import com.fsb.pfa_final.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long>{
}
