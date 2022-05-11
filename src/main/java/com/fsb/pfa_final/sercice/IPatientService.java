package com.fsb.pfa_final.sercice;


import com.fsb.pfa_final.entities.Consultation;
import com.fsb.pfa_final.entities.Patient;

import java.util.List;

public interface IPatientService  {
 List<Patient> showAllPatient();
 void addPatient(Patient p);
 void deletePatientById(long id);
 void updatePatient(long id,Patient p);
 Patient showPatient(Long idP);

 void deleteConsultationFromPatient(Long idC);
 void addConsultationToPatient(Long idP,Long idD, Consultation c);
 Consultation showConsultation(Long idC);
}
