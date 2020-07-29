import java.io.IOException;
import java.util.Random;
import javax.swing.*;

public class board{

    static JFrame frame = new JFrame("deadWood");
    static JPanel boardPanel = new JPanel();
    static JPanel playerPanel = new JPanel();
    static JPanel boardPieces = new JPanel();
    static JPanel infoPanel = new JPanel();
    static JPanel buttonPanelA = new JPanel();
    static JPanel buttonPanelP = new JPanel();
    static JPanel consolePanel = new JPanel();
    static JPanel playerNum = new JPanel();
    static JPanel otherInfo = new JPanel();
    static int boardW;
    static int boardH;

    public static card[] cards = new card[40];
    public static room[] rooms = new room[12];

    public static void setCards(card[] cards) {
        board.cards = cards;
    }

    public static room[] getRooms() {
        return rooms;
    }

    public static void setRoom(room[] room) {
        board.rooms = room;
    }


    public static boolean checkDayCompletion() {

        boolean temp = true;
        int tempary = 0;

        for (int i = 0; i < 10; i++) {

            tempary = tempary + rooms[i].isMovieComplete();

        }
        if (tempary >= 9) {
            temp = false;
        }

        return temp;
    }


    public static void layCardsOut() {
        int [] usedCards = new int[10];
        for (int i = 0; i < 10; i++) {
            boolean repeat = true;
            int cardNUM = 41;
            while(repeat){
                Random ran = new Random();
                cardNUM = ran.nextInt(40);
                boolean wasRepeat = true;
                for(int x= 0 ; x < i ; x++){
                    if((cardNUM == usedCards[x]) && wasRepeat){
                        wasRepeat = false;
                    }
                }
                if(wasRepeat){
                    repeat = false;
                }
            }
            rooms[i].setRoomCard(cards[cardNUM]);
            usedCards[i] = cardNUM;
        }
    }

    public static String[] findPossibleRooms(int currentRoom){
        int possibleNeigbors = rooms[currentRoom].getNeigbors().length;
        String [] sendBack = new String[possibleNeigbors+1];

        for (int i = 0; i < possibleNeigbors; i++) {

            String tempRoomNames = rooms[currentRoom].getNeigbors()[i];

            tempRoomNames = tempRoomNames.toLowerCase();

            sendBack[i] = tempRoomNames;
        }

        sendBack[possibleNeigbors] = "cancel";

        return sendBack;
    }

    public static int convertNameToInt(String roomName){
        for (int y = 0; y < 12; y++) {
            if (roomName.equals(rooms[y].getName().toLowerCase())) {
                return y;
            }
        }
        return -1;
    }

    public static void showBoard(){
        viewBoard.showBoard();
    }



    public static int getPlayerCount(){
        return viewBoard.getPlayerCount();
    }


    public static void initializePlayer() {
        viewBoard.initializePlayer();
    }

    public static String getPlayerCommand(){
        return viewBoard.getPlayerCommand();
    }

    public static void showInfo(){
        viewBoard.showInfo();
    }

    public static void setPlayersInfoPanels() {
        viewBoard.setPlayerInfoPanel();
    }

    public static void printPlayerTurn(String output){
        viewBoard.printPlayerTurn(output);
    }

    public static void information(String output){
        viewBoard.information(output);
    }

    public static String roomChoice(String [] possibleRooms){
        return viewBoard.roomChoice(possibleRooms);
    }

    public static void displayPieces() throws IOException {
        viewBoard.displayPieces();
    }


}




