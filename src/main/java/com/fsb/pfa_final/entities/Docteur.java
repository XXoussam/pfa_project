package com.fsb.pfa_final.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor @ToString
public class Docteur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCin;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Long numPhone;
    private String address;
    @ManyToMany
    private List<Specialite> specialites;
    @OneToMany(mappedBy = "docteur")
    private List<Consultation> consultationList;
}
