package com.fsb.pfa_final.Repositories;


import com.fsb.pfa_final.entities.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecieliteRepository extends JpaRepository<Specialite, Long> {

    Specialite findSpecialiteByNom(String nom);
}
