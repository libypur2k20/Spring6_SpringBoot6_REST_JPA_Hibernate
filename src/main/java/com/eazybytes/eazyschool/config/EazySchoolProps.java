package com.eazybytes.eazyschool.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Component("eazySchoolProps")
//@PropertySource("classpath:eazyschool.properties")
@Getter
@Setter
@ConfigurationProperties(prefix = "eazyschool")
@Validated
public class EazySchoolProps {

    @Range(min = 5, max = 25, message = "Page size must be between 5 and 25")
    private int pageSize;

    private Map<String,String> contact;

    private List<String> branches;
}
