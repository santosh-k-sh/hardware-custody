package com.emaratech.custody.hardwarecustody;

import lombok.var;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

@Component
public class ExcelFileUtil {

    @Autowired
    private ResourceLoader resourceLoader;

    public Map<String, List<EmailDTO>> readDataFromExcelSheet() {
        Map<String, List<EmailDTO>> dataList = new HashMap<>();

        try {

            Resource resource = resourceLoader.getResource("classpath:hardware-custory.xlsx");
            InputStream inputStream = resource.getInputStream();

            /*String filePath = System.getProperty("user.dir");
            System.out.println(filePath);
            FileInputStream file = new FileInputStream(new File(filePath + "/hardware-custory.xlsx"));
*/
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<EmailDTO> employeeData = new ArrayList<>();

            for (int i = 2; i < sheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow currentRow = sheet.getRow(i);

                EmailDTO emailDTO = new EmailDTO();
                emailDTO.setEmployeeId(currentRow.getCell(0).getStringCellValue());
                emailDTO.setEmployeeName(currentRow.getCell(1).getStringCellValue());
                emailDTO.setEmailId(currentRow.getCell(2).getStringCellValue());
                emailDTO.setAssetNumber(currentRow.getCell(3).getStringCellValue());
                emailDTO.setFAName(currentRow.getCell(4).getStringCellValue());
                emailDTO.setFAGroup(currentRow.getCell(5).getStringCellValue());
                emailDTO.setFASerialNumber(currentRow.getCell(6).getStringCellValue());
                emailDTO.setBrand(currentRow.getCell(7).getStringCellValue());
                emailDTO.setModel(currentRow.getCell(8).getStringCellValue());

                if(dataList.size() > 0) {
                    EmailDTO empData = employeeData.stream()
                            .filter(emp -> emp.getEmployeeId().equals(emailDTO.getEmployeeId()))
                            .findAny()
                            .orElse(null);

                    if(dataList.containsKey(emailDTO.getEmployeeId())) {
                        if(empData != null) {
                            employeeData.add(emailDTO);
                            dataList.put(emailDTO.getEmployeeId(), employeeData);
                        }
                    } else {
                        if(empData == null) {
                            employeeData = new ArrayList<>();
                            employeeData.add(emailDTO);
                            dataList.put(emailDTO.getEmployeeId(), employeeData);
                        }
                    }
                } else {
                    employeeData = new ArrayList<>();
                    employeeData.add(emailDTO);
                    dataList.put(emailDTO.getEmployeeId(), employeeData);
                }

                /*for(int j=0; j<currentRow.getPhysicalNumberOfCells(); j++) {
                    Cell currentCell = currentRow.getCell(j);
                    System.out.print(currentCell.getStringCellValue() + " ");
                }*/
            }

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    System.out.print(cell.getStringCellValue() + " ");

                    //CellsHelper.cellNameToIndex("C6");

                    //if(cell.getStringCellValue())

                }
                System.out.println(" ");
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }
   /* int row = 1;
    String accountId = getCellData("Account ID", row);
    String transactionId = getCellData("Transaction ID", row);

    public String getCellData(String column, int row){
        var dataRow = currentSheet.getRow(row);
        return getCellDataAsString(dataRow.getCell(columns.get(column)));
    }

    public String getCellDataAsString(Cell cell){
        return  switch(cell.getCellType()){
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int)cell.getNumericCellValue());
            default -> "";
        };
    }*/
}
