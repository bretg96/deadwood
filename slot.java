public class slot {

    public boolean ifEmpty = true;
    public String partName;
    public int requiredRank;
    public String description;
    public int x;
    public int y;
    public int h;
    public int w;

    public slot(String partName , int requiredRank, String description, int x, int y, int h, int w){
        this.description = description;
        this.partName = partName;
        this.requiredRank = requiredRank;
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;

    }

    public int getRequiredRank() {
        return requiredRank;
    }

    public String getPartName() {
        return partName;
    }

    public void setIfEmpty(boolean ifEmpty) {
        this.ifEmpty = ifEmpty;
    }

    public boolean getIfEmpty(){
        return ifEmpty;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
