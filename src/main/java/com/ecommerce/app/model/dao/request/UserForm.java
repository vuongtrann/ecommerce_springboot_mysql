package com.ecommerce.app.model.dao.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserForm {
    private String firstName;
    private String lastName;
    private String phone;
}
