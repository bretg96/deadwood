
public class card {

    private String Description;
    private int sceneNum;
    private String name;
    private slot[]  slots;
    private int payout;
    public String imageName;

    public card( String name , String imageName ,int payout,
                 String Description,int sceneNum, slot [] slots){

        this.name =name;
        this.imageName = imageName;
        this.Description = Description;
        this.sceneNum = sceneNum;
        this.slots = slots;
        this.payout = payout;
    }

    public slot[] getSlots() {
        return slots;
    }

    public String getName() {
        return name;
    }

    public int getPayout() {
        return payout;
    }

    public String getImageName() {
        return imageName;
    }
}
