package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Person;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(value={"/student"})
public class StudentController {

    @GetMapping(value={"/displayCourses"})
    public ModelAndView displayCourses(HttpSession session){

        Person loggedInPerson = (Person) session.getAttribute("loggedInPerson");

        ModelAndView modelAndView = new ModelAndView("courses_enrolled.html");
        modelAndView.addObject("person", loggedInPerson);

        return modelAndView;
    }
}
