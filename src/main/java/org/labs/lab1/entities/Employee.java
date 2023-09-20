package org.labs.lab1.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @JacksonXmlProperty(isAttribute = true)
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;

}
