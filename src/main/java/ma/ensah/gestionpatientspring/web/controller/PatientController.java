package ma.ensah.gestionpatientspring.web.controller;


import lombok.AllArgsConstructor;
import ma.ensah.gestionpatientspring.dao.IPatientDao;
import ma.ensah.gestionpatientspring.entities.Patient;
import ma.ensah.gestionpatientspring.services.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor //if we add constructor with all param ,so we don't need to @Autowired annotation to inject
public class PatientController {


        IPatientService patientService;
        IPatientDao patientDao;


        //l'architecture rendus coté serveur
        @GetMapping(path = "/user/index")
        public String index(Model model,
                            @RequestParam(name = "page",defaultValue = "0") int page,
                            @RequestParam(name="size",defaultValue = "5") int size ,
                            @RequestParam(name="keyword",defaultValue = "") String keyword){
            //list des patient
            Page<Patient> patientPage = patientService.findByNomContains(page,size,keyword);
            model.addAttribute("patientList",patientPage.getContent());
            model.addAttribute("pages",new int[patientPage.getTotalPages()]);
            model.addAttribute("currentPage",page);
            model.addAttribute("keyword",keyword);




            return "patients";
        }

        @GetMapping(path = "/")
        public String defaultMethode(){
            return "home";
        }


        @RequestMapping(path = "/admin/delete",method=RequestMethod.GET)
        public String deletePatient(@RequestParam(name = "id") Long id,@RequestParam(name = "page") int page,@RequestParam(name = "keyword") String keyword){
            patientService.delete(id);
            return "redirect:/user/index?page="+page+"&keyword="+keyword;
        }


        @GetMapping(path = "/admin/showFormAdd")
        public String showFormToAddPatient(Model model){


            model.addAttribute("patient",new Patient());

            return "formToAdd";

        }

        @PostMapping(path = "/admin/save")
        public String addPatient(Model model,
                                 @Valid Patient patient,
                                 BindingResult bindingResult,
                                 @RequestParam(name="page",defaultValue = "0") int page,
                                 @RequestParam(name="keyword" , defaultValue = "") String keyword){

            if (bindingResult.hasErrors()) return "formToAdd";

            patientService.save(patient);

            return "redirect:/user/index?page="+page+"&keyword="+keyword;

        }


        @GetMapping(path = "/admin/showFormUpdate")
        public String showFormForUpdatePatient(Model model,Long id,int page,String keyword){

            Patient patient = patientService.findById(id);

            model.addAttribute("patient",patient);
            model.addAttribute("page",page);
            model.addAttribute("keyword",keyword);




            return "formToUpdate";

        }


        //l'architecture rendus coté client

        @GetMapping(path = "/user/patient/{page}/{size}")
        @ResponseBody
        public List<Patient> patientList(@PathVariable("page") int page, @PathVariable("size") int size){
            List<Patient> patientList = patientService.findAll(page,size).getContent();
            return patientList;
        }





}
