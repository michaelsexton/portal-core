package org.auscope.portal.core.uifilter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import net.sf.json.JSONObject;

public class UIDate extends IFilterObject{

    public final String TYPE ="DATE";

    public UIDate(String label,String xpath,String value,Predicate predicate){
        this.setLabel(label);
        this.setXpath(xpath);
        this.setValue(value);
        this.setPredicate(predicate);
    }


    @Override
    public String getType() {
        return TYPE;
    }






}
