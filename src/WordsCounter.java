import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WordsCounter extends JFrame {

    private static final Color BACKGROUND_COLOR = new Color(253, 245, 251);

    private JTextArea textArea;
    private JLabel counterLabel;
    private int counter=0;
    private JButton saveBtn;

    public WordsCounter(){
        initializeGUI();
    }

    public void initializeGUI(){

        this.setResizable(false);
        this.setSize(500,500);
        this.setTitle("Words Counter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel titlePanel=new JPanel();
        JPanel textFieldPanel=new JPanel();
        JPanel counterPanel=new JPanel();
        JPanel buttonPanel = new JPanel();

        titlePanel.setBackground(BACKGROUND_COLOR);
        textFieldPanel.setBackground(BACKGROUND_COLOR);
        counterPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBackground(BACKGROUND_COLOR);

        saveBtn=new JButton("Save");
        textArea=new JTextArea(5,30);

        Border border = BorderFactory.createLineBorder(Color.BLACK);
        textArea.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        counterLabel=new JLabel();
        JScrollPane jScrollPane = new JScrollPane(
                textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        JLabel title=new JLabel("Words Counter");
        title.setFont(new Font("MV Boli", Font.PLAIN, 32));

        titlePanel.add(title);
        textFieldPanel.add(jScrollPane);
        buttonPanel.add(saveBtn);
        counterPanel.add(counterLabel);

        this.setLayout(new GridLayout(4,1));
        this.add(titlePanel);
        this.add(textFieldPanel);
        this.add(counterPanel);
        this.add(buttonPanel);

        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                updateWordCount();
            }
        });
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        saveBtn.addActionListener(e -> saveToFile());


        this.setVisible(true);

    }

    private void updateWordCount() {
        String text = textArea.getText();
        if (text.isEmpty()) {
            counter = 0;
        } else {
            String[] words = text.split("\\s+");
            counter = words.length;
        }
        counterLabel.setText(counter+" Words");
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            String filePath = file.getPath();
            if (!filePath.toLowerCase().endsWith(".txt")) {
                file = new File(filePath + ".txt");
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(this, "File saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
