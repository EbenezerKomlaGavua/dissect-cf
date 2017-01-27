package com.ljmu.andre.FitbitSim;


import com.ljmu.andre.FitbitSim.DataStores.PhysicalMachineData;
import com.ljmu.andre.FitbitSim.DataStores.RepositoryData;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Created by Andre on 25/01/2017.
 */
public class Scenario {
    private static final String WATCH_XML_PATH = System.getProperty("user.dir") + "/Watch_Data.xml";

    public Scenario() {
        try {
            writePMachineData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadWatchFromXML() {

    }

    public void writePMachineData() throws IOException {
        /*Map<String, Integer> latMap = new HashMap<String, Integer>();
        latMap.put("Test", 100);

        PhysicalMachineData pmData =
                new PhysicalMachineData(1, 2, 3, 4, 5,
        new RepositoryData("Repo1", 2048, 100, 100, 50, latMap));

        XMLUtils.encode(pmData, WATCH_XML_PATH);*/

        PhysicalMachineData pmData = (PhysicalMachineData) XMLUtils.decode(WATCH_XML_PATH);
        System.out.println(pmData.toString());
    }
}
