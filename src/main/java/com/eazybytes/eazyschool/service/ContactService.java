package com.eazybytes.eazyschool.service;

import com.eazybytes.eazyschool.config.EazySchoolProps;
import com.eazybytes.eazyschool.constants.EazySchoolConstants;
import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.repository.ContactRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Getter
@Setter
@Service
//@RequestScope
//@SessionScope
@ApplicationScope
public class ContactService {


    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;

        log.info("Contact Service Bean initialized");
    }

    public boolean saveMessageDetails(Contact contact){

        boolean isSaved = false;

        contact.setStatus(EazySchoolConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);

        if(savedContact != null && savedContact.getContactId() > 0){
            isSaved = true;
        }

        return isSaved;
    }

/*    public List<Contact> findMsgWithOpenStatus() {

        return contactRepository.findByStatus(EazySchoolConstants.OPEN);

    }*/

    @Autowired
    EazySchoolProps eazySchoolProps;

    public Page<Contact> findMsgWithOpenStatus(int pageNo, String sortField, String sortDir) {

        int pageSize = eazySchoolProps.getPageSize();
        if (eazySchoolProps.getContact() != null && eazySchoolProps.getContact().get("pageSize")!= null) {
            pageSize = Integer.parseInt(eazySchoolProps.getContact().get("pageSize").trim());
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.Direction.valueOf(sortDir.toUpperCase()), sortField);
        return contactRepository.findByStatusWithQuery(EazySchoolConstants.OPEN, pageable);

        // Call repository method with NamedQuery annotated on top of Contact class.
        //return contactRepository.findOpenMsg(EazySchoolConstants.OPEN, pageable);

        // Call repository method with NamedNativeQuery annotated on top of Contact class.
        //return contactRepository.findOpenMsgNative(EazySchoolConstants.OPEN, pageable);

    }

    public boolean updateMsgStatus(int contactId) {

        boolean isUpdated = false;

        int rows = contactRepository.updateStatusById( EazySchoolConstants.CLOSED, contactId);

        // Call repository method with NamedQuery annotated on top of Contact class.
        //int rows = contactRepository.updateMsgStatus( EazySchoolConstants.CLOSED, contactId);

        // Call repository method with NamedNativeQuery annotated on top of Contact class.
        //int rows = contactRepository.updateMsgStatusNative( EazySchoolConstants.CLOSED, contactId);

            if (rows > 0) {
                isUpdated = true;
            }

        return isUpdated;
    }
}
