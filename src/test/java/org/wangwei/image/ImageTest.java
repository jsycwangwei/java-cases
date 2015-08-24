package org.wangwei.image;

import org.apache.avalon.framework.configuration.ConfigurationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wangwei.pdf.Barcode4jCreatePDF128;

public class ImageTest {
    Barcode4jCreatePDF128 bar;
    long start;

    @Before
    public void before() {
        bar = new Barcode4jCreatePDF128();
        start = System.currentTimeMillis();
    }

    @Test
    public void generBarcode() {
        try {
            bar.generateBarcode("d:/code128.jpg", "code128", "focus1234567890");

            // ImageUtils imageUtils = new ImageUtils();
            // imageUtils.resizeImg("d:/code128.jpg", 100, 100);
        }
        catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        System.out.println("Time spend :" + (System.currentTimeMillis() - start));
    }
}
