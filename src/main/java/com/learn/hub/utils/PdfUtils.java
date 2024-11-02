package com.learn.hub.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.learn.hub.entity.CourseEntity;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class PdfUtils {

    public static ByteArrayOutputStream generateCoursePdfStream(List<CourseEntity> courses) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfPTable table = new PdfPTable(5);
        // Add PDF Table Header ->
        Stream.of("#", "Title", "Instructor Name", "Date", "Num of Hours")
                .forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(headerTitle, headFont));
                    table.addCell(header);
                });

        int i = 0;
        for (CourseEntity course : courses) {

            PdfPCell indexCell = new PdfPCell(new Phrase(String.valueOf(++i)));
            indexCell.setPaddingLeft(4);
            indexCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            indexCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(indexCell);

            PdfPCell titleCell = new PdfPCell(new Phrase(course.getTitle()));
            titleCell.setPaddingLeft(4);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(titleCell);

            PdfPCell instructorNameCell = new PdfPCell(new Phrase(course.getInstructor().getFirstName() + " " + course.getInstructor().getLastName()));
            instructorNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            instructorNameCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            instructorNameCell.setPaddingRight(4);
            table.addCell(instructorNameCell);

            PdfPCell dateCell = new PdfPCell(new Phrase(String.valueOf(course.getStartAt())));
            dateCell.setPaddingLeft(4);
            dateCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(dateCell);

            PdfPCell hoursCell = new PdfPCell(new Phrase(String.valueOf(course.getNumberOfHours())));
            hoursCell.setPaddingLeft(4);
            hoursCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hoursCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hoursCell);
        }
        document.add(table);
        document.close();
        return outputStream;
    }
}
