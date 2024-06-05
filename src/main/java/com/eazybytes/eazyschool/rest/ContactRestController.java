package com.eazybytes.eazyschool.rest;

import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.model.Response;
import com.eazybytes.eazyschool.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
//Approach 1: REST
//@Controller
//Approach 2: REST
@RestController
@RequestMapping(path = "/api/contact", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@CrossOrigin(origins = "*", allowedHeaders = "*") // Will allow any domain.
public class ContactRestController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/getMessagesByStatus")
    //Approach 1: REST
    //@ResponseBody
    public List<Contact> getMessagesByStatus(@RequestParam("status") String status) {
        return contactRepository.findByStatus(status);
    }

    @GetMapping("getAllMsgsByStatus")
    //Approach 1: REST
    //@ResponseBody
    public List<Contact> getAllMsgsByStatus(@RequestBody Contact contact){
        if (contact != null && contact.getStatus()!= null) {
            return contactRepository.findByStatus(contact.getStatus());
        } else{
            return List.of();
        }
    }

    @PostMapping("/saveMsg")
    //@ResponseBody
    public ResponseEntity<Response> saveMsg(@RequestHeader("invocationFrom") String invocationFrom,
                                            @Valid @RequestBody Contact contact) {

        log.info(String.format("Header invocationForm: %s", invocationFrom));
        contactRepository.save(contact);

        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatusMsg("Message saved successfully");

        return ResponseEntity.status(HttpStatus.CREATED).header("isMsgSaved","true")
                .header("msgId", Integer.toString(contact.getContactId())).body(response);
    }

    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity) {

        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info(String.format(
                    "Header '%s' = %s", key,value.stream().collect(Collectors.joining("|"))));

        });

        Contact contact = requestEntity.getBody();
        contactRepository.deleteById(contact.getContactId());
        Response response = new Response();
        response.setStatusCode(HttpStatus.OK.value());
        response.setStatusMsg("Message deleted successfully");

        return ResponseEntity.status(HttpStatus.OK).header("isMsgDeleted","true")
                .header("msgId", Integer.toString(contact.getContactId())).body(response);

    }

    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contactReq) {
        Response response = new Response();

        Optional<Contact> contact = contactRepository.findById(contactReq.getContactId());

        if (contact.isPresent()) {
            contact.get().setStatus(EazySchoolConstants.CLOSED);
            contactRepository.save(contact.get());
        } else {
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setStatusMsg("Invalid Contact ID received");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        response.setStatusCode(HttpStatus.OK.value());
        response.setStatusMsg("Message closed successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}

