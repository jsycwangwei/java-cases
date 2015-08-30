package org.wangwei.pdf.self;

/**
 * @author jsycwangwei
 * @since 15/8/30 上午10:24
 */
public enum LocLayout {

    CENTER(1),LEFT(2),RIGHT(4),JUSTIFY(8);

    private LocLayout(int k){
        this.k = k;
    }

    private int k;

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }
}
