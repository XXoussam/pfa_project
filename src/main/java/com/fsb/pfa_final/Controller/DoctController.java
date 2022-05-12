package com.fsb.pfa_final.Controller;

import com.fsb.pfa_final.entities.Consultation;
import com.fsb.pfa_final.entities.Docteur;
import com.fsb.pfa_final.entities.Specialite;
import com.fsb.pfa_final.sercice.IDocteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/doctor")
public class DoctController {
    @Autowired
    IDocteurService iDocteurService;

    @GetMapping(value = "/allDoctors")
    public ArrayList<Docteur> allDocters() {
        return iDocteurService.allDocters();
    }

    @GetMapping(value = "/allSpeciality")
    public ArrayList<Specialite> allSpecialite() {
        return iDocteurService.allSpecialite();
    }

    @GetMapping(value = "/findDoctorByName/{name}")
    public Docteur findDoctByName(@PathVariable String name) {
        return iDocteurService.findDoctByName(name);
    }

    @GetMapping(value = "/findDoctorBySpes/{name}")
    public ArrayList<Docteur> findDocterBySpecialite(@PathVariable String name) {
        return iDocteurService.findDocterBySpecialite(name);
    }

    @RequestMapping(value = "/addDoctor",method = RequestMethod.POST)
    public void addDoctor(@RequestBody Docteur docteur) {
        iDocteurService.addDoctor(docteur);
    }

    @PostMapping(value = "/addSpecialityToDoctor/{id}")
    public void addSpecialiteToDoctor(@RequestBody Specialite specialite,
                                      @PathVariable(name = "id") Long idDoct) {
        iDocteurService.addSpecialiteToDoctor(specialite, idDoct);
    }

    @DeleteMapping(value = "/deleteDoctorById/{id}")
    public void deleteDoctorById(@PathVariable(name = "id") Long idDoct) {
        iDocteurService.deleteDoctorById(idDoct);
    }

    @GetMapping(value = "/allConsultation")
    public ArrayList<Consultation> allConsultation() {
        return iDocteurService.allConsultation();
    }

    @GetMapping(value = "/findConsultationByDoctorId/{id}")
    public ArrayList<Consultation> findConsultationByDoctor(@PathVariable(name = "id") Long idDoct) {
        return iDocteurService.findConsultationByDoctor(idDoct);
    }

    @PostMapping("/addConsultationToDoctor/{idD}/{idP}")
    public void addConsultationToDoctor(@RequestBody Consultation c,
                                        @PathVariable(name = "idD") Long idD,
                                        @PathVariable(name = "idP") Long idP) {
        iDocteurService.addConsultationToDoctor(c, idD,idP);
    }

    @DeleteMapping(value = "/deleteOldConsultation/{idD}")
    public void deleteOldConsultation(@PathVariable(name = "idD") Long idDoct) {
        iDocteurService.deleteOldConsultation(idDoct);
    }

    @GetMapping(value = "/findByAddressAndSpecialites/{adr}/{spec}")
    public List<Docteur> findByAddressAndSpecialites(@PathVariable String adr,@PathVariable String spec) {
        return iDocteurService.findByAddressAndSpecialites(adr, spec);
    }

}
