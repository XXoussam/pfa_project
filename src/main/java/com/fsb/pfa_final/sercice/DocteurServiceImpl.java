package com.fsb.pfa_final.sercice;
import com.fsb.pfa_final.Repositories.*;
import com.fsb.pfa_final.config.User;
import com.fsb.pfa_final.entities.Consultation;
import com.fsb.pfa_final.entities.Docteur;
import com.fsb.pfa_final.entities.Patient;
import com.fsb.pfa_final.entities.Specialite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocteurServiceImpl implements IDocteurService {
    @Autowired
    DocteurRepository docteurRepository;
    @Autowired
    SpecieliteRepository specieliteRepository;
    @Autowired
    ConsutationRepository consutationRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public ArrayList<Specialite> findListspecByIdDoct(Long idDoct) {
        ArrayList<Specialite> sps = new ArrayList<>();
        for (Docteur docteur : docteurRepository.findAll()) {
            if (docteur.getIdCin() == idDoct) {
                for (Specialite specialite : docteur.getSpecialites()) {
                    Specialite specialite1 = new Specialite();
                    specialite1.setId(specialite.getId());
                    specialite1.setNom(specialite.getNom());
                    sps.add(specialite1);
                }
            }
        }
        return sps;
    }

    @Override
    public ArrayList<Docteur> allDocters() {
        ArrayList<Docteur> doc = new ArrayList<>();
        for (Docteur doctor : docteurRepository.findAll()) {
            Docteur docteur = new Docteur();
            docteur.setIdCin(doctor.getIdCin());
            docteur.setNom(doctor.getNom());
            docteur.setEmail(doctor.getEmail());
            docteur.setPrenom(doctor.getPrenom());
            docteur.setNumPhone(doctor.getNumPhone());
            docteur.setAddress(doctor.getAddress());
            docteur.setSpecialites(findListspecByIdDoct(doctor.getIdCin()));
            ArrayList<Consultation> css = new ArrayList<>();
            for (Consultation consultation : doctor.getConsultationList()) {
                Consultation c = new Consultation();
                c.setId(consultation.getId());
                c.setDate(consultation.getDate());
                c.setPatient(new Patient(consultation.getPatient().getIdP(),
                                         consultation.getPatient().getPrenomP(),
                                         consultation.getPatient().getNomP(),
                                         consultation.getPatient().getEmailP(),
                                         consultation.getPatient().getTelP(),
                                         consultation.getPatient().getAdresseP(),
                                         null,
                             null));
                css.add(c);
            }
            docteur.setConsultationList(css);

            doc.add(docteur);
        }
        return doc;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<Docteur> findListDoctByIdSpecialite(Long idSpes) {
        ArrayList<Docteur> listDoct = new ArrayList<>();
        for (Specialite specialite : specieliteRepository.findAll()) {
            if (specialite.getId().equals(idSpes)) {
                for (Docteur docteur : specialite.getDocteurs()) {
                    Docteur docteur1 = new Docteur();
                    docteur1.setIdCin(docteur.getIdCin());
                    docteur1.setNom(docteur.getNom());
                    docteur1.setEmail(docteur.getEmail());
                    docteur1.setPrenom(docteur.getPrenom());
                    docteur1.setNumPhone(docteur.getNumPhone());
                    docteur1.setAddress(docteur.getAddress());
                    listDoct.add(docteur1);
                }
            }
        }
        return listDoct;
    }

    @Override
    public ArrayList<Specialite> allSpecialite() {
        ArrayList<Specialite> sps = new ArrayList<>();
        for (Specialite specialite : specieliteRepository.findAll()) {
            Specialite specialite1 = new Specialite();
            specialite1.setId(specialite.getId());
            specialite1.setNom(specialite.getNom());
            specialite1.setDocteurs(findListDoctByIdSpecialite(specialite.getId()));
            sps.add(specialite1);
        }
        return sps;
    }

    @Override
    public Docteur findDoctByName(String name) {
        Docteur docteur21 = new Docteur();
        for (Docteur docteur : allDocters()) {
            if (docteur.getNom().equals(name)) {
                docteur21.setIdCin(docteur.getIdCin());
                docteur21.setNom(docteur.getNom());
                docteur21.setEmail(docteur.getEmail());
                docteur21.setPrenom(docteur.getPrenom());
                docteur21.setNumPhone(docteur.getNumPhone());
                docteur21.setAddress(docteur.getAddress());
                docteur21.setSpecialites(findListspecByIdDoct(docteur.getIdCin()));
            }
        }
        return docteur21;
    }

    @Override
    public ArrayList<Docteur> findDocterBySpecialite(String name) {
        ArrayList<Docteur> docts = new ArrayList<>();
        for (Specialite specialite : allSpecialite()) {
            if (specialite.getNom().equals(name)) {
                docts.addAll(specialite.getDocteurs());
            }
        }
        return docts;
    }

    public boolean findSpecialiteByName(String name) {
        boolean specialite = false;
        for (Specialite specialite1 : allSpecialite()) {
            if (specialite1.getNom().equals(name)) {
                specialite = true;
            }
        }
        return specialite;
    }

    @Override
    public void addDoctor(Docteur docteur) {
        ArrayList<Docteur> docts = new ArrayList<>();
        if (docteur.getSpecialites() != null) {
            for (Specialite specialite : docteur.getSpecialites()) {
                if (!findSpecialiteByName(specialite.getNom())) {
                    specialite.setDocteurs(docts);
                    specieliteRepository.save(specialite);
                } else {
                    for (Specialite specialite1 : allSpecialite()) {
                        if (specialite1.getNom().equals(specialite.getNom())) {
                            specialite.setId(specialite1.getId());
                            specialite1.getDocteurs().add(docteur);
                        }
                    }
                }
            }
            docteurRepository.save(docteur);
            User user = new User(0L,docteur.getNom(), encoder.encode(docteur.getPassword()), true,"DOCTOR");
            userRepository.save(user);
        } else throw new RuntimeException("you should have a spepcialite");
    }


    @Override
    public void addSpecialiteToDoctor(Specialite specialite, Long idDoct) {
        Docteur doc = docteurRepository.findById(idDoct).get();
        List<Specialite> spc = specieliteRepository.findAll();
        List<String> names = spc.stream().map(s -> s.getNom()).collect(Collectors.toList());
        if (names.contains(specialite.getNom())) {
            specialite = specieliteRepository.findSpecialiteByNom(specialite.getNom());
            doc.getSpecialites().add(specialite);
            specialite.getDocteurs().add(doc);
        } else {
            specialite.setDocteurs(Arrays.asList(doc));
            doc.getSpecialites().add(specialite);
        }
        specieliteRepository.save(specialite);
    }

    @Override
    public void deleteDoctorById(Long idDoct) {
        Docteur d = docteurRepository.findById(idDoct).get();
        for (Specialite s : d.getSpecialites()) {
            if (s.getDocteurs().size() == 1) {
                specieliteRepository.deleteById(s.getId());
            }
        }
        for (Consultation c : d.getConsultationList()) {
            consutationRepository.deleteById(c.getId());
            c.getPatient().getConsultationList().remove(c);
        }
        docteurRepository.deleteById(idDoct);
    }

    @Override
    public ArrayList<Consultation> allConsultation() {
        ArrayList<Consultation> cs = (ArrayList<Consultation>) consutationRepository.findAll();
        ArrayList<Consultation> css = new ArrayList<>();
        for (Consultation co1 : cs) {
            Consultation c1 = new Consultation();
            c1.setId(co1.getId());
            c1.setDate(co1.getDate());
            Docteur d = new Docteur();
            d.setPrenom(co1.getDocteur().getPrenom());
            d.setEmail(co1.getDocteur().getEmail());
            d.setNom(co1.getDocteur().getNom());
            d.setNumPhone(co1.getDocteur().getNumPhone());
            d.setPassword(co1.getDocteur().getPassword());
            d.setIdCin(co1.getDocteur().getIdCin());
            c1.setDocteur(d);

            Patient pp = new Patient();
            pp.setIdP(co1.getPatient().getIdP());
            pp.setPrenomP(co1.getPatient().getPrenomP());
            pp.setNomP(co1.getPatient().getNomP());
            pp.setEmailP(co1.getPatient().getEmailP());
            pp.setAdresseP(co1.getPatient().getAdresseP());

            c1.setPatient(pp);
            css.add(c1);
        }
        return css;
    }

    @Override
    public ArrayList<Consultation> findConsultationByDoctor(Long idDoct) {
        Docteur d = docteurRepository.findById(idDoct).get();
        ArrayList<Consultation> css = new ArrayList<>();
        for (Consultation c : d.getConsultationList()) {
            Consultation c1 = new Consultation();
            c1.setId(c.getId());
            c1.setDate(c.getDate());

            Patient pp = new Patient();
            pp.setIdP(c.getPatient().getIdP());
            pp.setPrenomP(c.getPatient().getPrenomP());
            pp.setNomP(c.getPatient().getNomP());
            pp.setEmailP(c.getPatient().getEmailP());
            pp.setAdresseP(c.getPatient().getAdresseP());

            c1.setPatient(pp);
            css.add(c1);
        }
        return css;
    }

    @Override
    public void addConsultationToDoctor(Consultation c, Long idDoct , Long idP) {
        int i = c.getDate().getHours();
        c.getDate().setHours(i-1);
        boolean separation = true;
        Docteur d = docteurRepository.findById(idDoct).get();
        for (Consultation c1 : d.getConsultationList()) {
            /*between tow Consultation we shoold have 1hr of suparation*/
            if ((c.getDate().getYear() == c1.getDate().getYear()) &&
                    (c.getDate().getMonth() == c1.getDate().getMonth()) &&
                    (c.getDate().getDay() == c1.getDate().getDay())) {
                if (Math.abs(ChronoUnit.HOURS.between(c.getDate().toInstant(), c1.getDate().toInstant())) < 1) {
                     throw new RuntimeException("between tow Consultation we shoold have 1hr of suparation");
                    //separation = false;
                }
            }
        }
        if (separation) {
            c.setDocteur(d);
            c.setPatient(patientRepository.findById(idP).get());
            Patient p = patientRepository.findById(idP).get();
            p.getConsultationList().add(c);
            consutationRepository.save(c);
            patientRepository.save(p);
        }
    }

    @Override
    public void deleteOldConsultation(Long idDoct) {
        Docteur d = docteurRepository.findById(idDoct).get();
        for (Consultation c : d.getConsultationList()) {
            if (c.getDate().before(new Date())) {
                consutationRepository.getById(c.getId()).getPatient().getConsultationList().remove(c);
                consutationRepository.deleteById(c.getId());

            }
        }
    }

    @Override
    public List<Docteur> findByAddressAndSpecialites(String adr, String spec) {
        List<Docteur> docs = findDocterBySpecialite(spec);System.out.println(docs);
        List<Docteur> docteurList=new ArrayList<>();
        for (Docteur d : docs){
            if (d.getAddress().equals(adr)){
                docteurList.add(d);
            }
        }

        return docteurList;
    }


}
