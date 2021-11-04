import Trees.BinaryTree;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TreePrinter<E> {
    public static final BaseColor TREE_BACKGROUND = BaseColor.LIGHT_GRAY;
    public static final String TABLE_HEADER_TEXT = "Table Order: %s";
    BinaryTree<E> tree;
    ArrayList<E> order;

    public TreePrinter(BinaryTree<E> tree, ArrayList<E> order) {
        this.tree = tree;
        this.order = order;
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));

        document.open();

        float width = document.right()-document.left();

        document.add(getTotalTree(width));
        document.close();
        System.out.println("Done");
    }

    public Paragraph getTotalTree(float width) throws DocumentException, IOException {
        Paragraph paragraph = new Paragraph();

        paragraph.add(getTableAdd(width));
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(getTree(width));

        return paragraph;
    }

    public static Paragraph getParagraphScaled(float width, String text) throws DocumentException, IOException {
        BaseFont bf = bf = BaseFont.createFont("c:/windows/fonts/cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        float fontSize = -1;

        Scanner scanner = new Scanner(text);
        while (scanner.hasNextLine()) {
            float glyphWidth = bf.getWidth(scanner.nextLine());
            float thisFontSize = 1000 * (width) / glyphWidth;

            if (fontSize == -1)
                fontSize = thisFontSize;
            else
                fontSize = Math.min(fontSize, thisFontSize);
        }

        Font font = new Font(bf, fontSize);

        Phrase phrase = new Phrase(text, font);

        Paragraph paragraph = new Paragraph();
        paragraph.add(phrase);

        return paragraph;
    }

    public Element getTableAdd(float width) throws DocumentException, IOException {
        String text = String.format(TABLE_HEADER_TEXT, order.toString());

        return getParagraphScaled(width-1, text);
    }

    public Element getTree(float width) throws DocumentException, IOException {
        String text = tree.toString();

        Paragraph paragraph = getParagraphScaled(width, text);

        PdfPCell cell = new PdfPCell(paragraph);
        cell.setBackgroundColor(TREE_BACKGROUND);

        PdfPTable table = new PdfPTable(1);

        table.setWidthPercentage(100);
        table.addCell(cell);

        return table;
    }

    public static void main(String[] args) throws IOException, DocumentException {
        IntegerRBTreeMaker maker = new IntegerRBTreeMaker(0, 100, 10);
        maker.tree.stringColor = false;
        //System.out.println(tree);
        TreePrinter<Integer> printer = new TreePrinter<>(maker.tree, maker.order);
        printer.createPdf("Test.pdf");
    }
}
