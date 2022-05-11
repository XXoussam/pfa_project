package com.fsb.pfa_final.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity @Data
public class Patient {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private  long idP;  
	@Column
	private String prenomP ;
	@Column
	private String nomP;
	@Column
	private String emailP ;
	@Column
    private  String telP ;
	@Column
    private String adresseP ;
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Consultation> consultationList;


	public Patient(long idP, String prenomP, String nomP, String emailP, String telP, String adresseP, List<Consultation> consultationList) {
		this.idP = idP;
		this.prenomP = prenomP;
		this.nomP = nomP;
		this.emailP = emailP;
		this.telP = telP;
		this.adresseP = adresseP;
		this.consultationList = consultationList;
	}

	public Patient() {
	}



	@Override
	public String toString() {
		return "Patient [idP=" + idP + ", prenomP=" + prenomP + ", nomP=" + nomP + ", emailP=" + emailP + ", telP="
				+ telP + ", adresseP=" + adresseP + "]";
	}
}
