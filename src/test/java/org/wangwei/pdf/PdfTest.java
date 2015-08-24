package org.wangwei.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.junit.Before;
import org.junit.Test;

public class PdfTest {

    private CreatePdfDocument pdf;
    private String savePath = "D:/test.pdf";

    @Before
    public void doBefore() {
        System.out.println("before ..");
        pdf = new CreatePdfDocument();
    }

    @Test
    public void createPdfWithText() throws Exception {
        PDDocument doc = new PDDocument();

        PDPage page = new PDPage();

        doc.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        String[][] content = {{"a", "b", "1"},

        {"c", "d", "2"},

        {"e", "f", "3"},

        {"g", "h", "4"},

        {"i", "j", "5"}};

        pdf.drawTable(page, contentStream, 700, 2, content);

        contentStream.close();

        doc.save(savePath);

        AddImageToPDF imagePdf = new AddImageToPDF();

        imagePdf.createPDFFromImage(savePath, "c:/1.jpg", savePath);
    }
}
