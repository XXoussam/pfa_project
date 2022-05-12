package com.fsb.pfa_final.sercice;


import com.fsb.pfa_final.Repositories.DocteurRepository;
import com.fsb.pfa_final.Repositories.PatientRepository;
import com.fsb.pfa_final.Repositories.ConsutationRepository;
import com.fsb.pfa_final.Repositories.UserRepository;
import com.fsb.pfa_final.config.User;
import com.fsb.pfa_final.entities.Consultation;
import com.fsb.pfa_final.entities.Docteur;
import com.fsb.pfa_final.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientServiceImpl implements IPatientService{
	@Autowired
	DocteurRepository docteurRepository;
	
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	ConsutationRepository consutationRepository;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<Patient> showAllPatient(){
		List<Patient> patientList=new ArrayList<>();
		for (Patient p: patientRepository.findAll()) {
			patientList.add(showPatient(p.getIdP()));
		}
		return patientList;
	}

	@Override
	public void addPatient(Patient p) {
	patientRepository.save(p);
		User user = new User(0L,p.getNomP(), encoder.encode(p.getPassword()),true,"PATIENT");
		userRepository.save(user);
	}

	@Override
	public void deletePatientById(long id) {
		for (Consultation c : consutationRepository.findAll()){
			if (c.getPatient().getIdP()==id){
				consutationRepository.delete(c);
				c.getPatient().getConsultationList().remove(c);
			}
		}
		for(User ur : userRepository.findAll()){
			if (ur.getUsername().equals(patientRepository.getById(id).getNomP())){
				userRepository.delete(ur);
			}
		}
		patientRepository.deleteById(id);
	}

	@Override
	public void updatePatient(long id,Patient p){
		Patient p2=patientRepository.getById(id); 
		if(p.getNomP()!=null)
			p2.setNomP(p.getNomP());
		if(p.getPrenomP()!=null)
			p2.setPrenomP(p.getPrenomP());
		if(p.getTelP()!=null)
			p2.setTelP(p.getTelP()); 
		if(p.getAdresseP()!=null)
			p2.setAdresseP(p.getAdresseP());
		if(p.getEmailP()!=null)
			p2.setEmailP(p.getEmailP());
		if (p.getPassword()!=null)
			p2.setPassword(p.getPassword());
		patientRepository.save(p2); 
		
	}

	@Override
	public Patient showPatient(Long idP) {
		Patient p = patientRepository.getById(idP);
		Patient p1=new Patient();
		List<Consultation> l1=new ArrayList<>();
		for (Consultation cc : consutationRepository.findAll()){
			if (cc.getPatient().getIdP()==idP){

				Consultation c11=new Consultation();
				c11.setId(cc.getId());
				c11.setDate(cc.getDate());
				Docteur dc = new Docteur();
				dc.setIdCin(cc.getDocteur().getIdCin());
				dc.setNom(cc.getDocteur().getNom());
				dc.setPrenom(cc.getDocteur().getPrenom());
				dc.setEmail(cc.getDocteur().getEmail());
				dc.setNumPhone(cc.getDocteur().getNumPhone());
				dc.setAddress(cc.getDocteur().getAddress());
				c11.setDocteur(dc);
				l1.add(c11);
			}
		}
		p1.setIdP(p.getIdP());
		p1.setNomP(p.getNomP());
		p1.setPrenomP(p.getPrenomP());
		p1.setEmailP(p.getEmailP());
		p1.setAdresseP(p.getAdresseP());
		p1.setTelP(p.getTelP());
		p1.setPassword(p.getPassword());
		p1.setConsultationList(l1);

		return p1;
	}

	@Override
	public void deleteConsultationFromPatient(Long idC) {
		for (Patient p : patientRepository.findAll()){
			p.getConsultationList().removeIf(c -> c.getId().equals(idC));
			patientRepository.save(p);
		}
		consutationRepository.deleteById(idC);
	}

	@Override
	public void addConsultationToPatient(Long idP,Long idD, Consultation c) {
		c.getDate().setHours(c.getDate().getHours()-1);
		c.setPatient(patientRepository.getById(idP));
		c.setDocteur(docteurRepository.findById(idD).get());
		consutationRepository.save(c);
		Patient p = patientRepository.findById(idP).get();
				p.getConsultationList().add(c);
		patientRepository.save(p);
	}

	@Override
	public Consultation showConsultation(Long idC) {
		Consultation cc = consutationRepository.findById(idC).get();
		Patient pa=cc.getPatient();
 		Patient p1=new Patient();
		p1.setIdP(pa.getIdP());
		p1.setNomP(pa.getNomP());
		p1.setPrenomP(pa.getPrenomP());
		p1.setAdresseP(pa.getAdresseP());
		p1.setEmailP(pa.getEmailP());
		p1.setTelP(pa.getTelP());

		Docteur d=new Docteur();
		d.setIdCin(cc.getDocteur().getIdCin());
		d.setNom(cc.getDocteur().getNom());
		d.setPrenom(cc.getDocteur().getPrenom());
		d.setAddress(cc.getDocteur().getAddress());
		d.setNumPhone(cc.getDocteur().getNumPhone());
		d.setEmail(cc.getDocteur().getEmail());

		Consultation c=new Consultation();
		c.setId(cc.getId());

		int i = cc.getDate().getHours() + 1;
		cc.getDate().setHours(i);
		c.setDate(cc.getDate());

		c.setPatient(p1);
		c.setDocteur(d);
		return c;
	}
}
