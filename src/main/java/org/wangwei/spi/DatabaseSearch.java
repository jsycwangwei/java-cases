package org.wangwei.spi;

import java.util.List;

public class DatabaseSearch implements Search {

    public List<String> search(String keyword) {
        System.out.println("Search By Database");
        return null;
    }

}
