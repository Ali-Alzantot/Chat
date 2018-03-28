/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapp;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;

/**
 * this class for saving chat as xml files
 * @author Team 4
 */

public class XMLBuilder {

    public void buildXML(VBox v) {
        try {
            DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder dB = dBF.newDocumentBuilder();
            Document doc = dB.newDocument();
            Element ms = doc.createElement("Messages");
            ProcessingInstruction pI = doc.createProcessingInstruction("xml-stylesheet", "type='text/xsl' href='convertertry.xsl'");
            String img = null;
            doc.appendChild(ms);
            doc.insertBefore(pI, ms);
            for (Node n : v.getChildren()) {
                HBox x = (HBox) n;
                TextFlow tF;
                String tDir;
                if (x.getAlignment().getHpos().toString().toLowerCase().contains("right") || x.getChildren().size() == 1) {
                    tF = (TextFlow) x.getChildren().get(0);
                    img = "l.png";
                    tDir="rtl";
                } else {
                    tF = (TextFlow) x.getChildren().get(1);
                    img = "n.png";
                    tDir="ltr";
                }
                Text t = (Text) tF.getChildren().get(0);
                // img = (ImageView) tF.getChildren().get(1);
                String bg = tF.getStyle();
                if (!bg.isEmpty()) {
                    bg = bg.substring(bg.indexOf("#"), bg.indexOf("#") + 7);
                }
                System.out.println();
                String fS = String.valueOf(t.getFont().getSize());
                String fC = t.getFill().toString().replace("0x", "#");
                String fF = t.getFont().getFamily();
                String dir = x.getAlignment().getHpos().toString().toLowerCase();
                String txt = t.getText();
                //String img = img.ge;
                Element backg = doc.createElement("background");
                Element fSize = doc.createElement("size");
                Element fColor = doc.createElement("fontcolor");
                Element fFamily = doc.createElement("family");
                Element direct = doc.createElement("direction");
                Element text = doc.createElement("text");
                Element tDirect = doc.createElement("tDirect");
                Element iURL=doc.createElement("URL");
                backg.appendChild(doc.createTextNode(bg));
                fSize.appendChild(doc.createTextNode(fS));
                fColor.appendChild(doc.createTextNode(fC));
                fFamily.appendChild(doc.createTextNode(fF));
                direct.appendChild(doc.createTextNode(dir));
                text.appendChild(doc.createTextNode(txt));
                tDirect.appendChild(doc.createTextNode(tDir));
                iURL.appendChild(doc.createTextNode(img));
                Element msg = doc.createElement("msg");
                msg.appendChild(tDirect);
                msg.appendChild(backg);
                msg.appendChild(fSize);
                msg.appendChild(fColor);
                msg.appendChild(fFamily);
                msg.appendChild(direct);
                msg.appendChild(text);
                msg.appendChild(iURL);
                ms.appendChild(msg);
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer trans = tf.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult("Save messages.xml");
                trans.transform(source, result);

            }
        } catch (Exception ex) {
            Logger.getLogger(XMLBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
