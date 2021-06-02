import java.time.LocalDateTime;

public class Scoredata {

    private final String name;
    private final int time; // Mänguaeg sek.
    private final int boardsize;
    private final int clickcount;
    private final LocalDateTime playedtime;

    public Scoredata(String name, int time, int boardsize, int clickcount, LocalDateTime playedtime) {
        this.name = name;
        this.time = time;
        this.boardsize = boardsize;
        this.clickcount = clickcount;
        this.playedtime = playedtime;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public int getBoardsize() {
        return boardsize;
    }

    public int getClickcount() {
        return clickcount;
    }

    public LocalDateTime getPlayedtime() {
        return playedtime;
    }

    public String convertSecToMMSS(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }
}
