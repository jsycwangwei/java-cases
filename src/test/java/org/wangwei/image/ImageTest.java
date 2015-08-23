package org.wangwei.image;

public class ImageTest {
    public static void main(String[] args) {
        ImageUtils imageUtils = new ImageUtils();
        try {
            imageUtils.resizeImg("/Users/jsycwangwei/code128.jpg", 10, 1000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
