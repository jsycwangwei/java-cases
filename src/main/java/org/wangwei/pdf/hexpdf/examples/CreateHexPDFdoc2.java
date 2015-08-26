/*
 * Copyright 2014 Frank J. Øynes, heksemann@gmail.com Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License. HexPDF is a simple Java class making it easier to use Apache PDFBox for creating pdf documents in from your
 * Java application or web-service. HexPDF adds stuff like automatic page adding, word-wrap, newline awareness,
 * left/right/center text alignment, table creation and image insertion. It also has support for text colour and page
 * footers. This file, CreateHexPDFdoc.java is an example showing basic HexPDF usage.
 */
package org.wangwei.pdf.hexpdf.examples;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.wangwei.pdf.hexpdf.Footer;
import org.wangwei.pdf.hexpdf.HexPDF;
import org.wangwei.pdf.hexpdf.HexPDF2;

/**
 * Create a simple, two-page, A4 document with text, images and a table. Demonstrates usage of HexPDF
 *
 * @author Frank J. Øynes, heksemann@gmail.com
 */
public class CreateHexPDFdoc2 {

    private void createDocument() throws Exception {

        Object[][] table = getTable();

        // Create a new document and include a default footer
        HexPDF2 doc = new HexPDF2();

        createFooter(doc);

        // Create the first page
        doc.newPage();

        doc.drawTable(table, new float[]{150, 100}, new int[]{HexPDF.CENTER, HexPDF.LEFT}, HexPDF.LEFT);
        doc.drawTable(table, new float[]{150, 100}, new int[]{HexPDF.CENTER, HexPDF.LEFT}, HexPDF.LEFT);

        // Save the document
        doc.finish("/Users/jsycwangwei/myHexPDFfile.pdf");
    }

    private void createFooter(HexPDF2 doc) {
        doc.setFooter(Footer.defaultFooter);
        // Change center text in footer
        doc.getFooter().setCenterText("A simple PDF document\nWritten by me");
        // Use footer also on first page
        doc.getFooter().setOMIT_FIRSTPAGE(false);
    }

    public CreateHexPDFdoc2() {
        //
    }

    // Helper functions used to retrieve text, table and images
    private BufferedImage getImage(String fn, int w, int h) {
        BufferedImage image = null;
        try {
            // URL url = new URL(fn);
            BufferedImage src = ImageIO.read(new File(fn));
            Image scale = src.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.getGraphics();
            g.drawImage(scale, 0, 0, null);
            g.dispose();
        }
        catch (IOException ex) {
            Logger.getLogger(CreateHexPDFdoc2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

    private String getText(int num) {
        String[] txt = {
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Multa sunt dicta ab antiquis de contemnendis ac despiciendis rebus humanis; Duo Reges: constructio interrete. Id Sextilius factum negabat. Tum Quintus: Est plane, Piso, ut dicis, inquit.\n\n",
                "Quasi vero, inquit, perpetua oratio rhetorum solum, non etiam philosophorum sit. Septem autem illi non suo, sed populorum suffragio omnium nominati sunt. Cur post Tarentum ad Archytam? Quia nec honesto quic quam honestius nec turpi turpius. Re mihi non aeque satisfacit, et quidem locis pluribus. Unum nescio, quo modo possit, si luxuriosus sit, finitas cupiditates habere. Ad quorum et cognitionem et usum iam corroborati natura ipsa praeeunte deducimur.\n\n"};

        String ret = "";
        for (int i = 0; i < num && i < txt.length; i++) {
            ret += txt[i];
        }
        return ret;
    }

    private Object[][] getTable() throws Exception {
        String base = "/Users/jsycwangwei/Downloads/1.jpg";

        Object[][] tab = {{"pic", "Country"}, {getImage(base, 150, 40), "Norway"}, {getImage(base, 150, 40), "Sweden"},
                {getImage(base, 150, 40), "Denmark"}, {getImage(base, 150, 40), "Vietnam"}};

        return tab;
    }

    public static void main(String[] args) throws Exception {
        CreateHexPDFdoc2 hex = new CreateHexPDFdoc2();
        hex.createDocument();
    }
}
