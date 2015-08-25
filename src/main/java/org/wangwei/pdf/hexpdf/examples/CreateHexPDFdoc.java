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

import java.awt.Color;
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

/**
 * Create a simple, two-page, A4 document with text, images and a table. Demonstrates usage of HexPDF
 *
 * @author Frank J. Øynes, heksemann@gmail.com
 */
public class CreateHexPDFdoc {

    private void createDocument() throws Exception {

        // BufferedImage basemap = getImage(imgPath1, 400, 300);
        // BufferedImage overlay = getImage(imgPath2, 400, 300);
        String fn = "/Users/jsycwangwei/Downloads/1.jpg";
        BufferedImage basemap = ImageIO.read(new File(fn));
        BufferedImage overlay = ImageIO.read(new File(fn));
        Object[][] table = getTable();

        // Create a new document and include a default footer
        HexPDF doc = new HexPDF();
        doc.setFooter(Footer.defaultFooter);
        // Change center text in footer
        doc.getFooter().setCenterText("A simple PDF document\nWritten by me");
        // Use footer also on first page
        doc.getFooter().setOMIT_FIRSTPAGE(false);

        // Create the first page
        doc.newPage();

        // Add a main title, centered in shiny colours
        doc.title1Style();
        doc.setTextColor(new Color(0x2020ff));
        doc.drawText("My simple pdf document\n\n", HexPDF.CENTER);

        // Typeset everything else in boring black
        doc.setTextColor(Color.black);

        // Add a left aligned sub title followed by left aligned paragraphs
        doc.title2Style();
        doc.drawText("\n\nLeft aligned text\n\n");
        doc.normalStyle();
        doc.drawText(getText(3));

        // Add a centered image with another image as overlay, caption below
        doc.drawImage(basemap, HexPDF.CENTER);
        doc.drawImage(overlay, HexPDF.CENTER | HexPDF.NEWLINE);
        doc.drawText("Figure 1: An example figure with overlay\n", HexPDF.CENTER);

        // A new left aligned sub title, now followed by right aligned text
        doc.title2Style();
        doc.drawText("\n\nRight aligned text\n\n");
        doc.normalStyle();
        doc.drawText(getText(2), HexPDF.RIGHT);

        // Add a table centered on page, crossing page boundary.
        // Four columns, aligned left, center, right, left
        doc.drawTable(table, new float[]{150, 100, 60, 70, 200},
                new int[]{HexPDF.CENTER, HexPDF.LEFT, HexPDF.CENTER, HexPDF.RIGHT, HexPDF.LEFT}, HexPDF.CENTER);
        // Add a caption under the table
        doc.drawText("Table 1: A rather odd table, centered on page\n", HexPDF.CENTER);

        // New left aligned sub title, followed by centered paragraphs
        doc.title2Style();
        doc.drawText("\n\nCentered text\n\n");
        doc.normalStyle();
        doc.drawText(getText(4), HexPDF.CENTER);

        // Add some newlines followed by pages of text, justified
        // A final left aligned sub title followed by lods of justified text
        doc.title2Style();
        doc.drawText("\n\nJustified text\n\n");
        doc.normalStyle();
        for (int i = 0; i < 10; i++) {
            doc.drawText(getText(5), HexPDF.JUSTIFY);
        }

        // The end...
        doc.title1Style();
        doc.drawText("\n\n-- END OF DOCUMENT --", HexPDF.CENTER);

        // Save the document
        doc.finish("/Users/jsycwangwei/myHexPDFfile.pdf");
    }

    public CreateHexPDFdoc() {
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
            Logger.getLogger(CreateHexPDFdoc.class.getName()).log(Level.SEVERE, null, ex);
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

        Object[][] tab = {{null, "Country", "Area", "Population", "Info"},
                {getImage(base, 150, 40), "Norway", "col2", "col2", "col4"},
                {getImage(base, 150, 40), "Sweden", "col2", "col2", "col4"},
                {getImage(base, 150, 40), "Denmark", "col2", "col2", "col4"},
                {getImage(base, 150, 40), "Vietnam", "col2", "col2", "col4"}};

        return tab;
    }

    public static void main(String[] args) throws Exception {
        CreateHexPDFdoc hex = new CreateHexPDFdoc();
        hex.createDocument();
    }
}
