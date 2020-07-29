import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class player {

    private String name;
    private boolean onACard = false;
    private boolean onASlot = false;
    private int credits;
    private int money;
    private int rank;
    private int rehearseTokens;
    public  int currentSlot;
    public  int currentRoom;
    public boolean haveWork = true;
    public boolean haveMove = true;
    public int playerNUM;
    public int cardCompleteThisTurn=0;
    public String color;
    public int XLOC;
    public int YLOC;
    public int startX;
    public int startY;

    public player(String name, int credits, int money, int rank, int currentRoom,
                  int currentSlot, int playerNUM, String color ,int XLOC , int YLOC, int startX, int startY){
        this.credits =credits;
        this.currentSlot=currentSlot;
        this.name = name;
        this.money =money;
        this.rank=rank;
        this.currentRoom = currentRoom;
        this.playerNUM = playerNUM;
        this.color = color;
        this.XLOC = XLOC;
        this.YLOC = YLOC;
        this.startX = startX;
        this.startY = startY;
    }

    public String getColor() {
        return color;
    }

    public int getCredits() {
        return credits;
    }

    public int getXLOC() {
        return XLOC;
    }

    public int getYLOC() {
        return YLOC;
    }

    public void setYLOC(int YLOC) {
        this.YLOC = YLOC;
    }

    public void setXLOC(int XLOC) {
        this.XLOC = XLOC;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getRehearseTokens() {
        return rehearseTokens;
    }

    public int getRank() {
        return rank;
    }

    public String getname() {
        return name;
    }

    public void setOnACard(boolean onACard) {
        this.onACard = onACard;
    }

    public void setOnASlot(boolean onASlot) {
        this.onASlot = onASlot;
    }

    public void setMoney(int money) {
        this.money = money + getMoney();
    }

    public int getMoney() {
        return money;
    }

    public int getCurrentRoom() {
        return currentRoom;
    }

    public int getCurrentSlot() {
        return currentSlot;
    }

    public boolean getOnACard(){
        return onACard;
    }

    public int getCardCompleteThisTurn() {
        return cardCompleteThisTurn;
    }

    public void startOfRound(){
        currentRoom = 10;
        onACard = false;
        onASlot = false;
        rehearseTokens = 0;
        cardCompleteThisTurn = 0;
        XLOC = startX;
        YLOC = startY;
    }

    public void startOfTurn() {
        haveMove=true;
        haveWork=true;
        cardCompleteThisTurn = 0;
    }

    public void turn() throws IOException {
        board.showInfo();
        int options = 1;
        while ((options == 1)){

            String choseOp = board.getPlayerCommand();
            if(choseOp.equals("upgrade")){
                if(currentRoom == 11){
                    upgrade();
                    board.initializePlayer();
                    board.showInfo();
                    board.frame.revalidate();
                    board.frame.repaint();
                }else {
                    board.information("you are not at the casting office");
                }
            }else if(choseOp.equals("move")){
                if(haveMove){
                    if(onASlot||onACard){
                        board.information("you cant move you are currently working" );
                    }else{
                        move();
                        board.initializePlayer();
                        board.frame.revalidate();
                        board.frame.repaint();
                    }

                }else{
                    board.information("you have already moved this turn");
                }
            }else if(choseOp.equals("work")){
                if(haveWork){
                    if (onASlot){
                        work();
                        board.initializePlayer();
                        board.displayPieces();
                        board.showInfo();
                        board.frame.revalidate();
                        board.frame.repaint();
                    }else{
                        board.information("you can't work you are not on a slot");
                    }
                }else{
                    board.information("you have already worked or rehearsed this turn ");
                }
            }else if(choseOp.equals("rehearse")){
                if(haveWork){
                    rehearse();
                    board.initializePlayer();
                }else{
                    board.information("you have already worked or rehearsed this turn ");
                }
            }else if(choseOp.equals("pass")){
                options--;
                board.information("                                                    " +
                        "                                                                     ");
            }
            board.showInfo();
        }
    }

    public void move() throws IOException {
        String [] possibleRooms =  board.findPossibleRooms(currentRoom);

        String choseRoom = board.roomChoice(possibleRooms);
        int choseLocation = board.convertNameToInt(choseRoom);

        if(onASlot){
            board.getRooms()[currentRoom].getSlots()[currentSlot].setIfEmpty(true);
        }

        if(onACard){
            board.getRooms()[currentRoom].getRoomCard().getSlots()[currentSlot].setIfEmpty(true);
        }

        if (!(choseLocation == 10||choseLocation == 11||choseLocation == -1)) {
            board.rooms[choseLocation].setVisited(true);
            board.displayPieces();
            board.information("do you want want to just be in the room");
            String[] YESorNO = new String[]{"yes", "no"};
            String choseCard = board.roomChoice(YESorNO);
            if (choseCard.equals("yes")) {
                currentRoom = choseLocation;
                setXLOC(board.rooms[currentRoom].getX()-15 + (playerNUM*50));
                setYLOC(board.rooms[currentRoom].getY()-285);
                haveMove = false;
            } else if (choseCard.equals("no")) {
                board.information("do you want a slot on the card or in the room");
                String[] ROOMorCARD = new String[]{"room", "card", "cancel"};
                String choseRoomOrCard = board.roomChoice(ROOMorCARD);

                if (choseRoomOrCard.equals("room")) {


                    board.information("select a role in the room");
                    int slotsInRoom = board.getRooms()[choseLocation].getSlots().length;
                    String[] roomSlots = new String[slotsInRoom];
                    for (int x = 0; x < slotsInRoom; x++) {
                        roomSlots[x] = board.getRooms()[choseLocation].getSlots()[x].getPartName();
                    }

                    String choseSlot = board.roomChoice(roomSlots);
                    choseSlot = choseSlot.toLowerCase();
                    slot[] temp = board.getRooms()[choseLocation].getSlots();
                    boolean rankCheck = false;
                    boolean avalibleCheck = false;

                    for (int z = 0; z < roomSlots.length; z++) {
                        if (choseSlot.equals(temp[z].getPartName().toLowerCase())) {
                            if (rank >= temp[z].getRequiredRank()) {
                                if (temp[z].getIfEmpty()) {
                                    currentRoom = choseLocation;
                                    temp[z].setIfEmpty(false);
                                    onASlot = true;
                                    currentSlot = z;
                                    setXLOC(temp[z].getX());
                                    setYLOC(temp[z].getY());
                                    haveMove = false;
                                } else {
                                    avalibleCheck = true;
                                }
                            } else {
                                rankCheck = true;
                            }
                        } else {

                        }
                    }
                    if (rankCheck) {
                        board.information("you are not high enough rank for that part");
                        currentRoom = choseLocation;
                        setXLOC(board.rooms[currentRoom].getX()-15 + (playerNUM*50));
                        setYLOC(board.rooms[currentRoom].getY()-285);
                        haveMove = false;
                    }
                    if (avalibleCheck) {
                        board.information("the slot you selected is taken");
                        currentRoom = choseLocation;
                        setXLOC(board.rooms[currentRoom].getX()-15 + (playerNUM*50));
                        setYLOC(board.rooms[currentRoom].getY()-285);
                        haveMove = false;
                    }

                } else if (choseRoomOrCard.equals("card")) {


                    board.information("select a role on the card");
                    int slotsOnCard = board.getRooms()[choseLocation].getRoomCard().getSlots().length;
                    String[] roomSlots = new String[slotsOnCard];

                    for (int x = 0; x < slotsOnCard; x++) {
                        roomSlots[x] = board.getRooms()[choseLocation].getRoomCard().getSlots()[x].getPartName();
                    }

                    String choseSlot = board.roomChoice(roomSlots);
                    choseSlot = choseSlot.toLowerCase();
                    slot[] temp = board.getRooms()[choseLocation].getRoomCard().getSlots();
                    boolean rankCheck = false;
                    boolean avalibleCheck = false;

                    for (int z = 0; z < roomSlots.length; z++) {
                        if (choseSlot.equals(temp[z].getPartName().toLowerCase())) {
                            if (rank >= temp[z].getRequiredRank()) {
                                if (temp[z].getIfEmpty()) {
                                    currentRoom = choseLocation;
                                    temp[z].setIfEmpty(false);
                                    onACard = true;
                                    onASlot = true;
                                    currentSlot = z;
                                    setXLOC(temp[z].getX()+board.getRooms()[currentRoom].getX());
                                    setYLOC(temp[z].getY()+board.getRooms()[currentRoom].getY());
                                    haveMove = false;
                                } else {
                                    avalibleCheck = true;
                                }
                            } else {
                                rankCheck = true;
                            }
                        } else {

                        }
                    }
                    if (rankCheck) {

                        board.information("you are not high enough rank for that part");
                        currentRoom = choseLocation;
                        setXLOC(board.rooms[currentRoom].getX()-15 + (playerNUM*50));
                        setYLOC(board.rooms[currentRoom].getY()-285);
                        haveMove = false;
                    }
                    if (avalibleCheck) {

                        board.information("the slot you selected is taken");
                        currentRoom = choseLocation;
                        setXLOC(board.rooms[currentRoom].getX()-15 + (playerNUM*50));
                        setYLOC(board.rooms[currentRoom].getY()-285);
                        haveMove = false;
                    }


                } else if (choseRoomOrCard.equals("cancel")) {
                    currentRoom = choseLocation;
                    setXLOC(board.rooms[currentRoom].getX()-15 + (playerNUM*50));
                    setYLOC(board.rooms[currentRoom].getY()-285);
                    haveMove = false;
                }
            }
        }else if(choseLocation == 10){

                currentRoom = choseLocation;
                setXLOC(startX);
                setYLOC(startY);
                haveMove = false;

        }else if(choseLocation == 11){

                currentRoom = choseLocation;
                setXLOC(startX - 2050);
                setYLOC(startY + 500);
                haveMove = false;

        }else {

        }
        board.information("                                                    " +
                "                                                                     ");
    }

    public void work(){
        boolean succesful = checkIfSuccesful();
        if (board.getRooms()[currentRoom].getScenes() == 0){
            board.information("this room has no more scenes to shoot on it");
        }else{
            if (onACard && succesful){
                credits = credits + 2;

                board.getRooms()[currentRoom].completeScene();
                cardCompleteThisTurn = board.getRooms()[currentRoom].isMovieComplete();

                board.information("you were successful and there are/is "+board.getRooms()[currentRoom].Scenes+" scene(s) left");
            }else if(!onACard && succesful ){
                credits = credits + 1;
                money =money + 1;

                board.getRooms()[currentRoom].completeScene();
                cardCompleteThisTurn = board.getRooms()[currentRoom].isMovieComplete();

                board.information("you were successful and there are "+board.getRooms()[currentRoom].Scenes+" left");
            }else if(onACard && !succesful ){
                board.information("you were not successful with your roll for this scene");
            }else if(!onACard && !succesful ) {
                money = money + 1;
                board.information("you were not successful with your roll for this scene");
            }
        }

    }

    private boolean checkIfSuccesful(){
        boolean temp = false;
        Random random = new Random();
        int ran = (random.nextInt(6)+1);

        if(currentRoom == 10 || currentRoom == 11){
            board.information("you cant work at the office or trailer");
        }else if((ran + rehearseTokens )>=
                board.getRooms()[currentRoom].getRoomCard().getPayout()){
            board.information("you rolled a " + ran +"making your total "+(ran + rehearseTokens)+"with tokens");

            temp = true;
            haveWork = false;
        }else {
            temp = false;
            haveWork = false;
        }
        rehearseTokens = 0;
        return temp;
    }

    public void rehearse (){
        if (currentRoom == 10){
            board.information("you cant rehearse at the trailer");
        }else if (currentRoom == 11){
            board.information("you cant rehearse at the office");
        }else{
            rehearseTokens++;
            haveWork = false;
        }
    }

    public void upgrade(){
        if(rank == 6){
            board.information("you are already at maxed rank");
        }else{

            board.information("what do you want to upgrade with");

            String [] MONEYorCREDITS = new String[] {"money", "credits"};

            String choice = board.roomChoice(MONEYorCREDITS);

            if(choice.equals("money")){
                rankUpM();
            }else if(choice.equals("credits")){
                rankUpC();
            }
        }

    }

    private void rankUpM(){
        board.information("what rank do you want to go to");

        String [] possibleRanks = new String[7-rank];

        for(int i = rank ; i <= 6 ; i++){
            possibleRanks [i-rank] = String.valueOf(i+1);
        }
        possibleRanks[6-rank] = "cancel";
        String choice = board.roomChoice(possibleRanks);

        if(choice.equals("2")){
            if(money >= 4){
                rank = 2;
                money = money - 4;
            }else{
             board.information("you didn't have enough money for that upgrade");
            }
        }else if(choice.equals("3")){
            if(money >= 10){
                rank = 3;
                money = money - 10;
            }else {
                board.information("you didn't have enough money for that upgrade");
            }
        }else if(choice.equals("4")){
            if(money >= 18){
                rank = 4;
                money = money - 18;
            }else{
                board.information("you didn't have enough money for that upgrade");
            }
        }else if(choice.equals("5")){
            if(money >= 28){
                rank = 5;
                money = money - 28;
            }else{
                board.information("you didn't have enough money for that upgrade");
            }
        }else if(choice.equals("6")){
            if(money >= 40){
                rank = 6;
                money = money - 40;
            }else{
                board.information("you didn't have enough money for that upgrade");
            }
        }else if(choice.equals("cancel")){
            board.information("upgrade was canceled");
        }
    }

    private void rankUpC(){
        board.information("what rank do you want to go to");

        String [] possibleRanks = new String[7-rank];

        for(int i = rank ; i <= 6 ; i++){
            possibleRanks [i-rank] = String.valueOf(i+1);
        }
        possibleRanks[6-rank] = "cancel";
        String choice = board.roomChoice(possibleRanks);

        if(choice.equals("2")){
            if(credits >= 5){
                rank = 2;
                credits = credits - 5;
            }else{
                board.information("you didn't have enough credits for that upgrade");
            }
        }else if(choice.equals("3")){
            if(credits >= 10){
                rank = 3;
                credits = credits - 10;
            }else{
                board.information("you didn't have enough credits for that upgrade");
            }
        }else if(choice.equals("4")){
            if(credits >= 15){
                rank = 4;
                credits = credits - 15;
            }else{
                board.information("you didn't have enough credits for that upgrade");
            }
        }else if(choice.equals("5")){
            if(credits >= 20){
                rank = 5;
                credits = credits - 20;
            }else{
                board.information("you didn't have enough credits for that upgrade");
            }
        }else if(choice.equals("6")){
            if(credits >= 25){
                rank = 6;
                credits = credits - 25;
            }else{
                board.information("you didn't have enough credits for that upgrade");
            }
        }else if(choice.equals("cancel")){
            board.information("upgrade was canceled");
        }
    }
}