package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findByStatus(String status);

    @Query(value="SELECT * FROM contact_msg c WHERE c.status = :status", nativeQuery = true)
    Page<Contact> findByStatusWithQuery(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value="UPDATE Contact c SET c.status = :status where c.contactId = :contactId")
    int updateStatusById(@Param("status") String status, @Param("contactId") int contactId);

    //Methods for NamedQueries annotated on top of Contact class
    Page<Contact> findOpenMsg(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(@Param("status") String status, @Param("contactId") int contactId);


    // Methods for NamedNativeQueries annotated on top of Contact class
    @Query(nativeQuery = true)
    Page<Contact> findOpenMsgNative(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(nativeQuery = true)
    int updateMsgStatusNative(String status, int contactId);
}
