package org.wangwei.enum_;

/**
 * @author jsycwangwei
 * @since 15/8/29 上午7:37
 */
public enum Status {
    OK(1, "GOOD."),
    BAD(2, "NOT GOOD.");

    private Status(int k, String val){
        this.k = k;
        this.val = val;
    }

    private int k;
    private String val;

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public static Status valueOf(int k){
        return values()[--k];
    }
}
