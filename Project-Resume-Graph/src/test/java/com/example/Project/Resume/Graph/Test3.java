package com.example.Project.Resume.Graph;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3 {
    @Test
    public void testing(){


        String duration = "1 year 11 months";

        Pattern pattern = Pattern.compile("((\\d*).years?.)?(\\d*).months?");
        Matcher matcher = pattern.matcher(duration);
        Boolean matchFound = matcher.find();

        if(matchFound) {

            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));

        }








    }
}
