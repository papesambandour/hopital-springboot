package diti5.com.hospital.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Utilisateur {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=100)
	private String username;
	@Column(length=100)
	private String password;
	private int enabled;
	@Column(length=100)
	private String prenom;
	@Column(length=50)
	private String nom;
	@Column(length=10)
	private String matricule;
	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	},fetch=FetchType.EAGER)
	@JoinTable(name = "utilisateur_role",
		joinColumns = @JoinColumn(name = "utilisateur_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> listeRoles;
	@ManyToOne 
	private Service service; 
	public Utilisateur() {}
	public Utilisateur(int id, String username, String password, int enabled, String prenom, String nom,
			String matricule) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.prenom = prenom;
		this.nom = nom;
		this.matricule = matricule;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
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
	public String getMatricule() {
		return matricule;
	}
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	public List<Role> getListeRoles() {
		return listeRoles;
	}
	public void setListeRoles(List<Role> listeRoles) {
		this.listeRoles = listeRoles;
	}
	
}
