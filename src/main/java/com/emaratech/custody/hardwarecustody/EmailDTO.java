package com.emaratech.custody.hardwarecustody;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailDTO {
    private String employeeId;
    @NotNull
    private String employeeName;
    @NotNull
    @Email
    private String emailId;
    private String assetNumber;
    private String FAName;
    private String FAGroup;
    private String FASerialNumber;
    private String Brand;
    private String model;

    @NotNull
    private String emailContent;
}
