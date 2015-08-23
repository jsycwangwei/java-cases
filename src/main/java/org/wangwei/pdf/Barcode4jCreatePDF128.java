/*
 * Copyright 2015 Focus Technology, Co., Ltd. All rights reserved.
 */
package org.wangwei.pdf;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.DefaultConfiguration;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.BarcodeUtil;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

/**
 * @author jsycwangwei
 */
public class Barcode4jCreatePDF128 {

    // public static void main(String[] args) throws IOException, ConfigurationException, BarcodeException,
    // org.apache.avalon.framework.configuration.ConfigurationException {
    // BarcodeUtil util = BarcodeUtil.getInstance();
    // BarcodeGenerator gen = util.createBarcodeGenerator(buildCfg("code128"));
    // OutputStream fout = new FileOutputStream("/Users/jsycwangwei/code128.jpg");
    // int resolution = 150;
    // BitmapCanvasProvider canvas =
    // new BitmapCanvasProvider(fout, "image/jpeg", resolution, BufferedImage.TYPE_BYTE_BINARY, false, 0);
    //
    // gen.generateBarcode(canvas, "abcdefuiop1234567890");
    // canvas.finish();
    //
    // }

    /**
     * 生成条形码接口
     * 
     * @param path
     * @param type
     * @throws Exception
     * @throws org.apache.avalon.framework.configuration.ConfigurationException
     */
    public void generateBarcode(String path, String type, String code)
            throws org.apache.avalon.framework.configuration.ConfigurationException, Exception {
        BarcodeUtil util = BarcodeUtil.getInstance();
        BarcodeGenerator gen = util.createBarcodeGenerator(buildCfg(type));
        OutputStream fout = new FileOutputStream(path);
        int resolution = 150;
        BitmapCanvasProvider canvas =
                new BitmapCanvasProvider(fout, "image/jpeg", resolution, BufferedImage.TYPE_BYTE_BINARY, false, 0);

        gen.generateBarcode(canvas, code);
        canvas.finish();
    }

    private Configuration buildCfg(String type) {
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
