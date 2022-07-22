package ma.ensah.gestionpatientspring.services;

import ma.ensah.gestionpatientspring.entities.Patient;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface IPatientService {
    void save(Patient patient);
    Page<Patient> findAll(int page,int size);

    Page<Patient> findByNomContains(int page,int size, String keyword);

    void delete(Long id);

    Patient findById(Long id);

}
