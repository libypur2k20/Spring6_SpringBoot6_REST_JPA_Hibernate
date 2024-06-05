package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Address;
import com.eazybytes.eazyschool.model.Person;
import com.eazybytes.eazyschool.model.Profile;
import com.eazybytes.eazyschool.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller("profileControllerBean")//To avoid collision with 'ProfileController' created by Spring Boot data REST.
public class ProfileController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/displayProfile")
    public String displayProfile(Model model, HttpSession session) {

        Profile profile = new Profile();
        loadProfileFromDatabase(profile, session);
        model.addAttribute("profile", profile);

        return "profile.html";
    }

    @RequestMapping(value = "/updateProfile", method = {RequestMethod.POST})
    public String updateProfile(@Valid @ModelAttribute("profile") Profile profile, Errors errors, HttpSession session) {

        if (errors.hasErrors()) {
            return "profile.html";
        } else {
            Person person = (Person)session.getAttribute("loggedInPerson");
            log.info("Updating profile for user: " + person.getName());
            Person updatedPerson = updateProfileInDatabase(profile,person);

            session.setAttribute("loggedInPerson", updatedPerson);
            return "redirect:/displayProfile";
        }
    }


    private Person updateProfileInDatabase(Profile profile, Person person) {

        person.setName(profile.getName());
        person.setMobileNumber(profile.getMobileNumber());
        person.setEmail(profile.getEmail());

        if(profile.getAddress1()!= null) {
            if (person.getAddress() == null) {
                person.setAddress(new Address());
            }
            person.getAddress().setAddress1(profile.getAddress1());
            person.getAddress().setAddress2(profile.getAddress2());
            person.getAddress().setCity(profile.getCity());
            person.getAddress().setState(profile.getState());
            person.getAddress().setZipCode(profile.getZipCode());
        }

        return personRepository.save(person);

    }

    private void loadProfileFromDatabase(Profile profile, HttpSession session) {

        Person person = (Person)session.getAttribute("loggedInPerson");

        profile.setName(person.getName());
        profile.setMobileNumber(person.getMobileNumber());
        profile.setEmail(person.getEmail());

        if(person.getAddress()!= null) {
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZipCode());
        }
    }
}
