package br.com.codethebasics.milionariotipsextractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SpringBootApplication
public class MilionariotipsExtractorApplication {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Cookie");
        JLabel label = new JLabel("Insira o cookie de autenticação");
        JButton button = new JButton("OK");
        JTextField jTextField = new JTextField(null, 30);

        MilionariotipsExtractorApplication m = new MilionariotipsExtractorApplication();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String outputDir = null;
                String cookie = jTextField.getText();
                jTextField.setEnabled(false);

                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Selecione o diretório do arquivo a ser salvo");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    outputDir = chooser.getSelectedFile().getAbsolutePath();
                }
                else {
                    System.exit(0);
                }
                button.setText("Extraindo...");
                button.setEnabled(false);
                Extractor extractor = new Extractor(outputDir, cookie);
                extractor.start();
//                SpringApplication.run(MilionariotipsExtractorApplication.class, args);
            }
        });

        JPanel panel = new JPanel();

        panel.add(label);
        panel.add(jTextField);
        panel.add(button);

        frame.add(panel);
        frame.setSize(500, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.show();

    }

}
