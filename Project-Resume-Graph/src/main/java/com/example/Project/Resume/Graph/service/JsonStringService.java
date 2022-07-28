package com.example.Project.Resume.Graph.service;


import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class JsonStringService {

    List<String> longDuration = new ArrayList<>();
    public JSONObject getShortDuration(String duration){

        Pattern pattern = Pattern.compile("((\\d*).years?.)?(\\d*).months?\\s?");
        Matcher matcher = pattern.matcher(duration);
        Boolean matchFound = matcher.find();

        if(matchFound){
            JSONObject jsonObject = new JSONObject();

                   jsonObject .put("years", matcher.group(2));
                   jsonObject.put("months", matcher.group(3));

            return jsonObject;

        }else
            return null;
    }
    public JSONObject getShortDuration2 (String duration){

        Pattern pattern = Pattern.compile("([A-Za-z]*)\\s(\\d*)");
        Matcher matcher = pattern.matcher(duration);
        Boolean matchFound = matcher.find();

        if(matchFound) {
            JSONObject jsonObject = new JSONObject();
                   jsonObject.put("month", matcher.group(1));
                    jsonObject.put("year", matcher.group(2));
            return jsonObject;
        }else
            return null;
    }
    public JSONObject getLongDuration(String duration){

        Pattern pattern = Pattern.compile("([A-Za-z]*.\\d{4}).-.([A-Za-z]*.\\d{4}|Present).\\(((\\d*.years?)?.\\d*.months?)\\)");
        Matcher matcher = pattern.matcher(duration);
        Boolean matchFound = matcher.find();

        if(matchFound){

            JSONObject jsonObject = new JSONObject();

                    jsonObject.put("starting", getShortDuration2(matcher.group(1)));
                    if(getShortDuration2(matcher.group(2))==null)
                        jsonObject.put("ending","present");
                    else
                        jsonObject.put("ending", getShortDuration2(matcher.group(2)));

                    jsonObject.put("duration", getShortDuration(matcher.group(3)));

            return jsonObject;

        }else
            return null;
    }
}
