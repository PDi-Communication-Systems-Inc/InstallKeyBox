/**
 * Created by mrobbeloth on 3/4/16.
 */
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

public class KeyBoxData {
    private String deviceID;
    private String deviceKey;
    private String keyData;
    private String magic;

    private String crc;
    public static final String WIDEVINE_NODE_NAME = "Widevine";
    public static final String NUM_KEYBOXES_NODE_NAME = "NumberOfKeyboxes";
    public static final String KEYBOX_NODE_NAME = "Keybox";
    public static final String KEY_NODE = "Key";
    public static final String TAG = "KeyBoxData";
    public static final String DEVICE_ID_STRING = "deviceID";
    public static final String DEVICE_KEY_STRING = "deviceKey";
    public static final String KEY_DATA_STRING = "keyData";
    public static final String CRC_STRING = "crc";

    public KeyBoxData() {
        this.deviceID = "BAD";
        this.deviceKey = "BAD";
        this.keyData = "BAD";
        this.crc = "BAD";
    }

    public KeyBoxData(String deviceID, String deviceKey, String keyData, String crc) {
        this.deviceID = deviceID;
        this.deviceKey = deviceKey;
        this.keyData = keyData;
        this.crc = crc;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getKeyData() {
        return keyData;
    }

    public void setKeyData(String keyData) {
        this.keyData = keyData;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Device ID: " + getDeviceID() + " ");
        sb.append("Device Key: " + getDeviceKey() + " ");
        sb.append("Key Data: " + getKeyData() + " ");
        sb.append("Magic: " + getKeyData() + " ");
        sb.append("CRC: " + getCrc());
        return sb.toString();
    }
}
