package com.fsb.pfa_final;

import com.fsb.pfa_final.Repositories.*;
import com.fsb.pfa_final.config.User;
import com.fsb.pfa_final.entities.Consultation;
import com.fsb.pfa_final.entities.Docteur;
import com.fsb.pfa_final.entities.Patient;
import com.fsb.pfa_final.entities.Specialite;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class PfaFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PfaFinalApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(DocteurRepository docteurRepository,
                                        SpecieliteRepository specieliteRepository,
                                        ConsutationRepository consutationRepository,
                                        PatientRepository patientRepository,
                                        UserRepository userRepository) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            Specialite s1 = new Specialite(null, "Allergy and Immunology", null);
            Specialite s2 = new Specialite(null, "Anesthesiology", null);
            Specialite s3 = new Specialite(null, "Dermatology", null);
            Specialite s4 = new Specialite(null, "Diagnostic radiology", null);
            Specialite s5 = new Specialite(null, "Emergency medicine", null);
            Specialite s6 = new Specialite(null, "Family medicine", null);

            Docteur d1 = new Docteur(null, "bargawi", "wael", "wael123@gmail.com",
                    "Oussam2923", 25919997L,"bnzart", null, null);
            Docteur d2 = new Docteur(null, "dali", "dallel", "dallel1333@gmail.com",
                    "Assam2923", 25889997L,"tunis", null, null);
            Docteur d3 = new Docteur(null, "dmaha", "loujey", "loojey1333@gmail.com",
                    "sam2923", 22887777L,"aryena", null, null);

            ArrayList<Specialite> sps = new ArrayList<>();
            sps.add(s1);
            sps.add(s2);
            sps.add(s5);
            d1.setSpecialites(sps);
            ArrayList<Specialite> sps1 = new ArrayList<>();
            sps1.add(s1);
            sps1.add(s2);
            sps1.add(s6);
            d2.setSpecialites(sps1);
            ArrayList<Specialite> sps2 = new ArrayList<>();
            sps2.add(s1);
            sps2.add(s2);
            sps2.add(s3);
            sps2.add(s4);
            d3.setSpecialites(sps2);

            Consultation c1 = new Consultation(null, new Date(101, 4, 22, 8, 00, 00), d1,null);
            Consultation c2 = new Consultation(null, new Date(101, 4, 25, 11, 00, 00), d2,null);
            Consultation c3 = new Consultation(null, new Date(101, 4, 27, 15, 00, 00), d1,null);
            Consultation c4 = new Consultation(null, new Date(101, 4, 28, 9, 00, 00), d1,null);
            Consultation c5 = new Consultation(null, new Date(101, 4, 29, 16, 00, 00), d2,null);
            Consultation c6 = new Consultation(null, new Date(101, 7, 2, 15, 00, 00), d3,null);


            specieliteRepository.save(s1);
            specieliteRepository.save(s2);
            specieliteRepository.save(s3);
            specieliteRepository.save(s4);
            specieliteRepository.save(s5);
            specieliteRepository.save(s6);


            docteurRepository.save(d1);
            docteurRepository.save(d2);
            docteurRepository.save(d3);

            consutationRepository.save(c1);
            consutationRepository.save(c2);
            consutationRepository.save(c3);
            consutationRepository.save(c4);
            consutationRepository.save(c5);
            consutationRepository.save(c6);

            ////////////////////////////////////////////////////////////////////////////////////////////

            Consultation c11 = new Consultation(null, new Date(101, 4, 22, 8, 00, 00),d1,null);
            Consultation c12 = new Consultation(null, new Date(101, 4, 25, 11, 00, 00),d2,null);
            Consultation c13 = new Consultation(null, new Date(101, 4, 27, 15, 00, 00),d1,null);
            Consultation c14 = new Consultation(null, new Date(101, 4, 28, 9, 00, 00),d1,null);
            Consultation c15 = new Consultation(null, new Date(101, 4, 29, 16, 00, 00),d2,null);
            Consultation c16 = new Consultation(null, new Date(101, 7, 2, 15, 00, 00),d3,null);

            consutationRepository.save(c11);
            consutationRepository.save(c12);
            consutationRepository.save(c13);
            consutationRepository.save(c14);
            consutationRepository.save(c15);
            consutationRepository.save(c16);

            List<Consultation> l1=new ArrayList<>();
            l1.add(c11);
            List<Consultation> l2=new ArrayList<>();
            l2.add(c12);
            l2.add(c13);
            List<Consultation> l3=new ArrayList<>();
            l3.add(c15);
            l3.add(c14);
            List<Consultation> l4=new ArrayList<>();
            l4.add(c16);




            Patient p1=new Patient(0L,"temimi","ahmed","ahmed11@gmail.com","24225598","kjsdljklfkj","kjùlkjùljù",l1);
            Patient p2=new Patient(0L,"saoudi","assma","ass22@gmail.com","99255598","gmana","lhkjhmhjm",l2);
            Patient p3=new Patient(0L,"saoudi","oussama","oussama13@gmail.com","25919997","benikhdech","ùlkjkmjhlhl",l3);
            Patient p4=new Patient(0L,"njr","amina","amii99@gmail.com","24225322","gasrr","ùkljlkjlkj",l4);
            patientRepository.save(p1);
            patientRepository.save(p2);
            patientRepository.save(p3);
            patientRepository.save(p4);

            c11.setPatient(p1);
            c12.setPatient(p2);
            c13.setPatient(p2);
            c14.setPatient(p3);
            c15.setPatient(p3);
            c16.setPatient(p4);

            consutationRepository.save(c11);
            consutationRepository.save(c12);
            consutationRepository.save(c13);
            consutationRepository.save(c14);
            consutationRepository.save(c15);
            consutationRepository.save(c16);

            /////////////////////////////////////////////////////////////////////////////////////////

            c1.setPatient(p1);
            c2.setPatient(p2);
            c3.setPatient(p2);
            c4.setPatient(p3);
            c5.setPatient(p3);
            c6.setPatient(p4);

            consutationRepository.save(c1);
            consutationRepository.save(c2);
            consutationRepository.save(c3);
            consutationRepository.save(c4);
            consutationRepository.save(c5);
            consutationRepository.save(c6);

            User user1=new User(0L,d1.getNom(),encoder.encode(d1.getPassword()),true,"DOCTOR");
            User user2=new User(0L,d2.getNom(),encoder.encode(d2.getPassword()),true,"DOCTOR");
            User user3=new User(0L,d3.getNom(),encoder.encode(d3.getPassword()), true,"DOCTOR");
            User user4=new User(0L,p1.getNomP(),encoder.encode(p1.getPassword()), true,"PATIENT");
            User user5=new User(0L,p2.getNomP(),encoder.encode(p2.getPassword()), true,"PATIENT");
            User user6=new User(0L,p3.getNomP(),encoder.encode(p3.getPassword()), true,"PATIENT");
            User user7=new User(0L,p4.getNomP(),encoder.encode(p4.getPassword()), true,"PATIENT");
            User user8=new User(0L,"admin",encoder.encode("nimda"), true,"ADMIN");

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
            userRepository.save(user6);
            userRepository.save(user7);
            userRepository.save(user8);

        };



    }

}
