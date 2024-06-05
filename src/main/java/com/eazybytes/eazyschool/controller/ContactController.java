package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping("/contact")
    public String displayContactPage(Model model){
        model.addAttribute("contact", new Contact());  //We need to pass a new Contact object to the view to perform validations.
        return "contact.html";
    }


    @PostMapping("/saveMsg")
    public String saveMsg(@Valid @ModelAttribute("contact") Contact contact, Errors errors) {  //The Model has an attribute called "contact" that should be mapped to the Contact object.

        if(errors.hasErrors()){
            log.error("Contact form validation failed due to : " + errors.toString());
            return "contact.html";
        }

        contactService.saveMessageDetails(contact);

        return "redirect:/contact";
    }

/*    @RequestMapping("/displayMessages")
    public ModelAndView displayMessages(Model model){
        List<Contact> contactMsgs = contactService.findMsgWithOpenStatus();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("contactMsgs", contactMsgs);

        return modelAndView;

    }*/

    @RequestMapping("/displayMessages/page/{pageNo}")
    public ModelAndView displayMessages(@PathVariable int pageNo, @RequestParam String sortField, @RequestParam String sortDir, Model model){

        //List<Contact> contactMsgs = contactService.findMsgWithOpenStatus();
        Page<Contact> contactMsgs = contactService.findMsgWithOpenStatus(pageNo, sortField, sortDir);
        ModelAndView modelAndView = new ModelAndView("messages.html");
        modelAndView.addObject("currentPage", pageNo);
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        modelAndView.addObject("reverseSortDir", (sortDir.equals("asc") ? "desc" : "asc"));
        modelAndView.addObject("totalPages", contactMsgs.getTotalPages());
        modelAndView.addObject("contactMsgs", contactMsgs.getContent());

        return modelAndView;

    }


    @RequestMapping("/closeMsg")
    public String closeMsg(@RequestParam(name = "id", required = true) int id){
        contactService.updateMsgStatus(id);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=desc";
    }
}
