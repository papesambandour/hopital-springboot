package diti5.com.hospital.dao;

import diti5.com.hospital.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceDOA extends JpaRepository<Service, Integer> {
}
