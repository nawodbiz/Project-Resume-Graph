package com.example.Project.Resume.Graph.service;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Service
public class JsonStringService {
    /**extract the time period string to months and years and adding them into a new json object*/
    public JSONObject getShortDuration(String duration, Boolean isADuration){
        Pattern pattern = Pattern.compile("([A-Za-z]*)\\s(\\d*)");
        int i = 1;
        if(isADuration) {
            pattern = Pattern.compile("((\\d*).years?.)?(\\d*).months?\\s?");
            i = 3;
        }
        Matcher matcher = pattern.matcher(duration);
        Boolean matchFound = matcher.find();
        if(matchFound){
            JSONObject jsonObject = new JSONObject();
            jsonObject .put("year", matcher.group(2));
            jsonObject.put("month", matcher.group(i));
            return jsonObject;
        }else
            return null;
    }
    /**extract the time period full property and assign them to starting and ending and duration assign them into new json object*/
    public JSONObject getLongDuration(String duration){
        Pattern pattern = Pattern.compile("([A-Za-z]*.\\d{4}).-.([A-Za-z]*.\\d{4}|Present).\\(((\\d*.years?)?.\\d*.months?)\\)");
        Matcher matcher = pattern.matcher(duration);
        Boolean matchFound = matcher.find();
        if(matchFound){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("starting", getShortDuration(matcher.group(1),false));
            if(getShortDuration(matcher.group(2),false)==null){
                JSONObject presentEndingStructure = new JSONObject();
                presentEndingStructure.put("year", "present");
                presentEndingStructure.put("month","present");
                jsonObject.put("ending", presentEndingStructure);
            }
            else
                jsonObject.put("ending", getShortDuration(matcher.group(2),false));
            jsonObject.put("duration", getShortDuration(matcher.group(3),true));
            return jsonObject;
        }else
            return null;
    }
}
