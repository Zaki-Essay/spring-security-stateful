package ma.ensah.gestionpatientspring.dao;

import ma.ensah.gestionpatientspring.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IPatientDao extends JpaRepository<Patient,Long> {

    Page<Patient> findByNomContains(String keyword, Pageable pageable);


}
