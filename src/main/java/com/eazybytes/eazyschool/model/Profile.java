package com.eazybytes.eazyschool.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profile {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
    private String name;

    @NotBlank(message = "Mobile Number is required")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message="Address1 is required")
    @Size(min=5, message="Address1 must be at least 5 characters long")
    private String address1;

    private String address2;

    @NotBlank(message="City is required")
    @Size(min=5, message="City must be at least 5 characters long")
    private String city;

    @NotBlank(message="State is required")
    @Size(min=5, message="State must be at least 5 characters long")
    private String state;

    @NotBlank(message="Zip Code is required")
    @Pattern( regexp = "(^$|[0-9]{5})", message="Zip Code must be 5 digits")
        private String zipCode;


}
