package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Courses;
import com.eazybytes.eazyschool.model.EazyClass;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.repository.ClassRepository;
import com.eazybytes.eazyschool.repository.CoursesRepository;
import com.eazybytes.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping(value={"/admin"})
public class AdminController {

    @Autowired
    ClassRepository classRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @RequestMapping(value={"/displayClasses"})
    public ModelAndView displayClasses(Model model){

        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("eazyClass", new EazyClass());
        modelAndView.addObject("eazyClasses", classRepository.findAll());
        return modelAndView;

    }

    @PostMapping(value={"/addNewClass"})
    public String addNewClass(@Valid @ModelAttribute("eazyClass") EazyClass eazyClass, Errors errors){
        if(errors.hasErrors()){
            return "classes.html";
        }

        log.info("Adding new class: " + eazyClass.getName());
        classRepository.save(eazyClass);

        return "redirect:/admin/displayClasses";
    }

    @GetMapping(value={"/deleteClass"})
    public String deleteClass(@RequestParam("id") int id){

        Optional<EazyClass> eazyClass = classRepository.findById(id);

        eazyClass.get().getPersons().forEach(person -> {
            person.setEazyClass(null);
            personRepository.save(person);
        });

        classRepository.deleteById(id);

        return "redirect:/admin/displayClasses";

    }


    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<EazyClass> eazyClass = classRepository.findById(classId);
        modelAndView.addObject("eazyClass",eazyClass.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("eazyClass",eazyClass.get());
        if(error != null) {
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        EazyClass eazyClass = (EazyClass) session.getAttribute("eazyClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId()
                    +"&error=true");
            return modelAndView;
        }
        personEntity.setEazyClass(eazyClass);
        personRepository.save(personEntity);
        eazyClass.getPersons().add(personEntity);
        classRepository.save(eazyClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }

    // Both EazyClass and Person entities has OneToMany & ManyToOne relationship.
    // Here EazyClass is acting as a parent entity and Person as a child entity.
    // Since we are invoking save() for EazyClass, the JPA will automatically save
    // the child entity details as well along with the parent details.
    @GetMapping(value={"/deleteStudent"})
    public String deleteStudent(@RequestParam("personId") int studentId, HttpSession session){

        Optional<Person> person = personRepository.findById(studentId);
        person.get().setEazyClass(null);

        EazyClass eazyClass = (EazyClass)session.getAttribute("eazyClass");
        eazyClass.getPersons().remove(person.get());
        EazyClass eazyClassSaved = classRepository.save(eazyClass);
        session.setAttribute("eazyClass",eazyClassSaved);

        return "redirect:/admin/displayStudents?classId="+eazyClassSaved.getClassId();
    }

    @RequestMapping(value={"/displayCourses"})
    public ModelAndView displayCourses(Model model){

        ModelAndView modelAndView = new ModelAndView("courses_secure.html");

        modelAndView.addObject("course", new Courses());
        //Static Sorting
        //modelAndView.addObject("courses", coursesRepository.findByOrderByName());
        // Dynamic Sorting
        modelAndView.addObject("courses", coursesRepository.findAll(Sort.by("name").descending()));

        return modelAndView;
    }

    @PostMapping(value={"/addNewCourse"})
    public String addNewCourse(@Valid @ModelAttribute("course") Courses course, Errors errors){

        if(errors.hasErrors()){
            return "courses_secure.html";
        }

        log.info("Adding new course: " + course.getName());
        coursesRepository.save(course);

        return "redirect:/admin/displayCourses";
    }

    @GetMapping(value={"/viewStudents"})
    public ModelAndView viewStudents(@RequestParam() int id, @RequestParam(required = false) String error, HttpSession session){

        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Courses course = coursesRepository.findById(id).get();
        modelAndView.addObject("courses", course);
        modelAndView.addObject("person", new Person());
        session.setAttribute("courses", course);

        if(error != null) {
            modelAndView.addObject("errorMessage", "Invalid Email entered!!");
        }

        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView();
        Person personEntity = personRepository.readByEmail(person.getEmail());
        Courses course = (Courses) session.getAttribute("courses");
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+course.getCourseId()
                    +"&error=true");
            return modelAndView;
        }

        personEntity.getCourses().add(course);
        course.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses", course);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+course.getCourseId());
        return modelAndView;
    }

    @GetMapping(value={"/deleteStudentFromCourse"})
    public String deleteStudentFromCourse(@RequestParam() int personId, HttpSession session){
        Courses course = (Courses) session.getAttribute("courses");
        Person person = personRepository.findById(personId).get();
        course.getPersons().remove(person);
        person.getCourses().remove(course);
        personRepository.save(person);
        session.setAttribute("courses", course);

        return "redirect:/admin/viewStudents?id=" + course.getCourseId();
    }
}
