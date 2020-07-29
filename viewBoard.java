/**
 * Created by malquib2 on 5/25/19.
 */

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class viewBoard extends board{

    private static String PlayerCommand = " ";
    private static int PlayerCount = 0;
    private static String roomChose = " ";

    public static void showBoard() {

        ImageIcon icon = new ImageIcon("board.jpg");
        JLabel label = new JLabel(icon);

        boardW = icon.getIconWidth();
        boardH = icon.getIconHeight();

        frame.setLayout(new BorderLayout());


        boardPanel.add(label,BorderLayout.CENTER);

        setPlayerPanels();

        setBoardPiecesPanel();

        setGameOptionPanel();



        setPlayerOptionPanel();

        setSystemOutPanel();

        Color clear = new Color(00,00,00,00 );
        boardPanel.setBackground(clear);
        frame.add(boardPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }


    public static void setPlayerPanels(){
        //dice
        playerPanel.setBounds(0,0,boardW+2000,boardH+2000);
        playerPanel.setLayout(null);
        Color clear = new Color(00,00,00,00 );
        playerPanel.setBackground(clear);
        frame.add(playerPanel,BorderLayout.CENTER);
    }

    public static void setBoardPiecesPanel(){
        //cards and scene markes

        boardPieces.setLayout(null);
        Color clear = new Color(00,00,00,00 );
        boardPieces.setBackground(clear);
        boardPieces.setBounds(0,-400,boardW+2000,boardH+3000);
        frame.add(boardPieces,BorderLayout.CENTER);
    }

    public static void setPlayerInfoPanel(){
        //boxes of player info

        infoPanel.setLayout(new GridLayout(PlayerCount , 1));

        //infoPanel.setSize(200,boardH);
        infoPanel.setVisible(true);
        Color clear = new Color(00,00,00,00 );
        infoPanel.setBackground(clear);
        frame.add(infoPanel, BorderLayout.WEST);
    }

    public static void setPlayerOptionPanel() {
        //buttons for player turns
        buttonPanelP.setLayout(new GridLayout(1,5));

        //buttonPanelP.setSize(boardW+400,200);
        buttonPanelP.setBackground(Color.black);

        frame.add(buttonPanelP,BorderLayout.SOUTH);
    }

    public static void setGameOptionPanel(){
        //buttons for player # to choose

        buttonPanelA.setLayout(new GridLayout(7,1));

        //buttonPanelA.setSize(200,boardH);
        buttonPanelA.setBackground(Color.black);

        frame.add(buttonPanelA, BorderLayout.EAST);
    }

    public static void setSystemOutPanel(){
        consolePanel.setLayout(new GridLayout(2 , 1 ));

        consolePanel.add(playerNum);
        consolePanel.add(otherInfo);

        consolePanel.setVisible(true);

        consolePanel.setBackground(Color.WHITE);
        frame.add(consolePanel,BorderLayout.NORTH);
    }


    public static void displayPieces() throws IOException {
        boardPieces.removeAll();
        setUpShotMarkers();
        setUpCards();
    }

    public static void setUpShotMarkers(){

        for(int i = 0 ; i < rooms.length-2; i++){
            int [][] sceneLocations = rooms[i].getSceneLocations();

            for(int x = 0 ; x < rooms[i].getScenes() ; x++){
                ImageIcon shotMarker = new ImageIcon("shot.png");
                JLabel temp = new JLabel(shotMarker);

                int xLoc = sceneLocations[x][0];
                int yLoc = sceneLocations[x][1];
                int hLoc = sceneLocations[x][2];
                int wLoc = sceneLocations[x][3];

                temp.setBounds(hLoc,wLoc,xLoc,yLoc);
                temp.setVisible(true);
                boardPieces.add(temp);
            }
        }

    }

    public static void setUpCards() throws IOException {
        for(int i = 0 ; i < rooms.length-2 ; i++){
            String input;
            if(rooms[i].getFinished()){

            }
            else if(rooms[i].getVisited()){

                input = "card_Img/"+rooms[i].getRoomCard().getImageName();
                ImageIcon cardBack  = new ImageIcon(new ImageIcon(input).getImage().getScaledInstance(205,118 ,Image.SCALE_DEFAULT));
                JLabel temp = new JLabel(cardBack);

                int xLoc = rooms[i].getX();
                int yLoc = rooms[i].getY();
                int hLoc = rooms[i].getH();
                int wLoc = rooms[i].getW();

                temp.setBounds(hLoc,wLoc,xLoc,yLoc);
                temp.setVisible(true);
                boardPieces.add(temp);
            }else{

                input = "CardBack.jpg";
                ImageIcon cardBack  = new ImageIcon(new ImageIcon(input).getImage().getScaledInstance(205,118 ,Image.SCALE_DEFAULT));
                JLabel temp = new JLabel(cardBack);

                int xLoc = rooms[i].getX();
                int yLoc = rooms[i].getY();
                int hLoc = rooms[i].getH();
                int wLoc = rooms[i].getW();

                temp.setBounds(hLoc,wLoc,xLoc,yLoc);
                temp.setVisible(true);
                boardPieces.add(temp);
            }

        }
    }


    public static int getPlayerCount(){
        JButton x2 = new JButton("2");
        x2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCount(2);

            }
        });

        JButton x3 = new JButton("3");
        x3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCount(3);
            }
        });

        JButton x4 = new JButton("4");
        x4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCount(4);
            }
        });

        JButton x5 = new JButton("5");
        x5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCount(5);
            }
        });


        JButton x6 = new JButton("6");
        x6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCount(6);
            }
        });

        JButton x7 = new JButton("7");
        x7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCount(7);
            }
        });

        JButton x8 = new JButton("8");
        x8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCount(8);
            }
        });

        buttonPanelA.add(x2);
        buttonPanelA.add(x3);
        buttonPanelA.add(x4);
        buttonPanelA.add(x5);
        buttonPanelA.add(x6);
        buttonPanelA.add(x7);
        buttonPanelA.add(x8);


        while (PlayerCount == 0){
            frame.pack();
        }
        //System.out.println(PlayerCount);

        buttonPanelA.removeAll();

        return PlayerCount;

    }

    public static void setPlayerCount(int playerCount) {
        PlayerCount = playerCount;
    }


    public static String getPlayerCommand(){

        PlayerCommand = " ";

        buttonPanelP.removeAll();

        JButton move = new JButton("move");
        move.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCommand("move");
            }
        });

        JButton rehearse = new JButton("rehearse");
        rehearse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCommand("rehearse");
            }
        });

        JButton upgrade = new JButton("upgrade");
        upgrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCommand("upgrade");
            }
        });

        JButton work = new JButton("work");
        work.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCommand("work");
            }
        });

        JButton pass =  new JButton("pass");
        pass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setPlayerCommand("pass");
            }
        });

        buttonPanelP.add(move);
        buttonPanelP.add(rehearse);
        buttonPanelP.add(upgrade);
        buttonPanelP.add(work);
        buttonPanelP.add(pass);

        frame.pack();

        while(PlayerCommand.equals(" ")){
            frame.pack();
        }
        //System.out.println(PlayerCommand);
        return PlayerCommand;
    }

    public static void setPlayerCommand(String playerCommand) {
        PlayerCommand = playerCommand;
    }


    public static void initializePlayer(){

        player [] players = game.getOrder();

        playerPanel.removeAll();

        for (int i = 0 ; i < players.length ; i++){

            int R = players[i].getRank();
            String C = players[i].getColor();
            ImageIcon dice = new ImageIcon(new ImageIcon("dice_Img/" + C + R + ".png").getImage().getScaledInstance(30,30 ,Image.SCALE_DEFAULT));
            JLabel diceLabel = new JLabel(dice);

            int StartW = players[i].getXLOC();
            int StartH = players[i].getYLOC();

            diceLabel.setBounds(30,30,StartW,StartH);
            diceLabel.setVisible(true);
            playerPanel.add(diceLabel);
        }
        frame.revalidate();
        frame.repaint();
    }


    public static void showInfo(){

        player[] players = game.getOrder();

        infoPanel.removeAll();

        for(int i = 0 ; i < players.length ; i++){

            JPanel temp = new JPanel();
            temp.setLayout(new GridLayout(4,1));
            JLabel name = new JLabel(players[i].getname());
            JLabel rank = new JLabel("rank: " + String.valueOf(players[i].getRank()));
            JLabel money = new JLabel("money: " + String.valueOf(players[i].getMoney()));
            JLabel credits = new JLabel("credits: " + String.valueOf(players[i].getCredits()));
            JLabel tokens = new JLabel("rehearse tokens: " + String.valueOf(players[i].getRehearseTokens()));
            JLabel color = new JLabel("color: "+players[i].getColor());

            temp.add(name);
            temp.add(rank);
            temp.add(money);
            temp.add(credits);
            temp.add(color);
            temp.add(tokens);
            infoPanel.add(temp);

        }

        frame.repaint();

    }


    public static void printPlayerTurn(String output){
        playerNum.removeAll();
        JLabel temp = new JLabel(output);
        playerNum.add(temp);
        frame.pack();
    }

    public static void information(String output){
        otherInfo.removeAll();
        JLabel temp = new JLabel(output);
        otherInfo.add(temp);
        frame.pack();
    }

    public static String roomChoice(String [] possibleRooms){

        buttonPanelA.removeAll();

        roomChose = " ";

        for(int i = 0 ; i < possibleRooms.length ; i++){
            JButton temp = new JButton(possibleRooms[i]);
            int finalI = i;
            temp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String name = possibleRooms[finalI];
                    setRoomChose(name);
                }
            });
            buttonPanelA.add(temp);
        }

        while (roomChose.equals(" ")){
            frame.pack();
        }
        //System.out.println(roomChose);
        buttonPanelA.removeAll();
        return roomChose;
    }

    public static void setRoomChose(String roomChose) {
        viewBoard.roomChose = roomChose;
    }

}

