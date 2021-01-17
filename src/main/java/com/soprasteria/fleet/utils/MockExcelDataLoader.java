package com.soprasteria.fleet.utils;

import com.soprasteria.fleet.enums.Language;
import com.soprasteria.fleet.models.StaffMember;
import com.soprasteria.fleet.repositories.StaffMemberRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

@Component
public class MockExcelDataLoader implements ApplicationRunner {
    private final StaffMemberRepository staffMemberRepository;
    private final String path = "./src/main/resources/dataloader.xlsx";

    enum Columns {
        FIRST_NAME,
        LAST_NAME,
        HAS_CAR,
        CORPORATE_EMAIL,
        COMM_LANGUAGE,
    }

    public MockExcelDataLoader(StaffMemberRepository staffMemberRepository) {
        this.staffMemberRepository = staffMemberRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (staffMemberRepository.count() == 0) {
            FileInputStream inputStream = null;
            XSSFWorkbook workbook = null;
            try {
                workbook = new XSSFWorkbook(new FileInputStream(path));
                XSSFSheet sheetT = workbook.getSheet("staff");
                for (Iterator<Row> it = sheetT.rowIterator(); it.hasNext(); ) {
                    Row row = it.next();
                    if (row.getRowNum() == 0) {
                        row = it.next();
                    }
                    staffMemberRepository.save(new StaffMember(
                            row.getCell(Columns.LAST_NAME.ordinal()).getStringCellValue(),
                            row.getCell(Columns.FIRST_NAME.ordinal()).getStringCellValue(),
                            row.getCell(Columns.HAS_CAR.ordinal()).getStringCellValue().equalsIgnoreCase("yes"),
                            row.getCell(Columns.CORPORATE_EMAIL.ordinal()).getStringCellValue(),
                            Language.valueOf(row.getCell(Columns.COMM_LANGUAGE.ordinal()).getStringCellValue())
                    ));
                }
            } catch (FileNotFoundException fnf) {
                System.out.println("File path is wrong or file is in use");
            } catch (IOException ioe) {
                System.out.println("IOException ".toUpperCase() + ioe.getMessage());
            } catch (Exception e) {
                System.out.println("Exception ".toUpperCase() + e.getMessage());
            } finally {
                try {
                    if (workbook != null) workbook.close();
                    System.out.println("Workbook closed");
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            }
        }
    }
}
