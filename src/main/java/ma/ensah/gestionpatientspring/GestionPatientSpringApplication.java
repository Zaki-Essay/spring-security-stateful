package ma.ensah.gestionpatientspring;

import ma.ensah.gestionpatientspring.entities.Patient;
import ma.ensah.gestionpatientspring.security.service.ISecurityService;
import ma.ensah.gestionpatientspring.services.impl.PatientServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class GestionPatientSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionPatientSpringApplication.class, args);
    }



    /*
    *
    * interface with a run method. Spring Boot will automatically
    * call the run method of all beans implementing this interface
    * after the application context has been loaded.
    *
    * the Method Work just only if it's annotate with bean annotation
    * */
    //@Bean
    CommandLineRunner commandLineRunner(PatientServiceImpl patientService){
        return args -> {

            //save patients in database
            patientService.save(new Patient(null,"patient-name-1",new Date(),true,233));
            patientService.save(new Patient(null,"patient-name-2",new Date(),true,33));
            patientService.save(new Patient(null,"patient-name-3",new Date(),true,263));
            patientService.save(new Patient(null,"patient-name-4",new Date(),true,243));
            patientService.save(new Patient(null,"patient-name-5",new Date(),true,2063));
            patientService.save(new Patient(null,"patient-name-6",new Date(),true,205));

            //print all patients in console
            Page<Patient> patientPage  = patientService.findAll(0,5);
            List<Patient> patientList = new ArrayList<>(patientPage.getContent());
            patientList.forEach(System.out::println);

            System.out.println(patientPage.getTotalPages());




        };
    }

    //@Bean
    CommandLineRunner setUsers(ISecurityService securityService){

        return args -> {
           securityService.saveNewUser("user1","1111","1111");
           securityService.saveNewUser("user2","1111","1111");
           securityService.saveNewUser("user3","1111","1111");
           securityService.saveNewUser("user4","1111","1111");


            securityService.saveNewRole("ADMIN","");
            securityService.saveNewRole("USER","");

            securityService.addRoleToUser("user1","USER");
            securityService.addRoleToUser("user1","ADMIN");
            securityService.addRoleToUser("user2","USER");
            securityService.addRoleToUser("user3","USER");
            securityService.addRoleToUser("user4","USER");



        };



    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
