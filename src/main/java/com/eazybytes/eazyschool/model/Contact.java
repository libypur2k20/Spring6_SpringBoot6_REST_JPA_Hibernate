package com.eazybytes.eazyschool.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Table(name = "contact_msg")
@SqlResultSetMappings({
        @SqlResultSetMapping(name="SqlResultSetMapping.count", columns = @ColumnResult(name="cnt"))
})
@NamedQueries({
        @NamedQuery(name = "Contact.findOpenMsg", query = "SELECT c FROM Contact c WHERE c.status = :status"),
        @NamedQuery(name = "Contact.updateMsgStatus", query = "UPDATE Contact c SET status = :status WHERE c.contactId = :contactId")
})
@NamedNativeQueries({
        @NamedNativeQuery(name="Contact.findOpenMsgNative", query=" SELECT * FROM contact_msg c WHERE c.status = :status", resultClass = Contact.class),
        @NamedNativeQuery(name="Contact.findOpenMsgNative.count", query="select count(*) as cnt from contact_msg c where c.status = :status",
        resultSetMapping = "SqlResultSetMapping.count"),
        /*
        Spring Data JPA doesn´t support dynamic sorting for native queries.
        Doing that would require Spring Data to analyze the provided statement and generate
        the ORDER BY clause in the database-specific dialect.
        This would ve a very comples operation and is currently not supported by Spring Data JPA.
         */
        @NamedNativeQuery(name="Contact.updateMsgStatusNative", query="UPDATE contact_msg c SET c.status = ?1 WHERE c.contact_id = ?2")
})
public class Contact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "contact_id" )
    private int contactId;

        /*
    * @NotNull: Checks if a given field is not null but allows empty values & zero elements inside collections.
      @NotEmpty: Checks if a given field is not null and its size/length is greater than zero.
      @NotBlank: Checks if a given field is not null and trimmed length is greater than zero.
    * */

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    //@JsonProperty(value = "person_name")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Mobile Number is required")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
    private String mobileNum;

    @NotBlank(message = "Subject is required")
    @Size(min = 5, max = 50, message = "Subject must be between 5 and 50 characters")
    private String subject;

    @NotBlank(message="Message is required")
    @Size(min = 10, message = "Message must be at least 10 characters long")
    private String message;

    private String status;


    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobileNum='" + mobileNum + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
