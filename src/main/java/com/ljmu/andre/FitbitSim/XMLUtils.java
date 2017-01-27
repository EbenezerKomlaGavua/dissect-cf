package com.ljmu.andre.FitbitSim;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Andre on 26/01/2017.
 */
public class XMLUtils {

    public static void encode(Object objToWrite, String filepath) throws FileNotFoundException {
        XMLEncoder encoder =
                new XMLEncoder(
                        new BufferedOutputStream(
                                new FileOutputStream(filepath)));

        encoder.writeObject(objToWrite);
        encoder.flush();
        encoder.close();
    }

    public static Object decode(String filepath) throws IOException {
        BufferedInputStream fis =
                new BufferedInputStream(
                        new FileInputStream(filepath));

        XMLDecoder decoder = new XMLDecoder(fis);
        Object decodedObject = decoder.readObject();
        decoder.close();
        fis.close();
        return decodedObject;
    }
}
