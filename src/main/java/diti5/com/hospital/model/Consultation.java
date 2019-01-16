package diti5.com.hospital.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;


@Entity
public class Consultation {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private Date dateConsultation;
	@Column(length=65535, columnDefinition="TEXT")
	@Type(type="text")
	private String commentaire;
	private String prescripion;
	@ManyToOne
	private Patient patient;
	@ManyToOne
	private Service service;
	@ManyToOne
	private Utilisateur utilisateur;
	public Consultation() {}
	public Consultation(int id, Date dateConsultation, String commentaire, String prescripion, Patient patient,
			Service service) {
		super();
		this.id = id;
		this.dateConsultation = dateConsultation;
		this.commentaire = commentaire;
		this.prescripion = prescripion;
		this.patient = patient;
		this.service = service;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateConsultation() {
		return dateConsultation;
	}
	public void setDateConsultation(Date dateConsultation) {
		this.dateConsultation = dateConsultation;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public String getPrescripion() {
		return prescripion;
	}
	public void setPrescripion(String prescripion) {
		this.prescripion = prescripion;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
}
