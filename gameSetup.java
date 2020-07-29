import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

public class gameSetup {

    public  static int numPlayers;
    public  static int numDays;
    private static player [] players;
    private static String [] colors = {"blue","grey","green","orange","pink","red","violet","yellow"};


    public static void gameSetting(int playerCount){

        int tempCreds = 0;
        int tempRank = 1;

        numPlayers = playerCount;
        numDays = 4;

        if(playerCount == 2||playerCount == 3){
            numDays = 3;
        }else if(playerCount == 5){
            tempCreds=2;
        }else if(playerCount == 6){
            tempCreds=4;
        }else if(playerCount == 7||playerCount == 8){
            tempRank=2;
        }

        player[] temp = new player [playerCount];

        for(int i=0;i<playerCount;i++){

            int StartW = 2600 + (( i % 3) * 100);
            int StartH = 600 ;
            if (i<=2){

            }else if (i>2 && i <= 5){
                StartH = StartH+100;
            }else {
                StartH = StartH+200;
            }

            temp[i] = new player("player" + (i+1),tempCreds,0,
                    tempRank,10,i,i,colors[i],StartW,StartH,StartW,StartH);
            players = temp;
        }
    }

    public static int getNumDays() {
        return numDays;
    }

    public static player[] getPlayers() {
        return players;
    }

    public static void buildCards() {
        board.setCards(CardXMLparser.cardReader());
    }

    public static void buildBoard() throws IOException, SAXException, ParserConfigurationException {

        try {
            board.setRoom(BoardXMLParser.getBoardData());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

}
