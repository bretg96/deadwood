
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class BoardXMLParser {
    static room[] rooms = new room[12];

    public static room[] getBoardData () throws ParserConfigurationException, IOException, SAXException {

        String cardFile = "board.xml";
        File inputFile = new File(cardFile);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(inputFile);
        doc.getDocumentElement().normalize();

        int nLength;
        int pLength;
        int tLength;
        int oLength;

        int X;
        int Y;
        int H;
        int W;

        Element root = doc.getDocumentElement();

        NodeList setNodes = root.getElementsByTagName("set");
        NodeList getTrailer = root.getElementsByTagName("trailer");
        NodeList getOffice = root.getElementsByTagName("office");

        Element office = (Element)getOffice.item(0);
        Element trailer = (Element)getTrailer.item(0);

        NodeList getArea ;
        Element area ;

        getArea = trailer.getElementsByTagName("area");
        area = (Element)getArea.item(0);
        X = Integer.parseInt(area.getAttribute("x"));
        Y = Integer.parseInt(area.getAttribute("y"));
        H = Integer.parseInt(area.getAttribute("h"));
        W = Integer.parseInt(area.getAttribute("w"));

        NodeList t = trailer.getElementsByTagName("neighbors");
        Element neigh =  (Element)t.item(0);
        NodeList getNeighbors = neigh.getElementsByTagName("neighbor");
        tLength = getNeighbors.getLength();
        String [] Neighbors2 = new String [tLength];

        for (int j = 0; j < tLength; j++) {
            Element neighbor = (Element)getNeighbors.item(j);
            Neighbors2[j] = neighbor.getAttribute("name");
        }

        slot [] max = new slot[8];

        room startingRoom = new room("trailer",max , 0,Neighbors2,2650,650,H,W,null,false);
        rooms [10] = startingRoom;


        NodeList o = office.getElementsByTagName("neighbors");
        neigh =  (Element)o.item(0);
        getNeighbors = neigh.getElementsByTagName("neighbor");
        oLength = getNeighbors.getLength();
        String [] Neighbors3 = new String [oLength];
        for (int j = 0; j < oLength; j++) {
            Element neighbor = (Element)getNeighbors.item(j);
            Neighbors3[j] = neighbor.getAttribute("name");
        }
        getArea = office.getElementsByTagName("area");
        area = (Element)getArea.item(0);
        X = Integer.parseInt(area.getAttribute("x"));
        Y = Integer.parseInt(area.getAttribute("y"));
        H = Integer.parseInt(area.getAttribute("h"));
        W = Integer.parseInt(area.getAttribute("w"));

        room castingOffice= new room("office",max , 0,Neighbors3,552,1200,H,W,null,false);
        rooms[11] = castingOffice;



        for (int i=0; i<setNodes.getLength();i++){

            Element set = (Element)setNodes.item(i);

            String toBeUsed = set.getAttribute("name");

            NodeList n = set.getElementsByTagName("neighbors");
            neigh =  (Element)n.item(0);
            getNeighbors = neigh.getElementsByTagName("neighbor");
            nLength = getNeighbors.getLength();
            String [] Neighbors = new String [nLength];
            for (int j = 0; j < nLength; j++) {
                Element neighbor = (Element)getNeighbors.item(j);
                Neighbors[j] = neighbor.getAttribute("name");
            }
            getArea = set.getElementsByTagName("area");
            area = (Element)getArea.item(0);
            X = (int) ((Integer.parseInt(area.getAttribute("x"))+500)*2)-475;
            Y = (int) ((Integer.parseInt(area.getAttribute("y"))+100)*2)+415;
            H = Integer.parseInt(area.getAttribute("h"));
            W = Integer.parseInt(area.getAttribute("w"));



            NodeList take = set.getElementsByTagName("takes");
            Element takes = (Element)take.item(0);
            NodeList getTakes = takes.getElementsByTagName("take");
            Element ta = (Element)getTakes.item(0);

            int takeNum = Integer.parseInt(ta.getAttribute("number"));

            int [][] takeArea = new int[takeNum][4];

            for(int T = 0; T < takeNum;T++){
                NodeList areas = takes.getElementsByTagName("area");
                area = (Element)areas.item(T);
                takeArea[T][0] = ((Integer.parseInt(area.getAttribute("x"))+500)*2)-495;
                takeArea[T][1] = ((Integer.parseInt(area.getAttribute("y"))+300)*2)+255;
                takeArea[T][2] = Integer.parseInt(area.getAttribute("h"));
                takeArea[T][3] = Integer.parseInt(area.getAttribute("w"));
            }


            NodeList getParts = set.getElementsByTagName("parts");
            Element parts = (Element)getParts.item(0);
            NodeList part = parts.getElementsByTagName("part");
            pLength = part.getLength();
            String [][] p = new String[pLength][3];

            slot [] slots = new slot[pLength];

            for (int k = 0; k < pLength; k++) {
                Element par = (Element)part.item(k);
                p[k][0] = par.getAttribute("name");
                p[k][1] = par.getAttribute("level");
                p[k][2] = par.getElementsByTagName("line").item(0).getTextContent();

                getArea = par.getElementsByTagName("area");
                area = (Element)getArea.item(0);
                int x =  (Integer.parseInt(area.getAttribute("x"))*2)+535;
                int y =  (Integer.parseInt(area.getAttribute("y"))*2)+100;
                int h = Integer.parseInt(area.getAttribute("h"));
                int w = Integer.parseInt(area.getAttribute("w"));

                slot curSlot = new slot(p[k][0], Integer.parseInt(p[k][1]), p[k][2],x,y,h,w);
                slots[k] = curSlot;
            }


            room curRoom = new room(toBeUsed,slots,takeNum,Neighbors,X,Y,H,W,takeArea,false);

            rooms[i] = curRoom;

        }
        return rooms;
    }
}
