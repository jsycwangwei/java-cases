package org.wangwei.excel.excel.genSql;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.wangwei.excel.common.Common;
import org.wangwei.excel.excel.vo.Cms;

public class ReadCmsExcl {
    public List<Cms> readXls() throws IOException, Exception {
        InputStream is = new FileInputStream(Common.CMSEXCEL_PATH);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<Cms> list = new ArrayList<Cms>();
        // Read Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read Row
            Cms cmsCN = new Cms();
            Cms cmsEN = new Cms();
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    if (StringUtils.isEmpty(getValue(hssfRow.getCell(0))))
                        continue;
                    cmsCN = (Cms) cmsCN.clone();
                    cmsEN = (Cms) cmsEN.clone();
                    System.out.println(getValue(hssfRow.getCell(0)));
                    // 设置中文
                    cmsCN.setKey(getValue(hssfRow.getCell(0)));
                    cmsCN.setLanCode(1);
                    cmsCN.setVal(getValue(hssfRow.getCell(1)));
                    list.add(cmsCN);
                    // 设置英文
                    cmsEN.setKey(getValue(hssfRow.getCell(0)));
                    cmsEN.setLanCode(0);
                    cmsEN.setVal(getValue(hssfRow.getCell(2)));
                    list.add(cmsEN);
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
