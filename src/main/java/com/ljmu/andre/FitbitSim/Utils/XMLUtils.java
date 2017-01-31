package com.ljmu.andre.FitbitSim.Utils;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

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
        XMLEncoder encoder = null;

        try {
            encoder =
                    new XMLEncoder(
                            new BufferedOutputStream(
                                    new FileOutputStream(filepath)));

            encoder.writeObject(objToWrite);
        } finally {
            if(encoder != null) {
                encoder.flush();
                encoder.close();
            }
        }
    }

    @Nullable public static Object decode(@NotNull String filepath) {
        BufferedInputStream fis =
                null;
        try {
            fis = new BufferedInputStream(
                    new FileInputStream(filepath));

            XMLDecoder decoder = new XMLDecoder(fis);
            Object decodedObject = decoder.readObject();
            decoder.close();
            fis.close();

            return decodedObject;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ignored) {
                }
            }
        }

        return null;
    }
}
