package com.fsb.pfa_final.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
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
	private String password;
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Consultation> consultationList;


	@Override
	public String toString() {
		return "Patient [idP=" + idP + ", prenomP=" + prenomP + ", nomP=" + nomP + ", emailP=" + emailP + ", telP="
				+ telP + ", adresseP=" + adresseP + "]";
	}
}
