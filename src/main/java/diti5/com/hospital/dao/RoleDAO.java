package diti5.com.hospital.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import diti5.com.hospital.model.Role;
@Repository
public interface RoleDAO extends JpaRepository<Role, Integer> {
	
}
