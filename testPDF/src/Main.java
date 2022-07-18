import java.io.File;
import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public String readPDFFile() throws Exception {
        File file = new File("G:\\professional\\NIMI\\Project Resume Graph\\malith gammanpila.pdf");
        FileInputStream fis = new FileInputStream(file);

        PDDocument pdfDocument = PDDocument.load(fis);
        System.out.println(pdfDocument.getPages().getCount());

        pdfDocument.close();
        fis.close();
        return "hello";
}