package com.emaratech.custody.hardwarecustody;

import com.emaratech.custody.hardwarecustody.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sendemail")
public class HardwareCustodyController {
    @Autowired
    ExcelFileUtil excelFileUtil;

    private EmailConfiguration emailConfiguration;

    public HardwareCustodyController(EmailConfiguration emailConfiguration) {
        this.emailConfiguration = emailConfiguration;
    }

    @Autowired
    public EmailService emailService;

    static List<EmailDTO> emailDTOList = null;

    private static final Map<String, Map<String, String>> labels;

    static {
        labels = new HashMap<>();

        //Simple email
        Map<String, String> props = new HashMap<>();
        props.put("headerText", "Send Simple Email");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "");
        labels.put("send", props);
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String createMail() {
        Map<String, List<EmailDTO>> custodyDataMap = excelFileUtil.readDataFromExcelSheet();

        custodyDataMap.entrySet().stream()
                .forEach(element-> {
                    StringBuffer stringBuffer = new StringBuffer();

                    String employeeId = element.getKey();
                    List<EmailDTO> custodyData = element.getValue();
                    String employeeName = custodyData.get(0).getEmployeeName();
                    String emailAddress = custodyData.get(0).getEmailId();

                    List<HardwareDetail> hardwareDetails = new ArrayList<>();
                    stringBuffer.append("\n" +
                            "Below is your custody items, confirm that these items are available with you.\n" +
                            " <BR/>" +
                            " <BR/>" +
                            "Please respond back to this email within a week, else it will be considered available with you.\n" +
                            " <BR/>");
                    System.out.println(stringBuffer.toString());

                    custodyData.stream().forEach(data -> {

                        HardwareDetail hardwareDetail = new HardwareDetail(data.getAssetNumber(), data.getFAName(), data.getFAGroup(),
                                data.getFASerialNumber(), data.getBrand(), data.getModel());
                        hardwareDetails.add(hardwareDetail);
                        System.out.println(data.getEmailId() + " " + data.getAssetNumber() + " " + data.getEmployeeName());

                    });
                    Map<String, Object> model = new HashMap<>();
                    model.put("name", employeeName);
                    model.put("value", stringBuffer.toString());
                    model.put("hardwareDetails", hardwareDetails);

                   /* emailService.sendEmail("santosh.sharma@emaratech.ae",
                            "Test mail for "+emailAddress,
                            stringBuffer.toString(),
                            model);*/

                    emailService.sendEmail(emailAddress,
                            "Emaratech hardware custody report",
                            stringBuffer.toString(),
                            model);
                });

        return "email sent";
    }

    @RequestMapping(value = "/hardware-details", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getHardwareCustodyDetails() {
        Map<String, List<EmailDTO>> custodyDataMap = excelFileUtil.readDataFromExcelSheet();

        custodyDataMap.forEach((k, v) -> {
            emailDTOList = v.stream().collect(Collectors.toList());
        });

        return new ResponseEntity<List<EmailDTO>>(emailDTOList, HttpStatus.OK);
    }

    @PostMapping
    public void sendCustodyEmail(@RequestBody EmailDTO feedback,
                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException("Data is not valid");
        }

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailConfiguration.getHost());
        //mailSender.setPort(this.emailConfiguration.getPort());
        mailSender.setUsername(this.emailConfiguration.getUsername());
        mailSender.setPassword(this.emailConfiguration.getPassword());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("anees.sheikh@emaratech.ae");
        mailMessage.setTo("rc@feedback.com");
        mailMessage.setSubject("Hardware custody report " + feedback.getAssetNumber());
        mailMessage.setText(feedback.getEmailContent());

        // Send mail
        mailSender.send(mailMessage);
    }
}
