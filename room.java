public class room {

    private slot[] slots;
    public boolean finished = false;
    public String name;
    public card roomCard;
    public int Scenes;
    public String [] neigbors;
    public int x;
    public int y;
    public int h;
    public int w;
    public int [][]sceneLocations;
    public boolean visited = false;

    public room (String name,slot[] slots,int scenes, String[] neigbors ,int x,int y,int h,int w,int[][]sceneLocations,boolean visited){
        this.name = name;
        this.slots = slots;
        this.Scenes = scenes;
        this.neigbors = neigbors;
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
        this.sceneLocations = sceneLocations;
        this.visited = visited;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int[][] getSceneLocations() {
        return sceneLocations;
    }

    public void completeScene(){
        Scenes = Scenes - 1;
        if (Scenes == 0){
            finished = true;
        }
    }

    public int isMovieComplete(){
        if (finished){
            return 1;
        }else {
            return 0;
        }
    }

    public void setRoomCard(card roomCard) {
        this.roomCard = roomCard;
    }

    public String[] getNeigbors() {
        return neigbors;
    }

    public int getScenes() {
        return Scenes;
    }

    public card getRoomCard() {
        return roomCard;
    }

    public String getName() {
        return name;
    }

    public slot[] getSlots() {
        return slots;
    }

    public boolean getVisited(){
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getFinished(){
        return finished;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public void setScenes(int scenes) {
        Scenes = scenes;
    }
}
