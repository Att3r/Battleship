import javax.swing.*;

public class AppMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Model model = new Model(); // Teeb mudeli
                View view = new View(model); // Teeb vaate JFrame
                Controller controller = new Controller(model, view);

                view.registerGameboardMouse(controller);

                view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                view.pack(); // Et objektid JFrame-il "oma koha leiaks"
                view.setLocationRelativeTo(null); // JFrame keset ekraani
                view.setResizable(true); // Akna suurust ei saa muuta.
                view.setVisible(true); // Näita JFrame
            }
        });
    }
}
