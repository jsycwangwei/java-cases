/**
 * 
 */
package org.wangwei.excel.client;

import java.io.IOException;

import org.wangwei.excel.excel.genSql.GenerateSQL;

/**
 * @author Hongten
 * @created 2014-5-18
 */
public class Client {

    public static void main(String[] args) throws IOException, Exception {
        // SaveData2DB saveData2DB = new SaveData2DB();
        // saveData2DB.save();

        GenerateSQL sql = new GenerateSQL();
        sql.generateSQL();
    }
}
