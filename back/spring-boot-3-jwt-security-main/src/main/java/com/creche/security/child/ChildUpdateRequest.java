package com.creche.security.child;

import lombok.Data;
@Data
public class ChildUpdateRequest {


        private String firstName;
        private String lastName;
        private String dateOfBirth; // format: yyyy-MM-dd
        private String allergies;
        private String specialNeeds;


}
