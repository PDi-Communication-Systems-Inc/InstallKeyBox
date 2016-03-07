package com.pdiarm.installkeybox;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by mrobbeloth on 3/5/16.
 */
public class ProcessWidevineKeyboxes {
    public static final String WIDEVINE_NODE_NAME = "Widevine";
    public static final String NUM_KEYBOXES_NODE_NAME = "NumberOfKeyboxes";
    public static final String KEYBOX_NODE_NAME = "Keybox";
    public static final String KEY_NODE_NAME = "Key";
    public static final String ID_NODE_NAME="ID";
    public static final String MAGIC_NODE_NAME="Magic";
    public static final String CRC_NODE_NAME="CRC";
    public static final String TAG = "ProcessWidevineKeyboxes";
    private ArrayList<KeyBoxData> kbaList;

    public  ArrayList<KeyBoxData> getKba() {
        return kbaList;
    }

    public ProcessWidevineKeyboxes(File f) {
        kbaList = new ArrayList<KeyBoxData>();
        InputStream in = null;
        if (f.exists()) {
            try {
                in = new FileInputStream(f);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "File not found: " + e.toString());
            }
        }

        XmlPullParser xmlPP = Xml.newPullParser();
        try {
            // don't use namespaces
            xmlPP.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            // connect the file to the parser
            if (in == null) {
                Log.e(TAG, "No file to parse, aborting");
                return;
            }
            xmlPP.setInput(in, null);

            // read the first entry after the xml declaration
            int eventType = xmlPP.nextTag();

            // make sure file starts with START_TAG
            xmlPP.require(XmlPullParser.START_TAG, null, WIDEVINE_NODE_NAME);

            readWidevine(xmlPP);

        } catch (IOException ioe){
            Log.e(TAG, "IO Exception in parsing Widevine xml file, error is " + ioe.toString());
        } catch (XmlPullParserException xppe) {
            Log.e(TAG, "XML Parser Exception, error is " + xppe.toString());
        }

    }

    private void readWidevine(XmlPullParser parser) {
        try {
            int eventType = parser.nextTag();
            parser.require(XmlPullParser.START_TAG, null, NUM_KEYBOXES_NODE_NAME);
            int numKeyboxes = 0;
            if (parser.next() == XmlPullParser.TEXT) {
                numKeyboxes = Integer.valueOf(parser.getText().toString());
            }

            // look for end of NUM_KEYBOXES_NODE_NAME
            eventType = parser.nextTag();
            parser.require(XmlPullParser.END_TAG, null, NUM_KEYBOXES_NODE_NAME);

            for (int i = 0; i < numKeyboxes; i++) {
                KeyBoxData kba = new KeyBoxData();

                // get keybox device id
                eventType = parser.nextTag();
                parser.require(XmlPullParser.START_TAG, null, KEYBOX_NODE_NAME);
                String deviceIdName = parser.getAttributeName(0);
                if (deviceIdName.equals("DeviceID")) {
                    kba.setDeviceID(parser.getAttributeValue(0));
                }

                // get keybox device key
                eventType = parser.nextTag();
                parser.require(XmlPullParser.START_TAG, null, KEY_NODE_NAME);
                if (parser.next() == XmlPullParser.TEXT) {
                    kba.setDeviceKey(parser.getText());
                }
                eventType = parser.nextTag();
                parser.require(XmlPullParser.END_TAG, null, KEY_NODE_NAME);

                // get keybox key data
                eventType = parser.nextTag();
                parser.require(XmlPullParser.START_TAG, null, ID_NODE_NAME);
                if (parser.next() == XmlPullParser.TEXT) {
                    kba.setKeyData(parser.getText());
                }
                eventType = parser.nextTag();
                parser.require(XmlPullParser.END_TAG, null, ID_NODE_NAME);

                // get keybox magic data
                eventType = parser.nextTag();
                parser.require(XmlPullParser.START_TAG, null, MAGIC_NODE_NAME);
                if (parser.next() == XmlPullParser.TEXT) {
                    kba.setMagic(parser.getText());
                }
                eventType = parser.nextTag();
                parser.require(XmlPullParser.END_TAG, null, MAGIC_NODE_NAME);

                // get keybox crc
                eventType = parser.nextTag();
                parser.require(XmlPullParser.START_TAG, null, CRC_NODE_NAME);
                if (parser.next() == XmlPullParser.TEXT) {
                    kba.setCrc(parser.getText());
                }
                eventType = parser.nextTag();
                parser.require(XmlPullParser.END_TAG, null, CRC_NODE_NAME);

                kbaList.add(kba);
            }
        } catch (IOException ioe){
            Log.e(TAG, "IO Exception in parsing Widevine xml file, error is " + ioe.toString());
        } catch (XmlPullParserException xppe) {
            Log.e(TAG, "XML Parser Exception, error is " + xppe.toString());
        }

    }
}
