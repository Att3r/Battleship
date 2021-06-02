import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Game {
    private final int boardsize; // Mängulaua suurus mängus.
    private int[][] boardmatrix; // Mängulaual asuvad laevad
    private final Random random = new Random(); //Juhuslikkus
    private final int[] ships = {4, 3, 3, 2, 2, 2, 1}; //laeva pikkused ja kokku 7 laeva e. 17 ruutu.
    private int shipsCounter = 0; //Laevu kokku, algselt 0.
    private int clickCounter = 0; // Kliki lugeja laeva otsimisel, algselt 0
    private final List<Point> points = new ArrayList<>(); // laevade paigutamisel vajalik



    public Game(int boardsize) {
        this.boardsize = boardsize;
        this.boardmatrix = new int[boardsize][boardsize];
        // TEST EESMÄRGIL
        //setupNewGame();
        //showGameboardMatrix();

    }

    public void setupNewGame() {
        boardmatrix = new int[boardsize][boardsize]; // Algava mängulaua suurus
        int shipsTotal = ships.length; // Laevu kokku
        int shipsPlaced = 0; //Mitu laeva on paigutatud, algselt 0

        while(shipsPlaced < shipsTotal) {
            int row = random.nextInt(boardsize);
            int col = random.nextInt(boardsize);
            boolean vertical = random.nextBoolean();
            boolean placed = false;; // Kas läks laev paika.

            if(vertical) { //Proovib paigutada vertikaalselt
                placed = checkVerticalPLace(row, col, ships[shipsPlaced]);
            } else { // Proovib paigutada horisontaalselt
                placed = checkHorizontalPLace(row, col, ships[shipsPlaced]);
            }
            // Kas laev paigas
            if(placed) {
                shipsPlaced++; // Võta järgmine laev.
            }
        }
        replaceNineToZero();
    }

    private boolean checkVerticalPLace(int row, int col, int length) {
        points.clear(); // Tühjenda punktid
        boolean placed = false; // See laev pole veel paigas

        if(row + length < boardsize) { // kas laeva saab siia panna
            for(int i = row; i < row + length; i++) {
                if(boardmatrix[i][col] == 0) {
                    points.add(new Point(i, col)); // Laeva üks kast on paigas.
                } else {
                    points.clear();
                    points.add(new Point(-1,-1));
                }
            }
        } else {
            points.add(new Point(-1,-1));
        }

        if(points.get(0).getRow() != -1) { //Me saime laeva paika
            for(int i = row; i < row + length; i++) {
                boardmatrix[i][col] = length;
            }
            placed = true;
            fillVerticalProtection(row, col, length);
        }
        return placed;
    }

    private boolean checkHorizontalPLace(int row, int col, int length) {
        points.clear();
        boolean placed = false;
        if (col + length < boardsize) {
            for(int i = col; i < col + length; i++) {
                if(boardmatrix[row][i] == 0) {
                    points.add(new Point(row, i));
                } else {
                    points.clear();
                    points.add(new Point(-1,-1));
                }
            }
        } else {
            points.add(new Point(-1,-1));
        }

        if(points.get(0).getCol() != -1) {
            for(int i = col; i < col + length; i++) {
                boardmatrix[row][i] = length;
            }
            placed = true;
            fillHorizontalProtection(row, col, length);
        }

        return placed;
    }

    private void fillVerticalProtection(int row, int col, int length) {
        // Vasakule ja paremale kaitse
        for(int i = row; i < row + length; i++) {
            if(col - 1 >= 0) {
                boardmatrix[i][col-1] = 9; // Kaitse
            }
            if(col + 1 < boardsize) {
                boardmatrix[i][col+1] = 9;
            }
        }
        // Ülemine rida
        if(row - 1 >= 0) {
            boardmatrix[row-1][col] = 9; // Laeva nina
            if(col - 1 >= 0) {
                boardmatrix[row-1][col-1] = 9; //Ülemine vasak nurk
            }
            if(col + 1 < boardsize) {
                boardmatrix[row-1][col+1] = 9; // Ülemine parem nurk
            }

        }
        // Alumine rida
        if(row + length < boardsize) { // Laeva alumine nina
            boardmatrix[row+length][col] = 9;
            if(col - 1 >= 0) {
                boardmatrix[row+length][col-1] = 9;// laeva vasak nurk
            }
            if(col + 1 < boardsize) {
                boardmatrix[row + length][col + 1] = 9; // Alumine parem nurk
            }
        }
    }

    private void fillHorizontalProtection(int row, int col, int length) {
        for(int i = col; i < col + length; i++) {
            if(row - 1 >= 0) {
                boardmatrix[row-1][i] = 9;
            }
            if(row + 1 < boardsize) {
                boardmatrix[row+1][i] = 9;
            }

        }  // for lõpp
        // Vasak pooln ots
        if(col - 1 >= 0) {
            boardmatrix[row][col-1] = 9; // Nina
            if(row - 1 >= 0) {
                boardmatrix[row-1][col-1] = 9;
            }
            if(row + 1 < boardsize) {
                boardmatrix[row+1][col-1] = 9;
            }
        } // if 1 lõpp
        // Parem poolne ots
        if(col + length < boardsize) {
            boardmatrix[row][col+length] = 9; //Nina
            if(row - 1 >= 0) {
                boardmatrix[row-1][col+length] = 9;
            }
            if(row + 1 < boardsize) {
                boardmatrix[row+1][col+length] = 9;
            }
        } // if 2 lõpp

    } // fillHorizontal lõpp

    private void replaceNineToZero() {
        for(int row = 0; row < boardsize; row++) {
            for(int col = 0; col < boardsize; col++) {
                if(boardmatrix[row][col] == 9) {
                    boardmatrix[row][col] = 0;
                }
            }
        }
    }

    public void showGameboardMatrix() {
        System.out.println(); // Reavahetus
        for(int row = 0; row < boardsize; row++) {
            for(int col = 0; col < boardsize; col++) {
                System.out.print(boardmatrix[row][col] + " ");
            }
            System.out.println(); //reavahetus peale rea lõppu.
        }
    }

    public int[][] getBoardmatrix() {
        return boardmatrix;
    }

    public int getShipsCounter() {
        return shipsCounter;
    }

    public void setShipsCounter(int shipsCounter) {
        this.shipsCounter += shipsCounter;
    }

    public int getClickCounter() {
        return clickCounter;
    }

    public void setClickCounter(int clickCounter) {
        this.clickCounter += clickCounter;
    }

    public void resetClickCounter () {
        this.clickCounter = 0;
    }

    public int getShipsParts() {
        return IntStream.of(ships).sum();
    }

    public void setUserClick(int row, int col, int what) {
        if(what == 7) {
            boardmatrix[row][col] = 7; // Saadi pihta
        } else {
            boardmatrix[row][col] = 8; // Läks mööda
        }
    }

    public boolean isGameOver() {
        return getShipsParts() == getShipsCounter();
    }

    public void setGameOver() {
        shipsCounter = getShipsParts();
    }
}