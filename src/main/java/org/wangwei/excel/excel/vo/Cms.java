package org.wangwei.excel.excel.vo;

public class Cms implements Cloneable {
    private String key;
    private int lanCode;
    private String val;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getLanCode() {
        return lanCode;
    }

    public void setLanCode(int lanCode) {
        this.lanCode = lanCode;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return new StringBuffer("CMS [key]").append(key).append("[lancode]").append(lanCode).append("[value]")
                .append(val).toString();
    }

}
