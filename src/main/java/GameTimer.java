import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameTimer {

    private int minutes;
    private int seconds;
    private boolean running;
    private final Timer timer;

    public GameTimer(View view) {
        this.minutes = 0;
        this.seconds = 0;
        this.running = false;

        timer = new Timer(1000, (ActionEvent evt) -> {
           seconds++;
           if(seconds > 59) {
               seconds = 0;
               minutes++;
           }
           view.getLblTime().setText(formatGameTime());
        });
    }

    public String formatGameTime() {
        return String.format("%02d:%02d", minutes, seconds);
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void startTimer() {
        this.timer.start();
    }

    public void stopTimer() {
        this.timer.stop();
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getPlayedTimeInSeconds() {
        return (this.minutes * 60) + seconds;
    }
}
