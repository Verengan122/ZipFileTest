package com.Verengan12;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;


public class ZipFileTest {
    @Test
    void zipFile() throws Exception {
        ZipFile archive = new ZipFile("src/test/resources/FileForHomeWork.zip");

        ZipEntry filePDF = archive.getEntry("FilePdf.pdf");
        try (InputStream streamPdf = archive.getInputStream(filePDF)) {
            PDF parsedPdf = new PDF(streamPdf);
            assertThat(parsedPdf.text).contains("www.samsung.com");
        }

        ZipEntry fileCSV = archive.getEntry("CsvFile.csv");
        try (InputStream streamCsv = archive.getInputStream(fileCSV)) {
            CSVReader reader = new CSVReader(new InputStreamReader(streamCsv));
            List<String[]> words = reader.readAll();
            assertThat(words)
                    .hasSize(3)
                    .contains(
                            new String[]{"Pushkin", " Evgeny Onegin"},
                            new String[]{"Gogol", " Viy"},
                            new String[]{"Clarc", " Noch Trifidov"}
                    );
        }

        ZipEntry fileXls = archive.getEntry("ExelFile.xlsx");
        try (InputStream streamXls = archive.getInputStream(fileXls)) {
            XLS parsedXls = new XLS(streamXls);
            assertThat(parsedXls.excel
                    .getSheetAt(0)
                    .getRow(0)
                    .getCell(0)
                    .getStringCellValue())
                    .isEqualTo("Тут могла быть ваша реклама)");
        }
    }
}


