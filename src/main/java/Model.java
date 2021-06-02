import java.awt.*;
import java.util.ArrayList;

public class Model {

    private int boardsize = 10; // Vaikimisi laua suurus 10x10
    private ArrayList<Griddata> griddata;
    private Game game;

    /**
     * Konstruktor
     */
    public Model() {
        this.griddata = new ArrayList<>();
        this.game = new Game(boardsize);
    }

    public void setupNewgame() {
        game = new Game(boardsize);
    }

    public int getBoardsize() {
        return boardsize;
    }

    public void setBoardsize(int boardsize) {
        this.boardsize = boardsize;
    }

    public ArrayList<Griddata> getGriddata() {
        return griddata;
    }

    public Game getGame() {
        return game;
    }

    public void setGriddata(ArrayList<Griddata> griddata) {
        this.griddata = griddata;
    }

    public int checkGridIndex(int mouseX, int mouseY) {
        int result = -1;
        int index = 0;
        for(Griddata gd : griddata) {
            if(mouseX > gd.getX() && mouseX <= (gd.getX() + gd.getWidth())
                    && mouseY > gd.getY() && mouseY <= (gd.getY() + gd.getHeight())) {
                result = index;
                // for -loop katkestus
            }
            index++;
        }
        return result;
    }

    public int getRowById(int id) {
        if(id != -1) {
            return griddata.get(id).getRow();
        }
        return -1;
    }

    public int getColById(int id) {
        if(id != -1) {
            return griddata.get(id).getCol();
        }
        return -1;
    }

    public void drawUserBoard(Graphics g) {
        ArrayList<Griddata> gd = getGriddata();
        int[][] matrix = game.getBoardmatrix();
        int id = 0;
        for(int r = 0; r < game.getBoardmatrix().length; r++) {
            for(int c =0; c < game.getBoardmatrix()[0].length; c++) {
                if(matrix[r][c] == 0) { // VESI
                    g.setColor(new Color(0,190,255));
                    if(gd.get(id).getRow() == r && gd.get(id).getCol() == c) {
                        g.fillRect(gd.get(id).getX(), gd.get(id).getY(), gd.get(id).getWidth(), gd.get(id).getHeight());
                    }
                } else if(matrix[r][c] == 7) { // PIHTAS
                    g.setColor(Color.GREEN);
                    if(gd.get(id).getRow() == r && gd.get(id).getCol() == c) {
                        g.fillRect(gd.get(id).getX()+3, gd.get(id).getY()+3, gd.get(id).getWidth()-6, gd.get(id).getHeight()-6);
                    }
                } else if(matrix[r][c] == 8) { // MÖÖDAS
                    g.setColor(Color.RED);
                    if(gd.get(id).getRow() == r && gd.get(id).getCol() == c) {
                        g.fillRect(gd.get(id).getX()+3, gd.get(id).getY()+3, gd.get(id).getWidth()-6, gd.get(id).getHeight()-6);
                    }
                } else if(matrix[r][c] > 0 && matrix[r][c] < 5) { // Peidab laeva asukohad.
                    g.setColor(new Color(0,191,255));
                    if(gd.get(id).getRow() == r && gd.get(id).getCol() == c) {
                        g.fillRect(gd.get(id).getX(), gd.get(id).getY(), gd.get(id).getWidth(), gd.get(id).getHeight());
                    }
                }
                id++;
            }
        }
    }
}
