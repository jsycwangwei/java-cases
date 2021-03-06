package org.wangwei.pdf.self;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class PDFTableGenerator {

    private PDDocument doc;

    // Generates document from Table object
    public void generatePDF(Table table) throws IOException, COSVisitorException {
        try {
            doc = new PDDocument();
            drawTable(doc, table);
            doc.save("d:/sample.pdf");
        }
        finally {
            if (doc != null) {
                doc.close();
            }
        }
    }

    // Configures basic setup for the table and draws it page by page
    public void drawTable(PDDocument doc, Table table) throws IOException {
        // Calculate pagination
        Integer rowsPerPage = new Double(Math.floor(table.getHeight() / table.getRowHeight())).intValue() - 1; // subtract
        Integer numberOfPages = new Double(Math.ceil(table.getNumberOfRows().floatValue() / rowsPerPage)).intValue();

        // Generate each page, get the content and draw it
        for (int pageCount = 0; pageCount < numberOfPages; pageCount++) {
            PDPage page = generatePage(doc, table);
            PDPageContentStream contentStream = generateContentStream(doc, page, table);
            Object[][] currentPageContent = getContentForCurrentPage(table, rowsPerPage, pageCount);
            drawCurrentPage(table, currentPageContent, contentStream);
        }
    }

    // Draws current page table grid and border lines and content
    private void drawCurrentPage(Table table, Object[][] currentPageContent, PDPageContentStream contentStream)
            throws IOException {
        float tableTopY =
                table.isLandscape() ? table.getPageSize().getWidth() - table.getMargin() : table.getPageSize()
                        .getHeight() - table.getMargin();

                // Draws grid and borders
                drawTableGrid(table, currentPageContent, contentStream, tableTopY);

                // Position cursor to start drawing content
                float nextTextX = table.getMargin() + table.getCellMargin();
                // Calculate center alignment for text in cell considering font height
                float nextTextY =
                        tableTopY
                        - (table.getRowHeight() / 2)
                        - ((table.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * table
                                .getFontSize()) / 4);

                // Write column headers
                writeContentLine(table.getColumnsNamesAsArray(), contentStream, nextTextX, nextTextY, table);
                nextTextY -= table.getRowHeight();
                nextTextX = table.getMargin() + table.getCellMargin();

                // Write content
                for (int i = 0; i < currentPageContent.length; i++) {
                    writeContentLine(currentPageContent[i], contentStream, nextTextX, nextTextY, table);
                    nextTextY -= table.getRowHeight();
                    nextTextX = table.getMargin() + table.getCellMargin();
                }

                contentStream.close();
    }

    // Writes the content for one line
    private void writeContentLine(Object[] lineContent, PDPageContentStream contentStream, float nextTextX,
            float nextTextY, Table table) throws IOException {
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            Object item = lineContent[i];
            if (item instanceof String) {
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(nextTextX, nextTextY);
                String text = (String) item;
                contentStream.drawString(text != null ? text : "");
                contentStream.endText();
            }
            if (item instanceof BufferedImage) {
                BufferedImage img = (BufferedImage) item;
                PDXObjectImage pdxImg = new PDPixelMap(doc, img);
                contentStream.drawXObject(pdxImg, nextTextX, nextTextY, img.getWidth(), img.getHeight());
            }
            nextTextX += table.getColumns().get(i).getWidth();
        }
    }

    private void drawTableGrid(Table table, Object[][] currentPageContent, PDPageContentStream contentStream,
            float tableTopY) throws IOException {
        // Draw row lines
        float nextY = tableTopY;
        for (int i = 0; i <= currentPageContent.length + 1; i++) {
            contentStream.drawLine(table.getMargin(), nextY, table.getMargin() + table.getWidth(), nextY);
            nextY -= table.getRowHeight();
        }

        // Draw column lines
        final float tableYLength = table.getRowHeight() + (table.getRowHeight() * currentPageContent.length);
        final float tableBottomY = tableTopY - tableYLength;
        float nextX = table.getMargin();
        for (int i = 0; i < table.getNumberOfColumns(); i++) {
            contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
            nextX += table.getColumns().get(i).getWidth();
        }
        contentStream.drawLine(nextX, tableTopY, nextX, tableBottomY);
    }

    private Object[][] getContentForCurrentPage(Table table, Integer rowsPerPage, int pageCount) {
        int startRange = pageCount * rowsPerPage;
        int endRange = (pageCount * rowsPerPage) + rowsPerPage;
        if (endRange > table.getNumberOfRows()) {
            endRange = table.getNumberOfRows();
        }
        return Arrays.copyOfRange(table.getContent(), startRange, endRange);
    }

    private PDPage generatePage(PDDocument doc, Table table) {
        PDPage page = new PDPage();
        page.setMediaBox(table.getPageSize());
        page.setRotation(table.isLandscape() ? 90 : 0);
        doc.addPage(page);
        return page;
    }

    private PDPageContentStream generateContentStream(PDDocument doc, PDPage page, Table table) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, false, false);
        // User transformation matrix to change the reference when drawing.
        // This is necessary for the landscape position to draw correctly
        if (table.isLandscape()) {
            contentStream.concatenate2CTM(0, 1, -1, 0, table.getPageSize().getWidth(), 0);
        }
        contentStream.setFont(table.getTextFont(), table.getFontSize());
        return contentStream;
    }
}
