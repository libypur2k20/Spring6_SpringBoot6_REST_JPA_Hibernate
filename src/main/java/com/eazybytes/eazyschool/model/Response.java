package com.eazybytes.eazyschool.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response {

    private int statusCode;
    private String statusMsg;
}
