package diti5.com.hospital.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Patient {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=10)
	private String numDossier;
	@Column(length=100)
	private String prenom;
	@Column(length=50)
	private String nom;
	@Column(length=10)
	private String tel;
	private Date dateNaiss;
	@OneToMany(mappedBy="patient")
	private List<Consultation> listeConsultations;
	public Patient() {}
	public Patient(int id, String numDossier, String prenom, String nom, String tel, Date dateNaiss) {
		super();
		this.id = id;
		this.numDossier = numDossier;
		this.prenom = prenom;
		this.nom = nom;
		this.tel = tel;
		this.dateNaiss = dateNaiss;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumDossier() {
		return numDossier;
	}
	public void setNumDossier(String numDossier) {
		this.numDossier = numDossier;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Date getDateNaiss() {
		return dateNaiss;
	}
	public void setDateNaiss(Date dateNaiss) {
		this.dateNaiss = dateNaiss;
	}
	public List<Consultation> getListeConsultations() {
		return listeConsultations;
	}
	public void setListeConsultations(List<Consultation> listeConsultations) {
		this.listeConsultations = listeConsultations;
	}
	
}
