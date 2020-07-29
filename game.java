import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class game {

    public static int currentPlayer = 0;
    public static player [] order;

    public static void run() throws ParserConfigurationException, SAXException, IOException {
        int play = 1;
        board.showBoard();

        board.printPlayerTurn("how many people are going to play");
        int temp2 = board.getPlayerCount();
        //int temp = gameSetup.getNumPlayers();
        gameSetup.gameSetting(temp2);

        while (play == 1) {


            gameSetup.buildCards();
            gameSetup.buildBoard();

            order = gameSetup.getPlayers();
            int days = gameSetup.getNumDays();

            for (int d = 0 ; d < days ; d++){
                board.layCardsOut();
                board.initializePlayer();
                board.setPlayersInfoPanels();
                board.showInfo();
                board.displayPieces();


                for(int p = 0 ; p < order.length ; p++){
                    order[p].startOfRound();
                }

                boolean contDay = true;

                while (contDay){

                    board.printPlayerTurn("it is "+ order[currentPlayer].getname()+"'s turn and it is day " + (d+1));
                    order[currentPlayer].startOfTurn();
                    order[currentPlayer].turn();

                    if( order[currentPlayer].getCardCompleteThisTurn()==1){
                        payPlayers();
                        MoveOffCard();
                        board.initializePlayer();

                    }

                    if(currentPlayer == order.length-1){
                        currentPlayer = 0;

                    }else if(currentPlayer != order.length){
                        currentPlayer ++;

                    }

                    contDay = board.checkDayCompletion();
                }
                resetCards();
                resetPlayers();
            }
            int leader = 0;
            int leaderScore = 0;
            for(int z = 0 ; z < order.length ; z++){
                int finalmoney = order[z].getMoney()   ;
                int finalCredits = order[z].getCredits();
                int finalRank = (5 * order[z].getRank());
                int totalFinalScore = finalmoney + finalCredits + finalRank;
                if (totalFinalScore >= leaderScore) {
                    leaderScore = totalFinalScore;
                    leader = z;
                }
            }
            board.printPlayerTurn(order[leader].getname()+" won with a total final score of " + leaderScore);
            board.information("do you want to play again");
            String [] yesOrNo = new String []{"yes","no"};
            String check =  board.roomChoice(yesOrNo);

            if(check.equals("yes")){

            }else if(check.equals("no")){
                play--;

            }
        }
        board.information("thanks for playing");
    }

    private static void resetPlayers() {
        for(int i = 0 ; i < order.length ; i++){
            order[i].setXLOC(order[i].getStartX());
            order[i].setYLOC(order[i].getStartY());
        }
    }

    private static void resetCards() {

        int R = board.rooms.length;
        for (int i = 0 ; i < R-2;i++){
            board.rooms[i].setVisited(false);
            board.rooms[i].setFinished(false);
            board.rooms[i].setScenes(board.rooms[i].sceneLocations.length);
        }

    }

    private static void MoveOffCard() {
        int room = order[currentPlayer].getCurrentRoom();

        int newX = board.getRooms()[room].getX();
        int newY = board.getRooms()[room].getY() - 300;

        for(int i = 0 ; i< order.length;i++){
            if(order[i].getCurrentRoom() == room){
                order[i].setXLOC(newX);
                order[i].setYLOC(newY);
                newX = newX + 50;
                order[i].setOnACard(false);
                order[i].setOnASlot(false);
            }
        }

    }

    private static void payPlayers() {

        int playersToPay = 0;

        int roomCheck = order[currentPlayer].getCurrentRoom();
        card finishedCard = board.getRooms()[roomCheck].getRoomCard();
        boolean [] onCompletedCard = new boolean[order.length];

        Integer [] payoutValues= new Integer[finishedCard.getPayout()];

        //gets an array of all the players that are on that card
        for(int i = 0 ; i < order.length ; i++){
            if(order[i].getCurrentRoom() == roomCheck){
                if(order[i].getOnACard()){
                    onCompletedCard[i]=true;
                }else {
                    onCompletedCard[i] = false;
                }
            }else {
                onCompletedCard[i] = false;
            }
        }


        //finds out how many people are on the completed card
        for(int x = 0 ;x < onCompletedCard.length;x++){
            if(onCompletedCard[x]){
                playersToPay++;
            }
        }

        //gets the players that are on that card in a separate array
        player [] players = new player[playersToPay];
        int temp = 0;

        for (int a =0 ; a < order.length; a++){
            if(onCompletedCard[a]){
                players[temp] = order[a];
                temp++;
            }
        }


        //sorts the players into correct order
        if (players.length > 1){
            players = sortPlayers(players);
        }


        //builds the array for the payout of the players
        for (int y = 0 ; y < payoutValues.length;y++){
            Random ran = new Random();
            payoutValues[y] =(ran.nextInt(6)+1);
        }
        Arrays.sort(payoutValues, Collections.reverseOrder());

        //pays the players
        if(!(players.length == 0)){
            for (int b = 0 ; b < payoutValues.length; b++){
                players[ b% players.length].setMoney(payoutValues[b]);
            }
        }


        for(int w = 0 ; w < board.rooms[roomCheck].getSlots().length ; w++ ){
            board.rooms[roomCheck].getSlots()[w].setIfEmpty(true);
        }


        for(int q = 0 ; q < board.rooms[roomCheck].getRoomCard().getSlots().length ; q++){
            board.rooms[roomCheck].getRoomCard().getSlots()[q].setIfEmpty(true);
        }

    }

    private static player[] sortPlayers(player[] players) {
        int i = 1;
        while (i < players.length){
            int j=i;
            while ((j > 0) && (players[j-1].getCurrentSlot() < players[j].getCurrentSlot())){
                player temp = players[j-1];
                players[j-1] = players[j];
                players[j]=temp;
                j--;
            }
            i++;
        }
        return players;
    }

    public static player[] getOrder() {
        return order;
    }
}
