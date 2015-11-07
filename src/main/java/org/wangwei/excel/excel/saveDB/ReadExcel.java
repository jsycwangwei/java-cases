/**
 * 
 */
package org.wangwei.excel.excel.saveDB;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.wangwei.excel.common.Common;
import org.wangwei.excel.excel.vo.Student;

/**
 * @author jsycwangwei
 */
public class ReadExcel {

    public List<Student> readXls() throws IOException {
        InputStream is = new FileInputStream(Common.EXCEL_PATH);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        Student student = null;
        List<Student> list = new ArrayList<Student>();
        // Read Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    student = new Student();
                    HSSFCell no = hssfRow.getCell(0);
                    HSSFCell name = hssfRow.getCell(1);
                    HSSFCell age = hssfRow.getCell(2);
                    HSSFCell score = hssfRow.getCell(3);
                    student.setNo(getValue(no));
                    student.setName(getValue(name));
                    student.setAge(getValue(age));
                    student.setScore(Float.valueOf(getValue(score)));
                    list.add(student);
                }
            }
        }
        return list;
    }

    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        }
        else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        }
        else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
}
