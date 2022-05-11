package com.fsb.pfa_final.Controller;

import com.fsb.pfa_final.entities.Consultation;
import com.fsb.pfa_final.entities.Patient;
import com.fsb.pfa_final.sercice.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/patient")

public class PatientControler {
	@Autowired
	IPatientService patientService;
	
	@GetMapping("/allPatients")
	public List<Patient> showAllPatient() {
		 return patientService.showAllPatient(); 
	}

	@PostMapping("/addPatient")
	public void addPatient(@RequestBody Patient p) {
		patientService.addPatient(p); 
	}

	@DeleteMapping("/deletePatient/{idP}")////////////////////////////////A REVENIR
	public void deletePatient(@PathVariable long idP ) {
		patientService.deletePatientById(idP); 
		
	}
	@PutMapping("/updatePatient/{idP}")
	public void deletePatient(@PathVariable long idP,@RequestBody Patient p) {
		patientService.updatePatient(idP,p); 
	}

	@GetMapping("/ShowPatient/{id}")////////////////////A REVENIR ///////// MODIFIER
	public Patient showPatient(@PathVariable(name = "id")Long id){
		return patientService.showPatient(id);
	}

	@DeleteMapping("/deleteConsultationFromPatient/{idC}")
	public void deleteConsultationFromPatient(@PathVariable(name = "idC") Long idC){
		patientService.deleteConsultationFromPatient(idC);
	}


	@PostMapping("/addConsultationToPatient/{idP}/{idD}")
	public void addConsultationToPatient(@PathVariable (name = "idP")Long idP,
										 @PathVariable(name = "idD")Long idD,
										 @RequestBody Consultation c){
		patientService.addConsultationToPatient(idP,idD,c);
	}

	@GetMapping("/showConsultation/{id}")
	public Consultation showConsultation(@PathVariable(name = "id")Long id){
		return patientService.showConsultation(id);
	}







}
