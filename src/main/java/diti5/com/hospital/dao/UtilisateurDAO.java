package diti5.com.hospital.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import diti5.com.hospital.model.Utilisateur;
@Repository
public interface UtilisateurDAO extends JpaRepository<Utilisateur, Integer>{
	public Utilisateur findByUsername(String username);
	public Utilisateur findByMatriculeOrUsername(String matricule,String username);
//	@Query("select u from Utilisateur ")
//	public Utilisateur hhh();
}
