package com.kapitsa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class GameOverGui extends JDialog {
    private static final long serialVersionUID = 1L;

    private BattleShip gui;
    private String appPath;
    private JLabel congratLbl;
    private JLabel congratMsgLbl;
    private JLabel picLbl;

    public GameOverGui(String appPath, BattleShip myGui) {
        this.gui = myGui;
        this.appPath = appPath;
        initializeComponents();
    }

    private void initializeComponents() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.appPath + "icon.png"));
        setTitle("Game Over");
        setAlwaysOnTop(true);
        setResizable(false);
        setSize(600, 600);
        getContentPane().setLayout(null);

        this.congratLbl = new JLabel("Congratulations!");
        this.congratLbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.congratLbl.setFont(new Font("Arial", Font.BOLD, 28));
        this.congratLbl.setBounds(10, 11, 574, 33);
        getContentPane().add(this.congratLbl);

        this.congratMsgLbl = new JLabel("You won, but your enemy may seek revenge!");
        this.congratMsgLbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.congratMsgLbl.setFont(new Font("Arial", Font.PLAIN, 22));
        this.congratMsgLbl.setBounds(20, 55, 574, 33);
        getContentPane().add(this.congratMsgLbl);

        this.picLbl = new JLabel("");
        this.picLbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.picLbl.setIcon(new ImageIcon(this.appPath + "win.jpg"));
        this.picLbl.setBounds(10, 99, 574, 300);
        getContentPane().add(this.picLbl);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 410, 574, 2);
        getContentPane().add(separator);

        JLabel logoLbl = new JLabel("");
        logoLbl.setIcon(new ImageIcon(this.appPath + "logo.png"));
        logoLbl.setBounds(10, 423, 317, 61);
        getContentPane().add(logoLbl);

        JSeparator separator1 = new JSeparator();
        separator1.setBounds(10, 495, 574, 2);
        getContentPane().add(separator1);

        JButton btnNewGame = new JButton("New Game");
        btnNewGame.setFont(new Font("Arial", Font.PLAIN, 18));
        btnNewGame.setBounds(10, 508, 264, 53);
        btnNewGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                gui.enableShipAllocation(true);
                gui.restartAllocations();
                gui.getDiceHandler().clearResults();

                if (gui.IsServer()) {
                    gui.writeOutputMessage(" - Reallocate your ships and wait for your enemy!");
                } else {
                    gui.getMyClient().SendMessage("B");
                    gui.getMyClient().StopClient();
                    gui.btnConnect.setEnabled(true);
                    gui.btnStopServer.setEnabled(false);
                    gui.writeOutputMessage(" - You have to reallocate your ships and connect to the server again!");
                }

                dispose();
            }
        });
        getContentPane().add(btnNewGame);

        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Arial", Font.PLAIN, 18));
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                gui.enableShipAllocation(true);
                gui.restartAllocations();
                gui.getDiceHandler().clearResults();

                if (gui.IsServer()) {
                    if (gui.getMyServer().hasClient()) {
                        gui.getMyServer().SendMessage("B");
                        gui.getMyServer().StopCommunication();
                        gui.getMyServer().StopServer();
                    } else {
                        gui.getMyServer().StopServer();
                    }
                    gui.getMyServer().stop();
                    gui.btnConnect.setEnabled(true);
                    gui.btnStopServer.setEnabled(false);
                    gui.writeOutputMessage(" - Thank you for playing with BattleShip v2.0!");
                    gui.writeOutputMessage(" - If you decide to play again, you have to reallocate your ships and start a new connection!");
                } else {
                    gui.getMyClient().SendMessage("B");
                    gui.getMyClient().StopClient();
                    gui.btnConnect.setEnabled(true);
                    gui.btnStopServer.setEnabled(false);
                    gui.writeOutputMessage(" - Thank you for playing with BattleShip v2.0!");
                    gui.writeOutputMessage(" - If you decide to play again, you have to reallocate your ships and connect to the server!");
                }
                dispose();
            }
        });
        btnClose.setBounds(320, 508, 264, 53);
        getContentPane().add(btnClose);

        JLabel lblIfYouWant = new JLabel("If you want to know more about us, visit our page:");
        lblIfYouWant.setBounds(337, 434, 247, 14);
        getContentPane().add(lblIfYouWant);

        JButton urlBtn = new JButton();
        urlBtn.setText("<HTML><FONT color=\"#000099\"><U>http://www.rochacardoso.de</U></FONT></HTML>");

        urlBtn.setHorizontalAlignment(SwingConstants.LEFT);
        urlBtn.setBorderPainted(false);
        urlBtn.setOpaque(false);
        urlBtn.setBackground(Color.LIGHT_GRAY);
        urlBtn.setToolTipText("http://www.rochacardoso.de");
        urlBtn.setHorizontalAlignment(SwingConstants.CENTER);
        urlBtn.setBounds(337, 459, 247, 25);
        urlBtn.addActionListener(new ActionListener() {
                                     public void actionPerformed(ActionEvent arg0) {
                                         if (Desktop.isDesktopSupported()) {
                                             try {
                                                 URI uri = null;
                                                 try {
                                                     uri = new URI("http://www.rochacardoso.de");
                                                 } catch (URISyntaxException e) {
                                                     // TODO Auto-generated catch block
                                                     e.printStackTrace();
                                                 }
                                                 if (uri != null)
                                                     Desktop.getDesktop().browse(uri);
                                             } catch (IOException e) { /* TODO: error handling */ }
                                         }
                                     }
                                 }
        );
        getContentPane().add(urlBtn);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                gui.enableShipAllocation(true);
                gui.restartAllocations();
                gui.getDiceHandler().clearResults();

                if (gui.IsServer()) {
                    if (gui.getMyServer().hasClient()) {
                        gui.getMyServer().SendMessage("B");
                        gui.getMyServer().StopCommunication();
                        gui.getMyServer().StopServer();
                    } else {
                        gui.getMyServer().StopServer();
                    }
                    gui.getMyServer().stop();
                    gui.btnConnect.setEnabled(true);
                    gui.btnStopServer.setEnabled(false);
                    gui.writeOutputMessage(" - Thank you for playing with BattleShip v2.0!");
                    gui.writeOutputMessage(" - If you decide to play again, you have to reallocate your ships and start a new connection!");
                } else {
                    gui.getMyClient().SendMessage("B");
                    gui.getMyClient().StopClient();
                    gui.btnConnect.setEnabled(true);
                    gui.btnStopServer.setEnabled(false);
                    gui.writeOutputMessage(" - Thank you for playing with BattleShip v2.0!");
                    gui.writeOutputMessage(" - If you decide to play again, you have to reallocate your ships and connect to the server!");
                }

            }
        });
    }

    public void ShowGameOver(int flag) {
        switch (flag) {
            case 0: //lose
            {
                this.congratLbl.setText("You lost!");
                this.congratMsgLbl.setText("You lost the battle, but maybe not the war! Try again!");
                this.picLbl.setIcon(new ImageIcon(this.appPath + "lose.jpg"));
                break;
            }
            case 1: //win
            {
                this.congratLbl.setText("Congratulations!");
                this.congratMsgLbl.setText("You won, but your enemy may seek revenge!");
                this.picLbl.setIcon(new ImageIcon(this.appPath + "win.jpg"));
                break;
            }
        }
        setLocationRelativeTo(this.gui);
        show();
    }
}
