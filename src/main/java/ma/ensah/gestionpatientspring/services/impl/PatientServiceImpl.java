package ma.ensah.gestionpatientspring.services.impl;

import ma.ensah.gestionpatientspring.dao.IPatientDao;
import ma.ensah.gestionpatientspring.entities.Patient;
import ma.ensah.gestionpatientspring.services.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;


@Service
public class PatientServiceImpl  implements IPatientService {

    @Autowired
    IPatientDao patientDao;



    //to save patient in database
    @Override
    public void save(Patient patient){

        patientDao.save(patient);

    }


    public Page<Patient> findAll(int page,int size) {
        return patientDao.findAll(PageRequest.of(page,size));
    }

    @Override
    public Page<Patient> findByNomContains(int page,int size,String keyword) {
        return patientDao.findByNomContains(keyword,PageRequest.of(page,size));

    }

    @Override
    public void delete(Long id) {
        patientDao.deleteById(id);
    }

    @Override
    public Patient findById(Long id) {
        return patientDao.findById(id).orElse(null);
    }


}
