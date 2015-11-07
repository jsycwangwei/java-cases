/**
 * 
 */
package org.wangwei.excel.excel.saveDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.wangwei.excel.common.Common;
import org.wangwei.excel.excel.util.DbUtil;
import org.wangwei.excel.excel.vo.Student;

/**
 * @author Hongten
 * @created 2014-5-18
 */
public class SaveData2DB {

    @SuppressWarnings({"rawtypes"})
    public void save() throws IOException, SQLException {
        ReadExcel xlsMain = new ReadExcel();
        Student student = null;
        List<Student> list = xlsMain.readXls();
        // 根据excel中的值校验数据库中是否重复；
        for (int i = 0; i < list.size(); i++) {
            student = list.get(i);
            List l = DbUtil.selectOne(Common.SELECT_STUDENT_SQL + "'%" + student.getName() + "%'", student);
            if (!l.contains(1)) {
                DbUtil.insert(Common.INSERT_STUDENT_SQL, student);
            }
            else {
                System.out.println("The Record was Exist : No. = " + student.getNo() + " , Name = " + student.getName()
                        + ", Age = " + student.getAge() + ", and has been throw away!");
            }

            // }

            // 直接读取excel
            // StringBuffer sb = new StringBuffer();
            // StringBuffer sql = new StringBuffer();
            // for (Student s : list) {
            // sql.append("insert into cb.wms_cms(cms_key,lan_code,cms_value) values");
            // sql.append("(");
            // sql.append("'").append(s.getNo()).append("',");
            // sql.append(s.getId()).append(",");
            // sql.append("'").append(s.getName()).append("'");
            // sql.append(");");
            // sql.append("\n");
            // sb.append(sql);
            // sql.delete(0, sql.length());
            // }
            // System.out.println(sb.toString());
        }

    }
}
