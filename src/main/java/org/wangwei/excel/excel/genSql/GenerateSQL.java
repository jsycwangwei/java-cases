package org.wangwei.excel.excel.genSql;

import java.util.List;

import org.wangwei.excel.excel.vo.Cms;

public class GenerateSQL {
    public void generateSQL() throws Exception {
        ReadCmsExcl xls = new ReadCmsExcl();
        List<Cms> list = xls.readXls();
        // 根据excel中的值校验数据库中是否重复；
        // 直接读取excel
        StringBuffer sb = new StringBuffer();
        StringBuffer sql = new StringBuffer();
        for (Cms c : list) {
            sql.append("insert into cb.wms_cms(cms_key,lan_code,cms_value) values");
            sql.append("(");
            sql.append("'").append(c.getKey()).append("',");
            sql.append(c.getLanCode()).append(",");
            sql.append("'").append(c.getVal()).append("'");
            sql.append(");");
            sql.append("\n");
            sb.append(sql);
            sql.delete(0, sql.length());
        }
        System.out.println(sb.toString());
    }
}
