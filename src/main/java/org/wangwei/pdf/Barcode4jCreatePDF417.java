/*
 * Copyright 2015 Focus Technology, Co., Ltd. All rights reserved.
 */
package org.wangwei.pdf;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.naming.ConfigurationException;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.krysalis.barcode4j.BarcodeException;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

/**
 * Barcode4jCreatePDF417.java
 *
 * @author wangwei-ww
 */
public class Barcode4jCreatePDF417 {

    public static void main(String[] args) throws IOException, ConfigurationException, BarcodeException,
    org.apache.avalon.framework.configuration.ConfigurationException {
        BarcodeUtil util = BarcodeUtil.getInstance();
        BarcodeGenerator gen = util.createBarcodeGenerator(buildCfg("code128"));
        OutputStream fout = new FileOutputStream("d:/code128.jpg");
        int resolution = 200;
        BitmapCanvasProvider canvas =
                new BitmapCanvasProvider(fout, "image/jpeg", resolution, BufferedImage.TYPE_BYTE_BINARY, false, 0);

        gen.generateBarcode(canvas, "adfda40076300001123424515451");
        canvas.finish();

    }

    private static Configuration buildCfg(String type) {
        DefaultConfiguration cfg = new DefaultConfiguration("barcode");
        // 条码类型
        DefaultConfiguration child = new DefaultConfiguration(type);
        cfg.addChild(child);
        // 条形码位置
        DefaultConfiguration attr = new DefaultConfiguration("human-readable");
        DefaultConfiguration subAttr = new DefaultConfiguration("placement");
        subAttr.setValue("bottom");
        attr.addChild(subAttr);
        child.addChild(attr);

        return cfg;
    }
}
