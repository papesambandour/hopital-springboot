package diti5.com.hospital.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import diti5.com.hospital.model.Utilisateur;
@Repository
public interface UtilisateurDAO extends JpaRepository<Utilisateur, Integer>{
	public Utilisateur findByUsername(String username);
	public Utilisateur findByPasswordAndUsername(String password,String username);
}
