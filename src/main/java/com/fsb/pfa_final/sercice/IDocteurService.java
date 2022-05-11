package com.fsb.pfa_final.sercice;

import com.fsb.pfa_final.entities.Consultation;
import com.fsb.pfa_final.entities.Docteur;
import com.fsb.pfa_final.entities.Specialite;
import java.util.ArrayList;
import java.util.List;

public interface IDocteurService {
     ArrayList<Docteur> allDocters();

     ArrayList<Specialite> allSpecialite();

     Docteur findDoctByName(String name);

     ArrayList<Docteur> findDocterBySpecialite(String name);

     void addDoctor(Docteur docteur);

     void addSpecialiteToDoctor(Specialite specialite, Long idDoct);

     void deleteDoctorById(Long idDoct);

     ArrayList<Consultation> allConsultation();

     ArrayList<Consultation> findConsultationByDoctor(Long idDoct);

     void addConsultationToDoctor(Consultation c, Long idD,Long idP);

     void deleteOldConsultation(Long idDoct);

     List<Docteur> findByAddressAndSpecialites(String adr, String spec);



}
