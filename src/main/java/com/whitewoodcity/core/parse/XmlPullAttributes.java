package com.whitewoodcity.core.parse;

import com.whitewoodcity.util.XmlUtils;
import org.xmlpull.v1.XmlPullParser;

class XmlPullAttributes implements AttributeSet{

    XmlPullParser mParse;

    public XmlPullAttributes(XmlPullParser parser){
        mParse=parser;
    }

    @Override
    public int getAttributeCount() {
        return mParse.getAttributeCount();
    }

    @Override
    public String getAttributeName(int index) {
        return mParse.getAttributeName(index);
    }

    @Override
    public String getAttributeValue(int index) {
        return mParse.getAttributeValue(index);
    }

    @Override
    public String getAttributeValue(String namespace, String name) {
        return mParse.getAttributeValue(namespace,name);
    }

    @Override
    public String getPositionDescription() {
        return mParse.getPositionDescription();
    }

    @Override
    public int getAttributeNameResource(int index) {
        return 0;
    }

    @Override
    public int getAttributeListValue(String namespace, String attribute, String[] options, int defaultValue) {
        return XmlUtils.convertValueToList(getAttributeValue(namespace,attribute),options,defaultValue);
    }

    @Override
    public boolean getAttributeBooleanValue(String namespace, String attribute, boolean defaultValue) {
        return XmlUtils.convertValueToBoolean(getAttributeValue(namespace,attribute),defaultValue);
    }

    @Override
    public int getAttributeResourceValue(String namespace, String attribute, int defaultValue) {
        return XmlUtils.convertValueToInt(getAttributeValue(namespace,attribute),defaultValue);
    }

    @Override
    public int getAttributeIntValue(String namespace, String attribute, int defaultValue) {
        return XmlUtils.convertValueToInt(getAttributeValue(namespace,attribute),defaultValue);
    }

    @Override
    public int getAttributeUnsignedIntValue(String namespace, String attribute, int defaultValue) {
        return XmlUtils.convertValueToUnsignedInt(getAttributeValue(namespace,attribute),defaultValue);
    }

    @Override
    public float getAttributeFloatValue(String namespace, String attribute, float defaultValue) {
        String s = getAttributeValue(namespace, attribute);
        if (s != null) {
            return Float.parseFloat(s);
        }
        return defaultValue;
    }

    @Override
    public int getAttributeListValue(int index, String[] options, int defaultValue) {
        return XmlUtils.convertValueToList(
                getAttributeValue(index), options, defaultValue);
    }

    @Override
    public boolean getAttributeBooleanValue(int index, boolean defaultValue) {
        return XmlUtils.convertValueToBoolean(
                getAttributeValue(index), defaultValue);
    }

    @Override
    public int getAttributeResourceValue(int index, int defaultValue) {
        return XmlUtils.convertValueToInt(
                getAttributeValue(index), defaultValue);
    }

    @Override
    public int getAttributeIntValue(int index, int defaultValue) {
        return XmlUtils.convertValueToInt(
                getAttributeValue(index), defaultValue);
    }

    @Override
    public int getAttributeUnsignedIntValue(int index, int defaultValue) {
        return XmlUtils.convertValueToUnsignedInt(
                getAttributeValue(index), defaultValue);
    }

    @Override
    public float getAttributeFloatValue(int index, float defaultValue) {
        String s = getAttributeValue(index);
        if (s != null) {
            return Float.parseFloat(s);
        }
        return defaultValue;
    }

    @Override
    public String getIdAttribute() {
        return getAttributeValue(null, "id");
    }

    @Override
    public String getClassAttribute() {
        return getAttributeValue(null, "class");
    }

    @Override
    public int getIdAttributeResourceValue(int defaultValue) {
        return getAttributeResourceValue(null, "id", defaultValue);
    }

    @Override
    public int getStyleAttribute() {
        return getAttributeResourceValue(null, "style", 0);
    }
}
