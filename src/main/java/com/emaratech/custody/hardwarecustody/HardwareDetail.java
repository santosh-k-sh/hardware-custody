package com.emaratech.custody.hardwarecustody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HardwareDetail {
   private String assetNumber;
   private String faName;
   private String faGroup;
   private String faSerialNumber;
   private String brandName;
   private String model;
}
