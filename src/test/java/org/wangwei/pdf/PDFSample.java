package org.wangwei.pdf;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;
import org.wangwei.pdf.hexpdf.examples.CreateHexPDFdoc;
import org.wangwei.pdf.self.Column;
import org.wangwei.pdf.self.PDFTableGenerator;
import org.wangwei.pdf.self.Table;
import org.wangwei.pdf.self.TableBuilder;

public class PDFSample {

    // Page configuration
    private static final PDRectangle PAGE_SIZE = PDPage.PAGE_SIZE_A4;
    private static final float MARGIN = 20;
    private static final boolean IS_LANDSCAPE = false;

    // Font configuration
    private static final PDFont TEXT_FONT = PDType1Font.HELVETICA;
    private static final float FONT_SIZE = 10;

    // Table configuration
    private static final float ROW_HEIGHT = 15;
    private static final float CELL_MARGIN = 2;

    @Test
    public void createPDF() {
        try {
            new PDFTableGenerator().generatePDF(createContent());
        }
        catch (COSVisitorException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-genserated catch block
            e.printStackTrace();
        }
    }

    private Table createContent() {
        // Total size of columns must not be greater than table width.
        List<Column> columns = new ArrayList<Column>();
        columns.add(new Column("name", 60));
        columns.add(new Column("value", 60));

        // Object[][] content = {{"text", "hello"}, {"image", getImage("/Users/jsycwangwei/Downloads/1.jpg", 0.2f)}};
        Object[][] content = {{"text", "hello"}, {"image", getImage("/Users/jsycwangwei/Downloads/1.jpg", 0.2f)}};

        float tableHeight = IS_LANDSCAPE ? PAGE_SIZE.getWidth() - (2 * MARGIN) : PAGE_SIZE.getHeight() - (2 * MARGIN);

        Table table = new TableBuilder().setCellMargin(CELL_MARGIN).setColumns(columns).setContent(content)
                .setHeight(tableHeight).setNumberOfRows(content.length).setRowHeight(ROW_HEIGHT).setMargin(MARGIN)
                .setPageSize(PAGE_SIZE).setLandscape(IS_LANDSCAPE).setTextFont(TEXT_FONT).setFontSize(FONT_SIZE)
                .build();
        return table;
    }

    // Helper functions used to retrieve text, table and images
    private BufferedImage getImage(String fn, float sc) {
        BufferedImage image = null;
        try {
            // URL url = new URL(fn);
            BufferedImage src = ImageIO.read(new File(fn));
            int w = (int) (src.getWidth() * sc);
            int h = (int) (src.getHeight() * sc);
            Image scale = src.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.getGraphics();
            g.drawImage(scale, 0, 0, null);
            g.dispose();
        }
        catch (IOException ex) {
            Logger.getLogger(CreateHexPDFdoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
}
