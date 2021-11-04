import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileOutputStream;
import java.io.IOException;

public class MultipleIntegerTreePrinter {
    public static final String HEADER_FORMAT =
            "%s Trees \n \n" +
            "%s Items, Range: [%s,%s] \n";

    int minN;
    int maxN;
    int amountPerTree;
    int amountTrees;

    public MultipleIntegerTreePrinter(int minN, int maxN, int amountPerTree, int amountTrees) {
        this.minN = minN;
        this.maxN = maxN;
        this.amountPerTree = amountPerTree;
        this.amountTrees = amountTrees;
    }

    public void createPdf(String dest) throws IOException, DocumentException {
        Document document = new Document(PageSize.LETTER.rotate());
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));

        document.open();

        float width = document.right()-document.left();

        document.add(getHeader(width));
        document.add(new Chunk(new LineSeparator()));
        document.add(Chunk.NEWLINE);

        for (int i = 0; i < amountTrees; i++) {
            IntegerRBTreeMaker maker = new IntegerRBTreeMaker(minN, maxN, amountPerTree);
            maker.tree.stringColor = false;
            TreePrinter<Integer> treePrinter = new TreePrinter<>(maker.tree, maker.order);

            document.add(treePrinter.getTotalTree(width));
            if (i != amountTrees-1) {
                document.add(new Chunk(new LineSeparator()));
                document.add(Chunk.NEWLINE);
            }
        }

        document.close();
        System.out.println("Done");
    }

    public Element getHeader(float width) throws DocumentException, IOException {
        String text = String.format(HEADER_FORMAT, amountTrees, amountPerTree, minN, maxN);
        return TreePrinter.getParagraphScaled(width, text);
    }

    public static void main(String[] args) throws DocumentException, IOException {
        MultipleIntegerTreePrinter printer = new MultipleIntegerTreePrinter(0, 128, 8, 16);
        printer.createPdf("Test2.pdf");
    }
}
