import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

public class CardXMLparser{
   static card[] cards = new card[40];

   public static card[] cardReader(){
      try{

         String cardFile = "cards.xml";
         File inputFile = new File(cardFile);

         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();

         Document doc = db.parse(inputFile);
         doc.getDocumentElement().normalize();

         NodeList cList = doc.getElementsByTagName("card");

         for(int i=0; i<cList.getLength(); i++){
            Node node = cList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
               Element element = (Element) node;
              // card currCard = new card();

               String name = element.getAttribute("name");
               String sceneImg = element.getAttribute("img");

               int budget = Integer.parseInt(element.getAttribute("budget"));
               int sceneNum = Integer.parseInt(((Element) element.getElementsByTagName("scene").item(0)).getAttribute("number"));

               String descr = element.getElementsByTagName("scene").item(0).getTextContent();
               NodeList part = element.getElementsByTagName("part");

               slot [] tempParts = new slot[part.getLength()] ;

               for(int j = 0; j < part.getLength(); j++) {

                   Node pNode = part.item(j);
                  if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                     Element pElement = (Element) pNode;
                     String pNames = pElement.getAttribute("name");
                     String pDescr = pElement.getElementsByTagName("line").item(0).getTextContent();
                     int lvl = Integer.parseInt(pElement.getAttribute("level"));

                     NodeList area = pElement.getElementsByTagName("area");
                     Node holder = area.item(0);
                     Element e = (Element) holder;
                     int xDim = (Integer.parseInt(e.getAttribute("x"))*2)+9;
                     int yDim = (Integer.parseInt(e.getAttribute("y"))-475);
                     int hDim = Integer.parseInt(e.getAttribute("h"));
                     int wDim = Integer.parseInt(e.getAttribute("h"));

                     slot currP = new slot(pNames,lvl,pDescr,xDim,yDim,hDim,wDim);

                     tempParts[j] = currP;
                  }


               }
                card finishedCard = new card(name , sceneImg , budget , descr ,sceneNum,tempParts);
                cards[i] = finishedCard;


            }
         }

      }catch(Exception e){
         System.out.print("ERROR unable to parse file");
      }
      return cards;
   }
}

