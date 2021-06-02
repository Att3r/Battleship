import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class View extends JFrame {

    private final Model model;
    private final Gameboard gameboard;
    private final Infoboard infoboard;
    private final String filename = "scores.txt"; // Edetabel failis

    public View(Model model) {
        super("Laevade pommitamine");
        this.model = model;

        this.gameboard = new Gameboard(model);
        this.infoboard = new Infoboard();

        JPanel container = new JPanel(new BorderLayout());

        container.add(gameboard, BorderLayout.CENTER); // Paneelile lisame gameboard vasakul
        container.add(infoboard, BorderLayout.EAST); // Paneelile lisame infoboard paremal

        add(container); //Lisa container JFrame peale
    }

    // Comboboxi funk. lisamine
    public void registerCombobox(ItemListener il) {
        infoboard.getCmbSize().addItemListener(il);
    }

    // Hiire funktsionaalsus.
    public void registerGameboardMouse(Controller controller) {
        gameboard.addMouseListener(controller);
        gameboard.addMouseMotionListener(controller);
    }

    // uue mängu funkt.
    public void registerNewgame(ActionListener al) {
        infoboard.getBtnNewGame().addActionListener(al);
    }

    // Edetabeli nupp
    public void registerScoreButton(ActionListener al) {
        infoboard.getBtnScoreboard().addActionListener(al);
    }

    // Raadionupp
    public void registerRadiobuttons(ActionListener al) {
        infoboard.getRdoTable().addActionListener(al);
        infoboard.getRdoTableModel().addActionListener(al);
        infoboard.getRdoDatabase().addActionListener(al);
    }

    public JLabel getLblMouseXY() {
        return infoboard.getLblMouseXY();
    }

    public JLabel getLblID() {
        return infoboard.getLblID();
    }

    public JLabel getLblRowCol() {
        return infoboard.getLblRowCol();
    }

    public JLabel getLblTime() {
        return infoboard.getLblTime();
    }

    public JLabel getLblShip() {
        return infoboard.getLblShip();
    }

    public JLabel getLblGameboard() {
        return infoboard.getLblGameboard();
    }

    public JComboBox<String> getCmbSize() {
        return infoboard.getCmbSize();
    }

    public JButton getBtnNewGame() {
        return infoboard.getBtnNewGame();
    }

    public JButton getBtnScoreboard() {
        return infoboard.getBtnScoreboard();
    }

    public JRadioButton getRdoTable() {
        return infoboard.getRdoTable();
    }

    public JRadioButton getRdoTableModel() {
        return infoboard.getRdoTableModel();
    }

    public JRadioButton getRdoDatabase() {
        return infoboard.getRdoDatabase();
    }

    public void writeToFile(String name, int boardsize, int clicks, int gtime) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            String line = name + ";" + gtime + ";" + boardsize + ";" + clicks + ";" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            bw.write(line);
            bw.newLine(); // Reavahetus
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Edetabeli faili ei leitud " + filename);
        }
    }

    public ArrayList<Scoredata> readFromFile() throws IOException {
        ArrayList<Scoredata> scoredatas = new ArrayList<>();
        File f = new File(filename);
        if(f.exists()) {
            try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
                for(String line; (line = br.readLine()) != null;) {
                    String[] parts = line.split(";");
                    if(Integer.parseInt(parts[2]) == model.getBoardsize()) {
                        String name = parts[0];
                        int gametime = Integer.parseInt(parts[1]); // Mängu aeg sekundites
                        int board = Integer.parseInt(parts[2]); // Laua suurus
                        int click = Integer.parseInt(parts[3]); // Klikke
                        LocalDateTime played = LocalDateTime.parse(parts[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        scoredatas.add(new Scoredata(name, gametime, board, click, played));
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else { // Faili pole, teeb uue faili
            File file = new File(filename);
            file.createNewFile();
        }
        return scoredatas;
    }
}
