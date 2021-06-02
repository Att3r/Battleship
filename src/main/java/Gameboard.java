import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Gameboard  extends JPanel {

    private final Model model;
    private final int startX = 5; // Algkordinaat kuhu tuleb mänguväli
    private final int startY = 5; // Algkordinaat kuhu tuleb mänguväli
    private final int width = 30; // ruudu suurus
    private final int height = 30; // ruudu suurus
    private final String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};

    public Gameboard(Model model) {
        this.model = model;
        setBackground(new Color(135,205,250));
    }

    @Override
    public Dimension getPreferredSize() {
        int w = (width * model.getBoardsize()) + width + (2 * startX);
        int h = (height * model.getBoardsize()) + height + (2 * startY);
        return new Dimension(w, h);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        // Kirjastiil mängulaual
        g.setFont(new Font("Verdana", Font.BOLD, 14));

        // Joonista tähestiku ruudustik
        drawColAlphabet(g);
        // Joonistab rea numbrid ülevalt alla.
        drawRowNumbers(g);
        drawGameGrid(g); // Joonistab ülejäänud laua osa
        // Kui on mäng, siis näita laevu ka
        if(!model.getGame().isGameOver()) {
            model.drawUserBoard(g);
        }
        drawGameGrid(g);
    }

    private void drawColAlphabet(Graphics g) {
        int i = 0; //Abimuutuja tähestiku massiivist tähe saamiseks.
        g.setColor(Color.BLUE);
        for(int x = startX; x <= (width * model.getBoardsize()) + width; x+=width) {
            g.drawRect(x, startY, width, height);
            if(x > startX) { // Esimene lahter jääb tühjaks
                g.drawString(alphabet[i], x + (width / 2) - 5, 2 * (startY + startY) + 5);
                i++;
            }
        }
    }

    private void drawRowNumbers(Graphics g) {
        int i = 1; // Number mis joonistatakse lauale.
        g.setColor(Color.RED);
        for(int y = startY+height; y < (height * model.getBoardsize()) + height; y += height) {
            g.drawRect(startX, y, width, height);
            if(i < 10) {
                // 1 - 9 numbrid
                g.drawString(String.valueOf(i), startX + (width / 2) - 5, y + 2 * (startY + startY));
            } else {
                g.drawString(String.valueOf(i), startX + (width / 2) - 10, y + 2 * (startY + startY));
            }
            i++; // Järmgine number
        }
    }

    private void drawGameGrid(Graphics g) {
        ArrayList<Griddata> matrix = new ArrayList<>();
        g.setColor(Color.BLACK); // Joonte värv laual
        int x = startX + width;
        int y = startY + height;
        int i = 1;
        for(int r = 0; r < model.getBoardsize(); r++) {
            for(int c = 0; c < model.getBoardsize(); c++) {
                g.drawRect(x, y, width, height); // Joonistab jooned lauale
                matrix.add(new Griddata(r, c, x, y, width, height));
                x += width; // Laiuse võrra muutuja x kasvab, vasakult paremale
            }
            y = (startY + height) + (height * i); // Ülevalt alla kasvavalt
            i += 1;
            x = startX + width;
        }
        model.setGriddata(matrix);
    }
}
