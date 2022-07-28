package com.example.Project.Resume.Graph.service;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;


import java.util.ArrayList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReadPdfService {

    @Autowired
    FileManageService fileManageService;

    @Autowired
    JsonStringService jsonStringService;

public String extractExperiences(MultipartFile file) throws IOException {

    String savedFileLocation = fileManageService.getSavedFileLocation(file);

    PDDocument pdf = PDDocument.load(new File(savedFileLocation));
    Writer output = new PrintWriter(savedFileLocation.substring(0,savedFileLocation.length()-4)+".html", "utf-8");
    new PDFDomTree().writeText(pdf, output);

    output.close();
    pdf.close();


    File input = new File(savedFileLocation.substring(0,savedFileLocation.length()-4)+".html");
    Document doc = Jsoup.parse(input, "UTF-8");

    Elements allElements = doc.getAllElements();

    int expBegins = 0;
    int expEnds = 0;

    ArrayList<Element> listOfElements = new ArrayList<>();

    ArrayList<Element> cleanListOfElements = new ArrayList<>();

    ArrayList<String> jsonOutput = new ArrayList<>();

    List<JSONObject> positionsList = new ArrayList<>();

    String mainCompany = "";
    String mainTimeDuration = "";



    String company = "";
    String title = "";
    String description = "";
    String timePeriod = "";
    String location= "";

//    String duration = "";
//    String startedYearAndMonth = "";
//    String endedYearAndMonth = "";
//    String numberOfYears = "";
//    String numberOfMonths = "";


    String shortDuration = "";
    String longDuration = "";

    JSONObject timePeriodObject;




    Boolean hasOnlyServiceDuration = false;

    ArrayList<ArrayList<String>> experienceListArray = new ArrayList<ArrayList<String>>();



    /**adding all elements to a Array list named allElements **/

    for (Element element : allElements) {

        listOfElements.add(element);


    }

    /** idetifying the required elements by capturing Experience and Education keyword as the index number of the arrayList of allElements ,
     *
     * decaring the index numbers to expBeging and expEnds **/

    for (int i = 0; i < listOfElements.size(); i++) {
        if (listOfElements.get(i).text().equals("Experience"))
            expBegins = i;
        if (listOfElements.get(i).text().equals("Education")) {
            expEnds = i;
            break;
        }

    }

    /**
     *  remove unnecessary elements from the captured middle content
     * capturing styles and splitting them into pieces
     * like not having index 4 of String lists and font size 9 removed
     * adding to new list named cleanListOfElements for the ease
     **/

    for (int i = expBegins + 1; i < expEnds; i++) {

        Element element = allElements.get(i);



        String style = element.attr("style");
        String[] styleValues = style.split(";");
        String styleValue = styleValues[0];



        if (styleValue.startsWith("top") && !styleValues[4].matches("font-size:9.0pt")
                && !styleValues[1].matches("left:0.0pt") && !styleValue.matches("top:-2.621973pt") ) {

            cleanListOfElements.add(element);

        }
    }

    /**
     * seperating the captured content into experiences one by one
     **/

    for (int i = 1; i < cleanListOfElements.size(); i++) {


        Element element = cleanListOfElements.get(i);
        Element beforeElement = cleanListOfElements.get(i - 1);

        String style = element.attr("style");
        String beforeStyle = beforeElement.attr("style");

        String[] styleValues = style.split(";");
        String[] beforeStyleValues = beforeStyle.split(";");

        String styleValue = styleValues[4];
        String beforeStyleValue = beforeStyleValues[4];


        String fontColor = styleValues[5];
        String beforeFontColor = beforeStyleValues[5];


        int styleIntigerValue1 = Integer.parseInt(styleValue.substring(10, 12));
        int styleIntigerValue2 = Integer.parseInt(beforeStyleValue.substring(10, 12));

        if(i==1)
            company = cleanListOfElements.get(0).text();

        if (styleIntigerValue1==10 && i==cleanListOfElements.size()-1){

            description += element.text();

        }

        if((styleIntigerValue1 ==12 && styleIntigerValue2 ==10 ) || i==cleanListOfElements.size()-1){




            ArrayList<String> experienceList2 = new ArrayList<>();

            if(location.isEmpty()){

                Pattern pattern = Pattern.compile("[A-Za-z]*.\\d{4}.-.(([A-Za-z]*.\\d{4})|(Present)).\\((\\d*.year)?.\\d*.months?\\)");
                Matcher matcher = pattern.matcher(description);
                Boolean matchFound = matcher.find();



                if(matchFound){
                    timePeriod = matcher.group(1);

//                    longDuration = jsonStringService.getLongDuration(timePeriod);



//                    System.out.println(longDuration);

                    description = description.substring(timePeriod.length(),description.length()-1);


                }


            }

            if(location.isEmpty() && timePeriod.isEmpty()){
                Pattern pattern = Pattern.compile("(\\d*.years?.)?(\\d*.months?)");
                Matcher matcher = pattern.matcher(description);
                Boolean matchFound = matcher.find();


                if(matchFound){
                    timePeriod = matcher.group(0);



                    description = description.substring(timePeriod.length(),description.length()-1);
                }

            }





            if(!location.isEmpty() && timePeriod.isEmpty()) {
                timePeriod = description;
                description = "";
            }



            if (company!="" && company.charAt(company.length()-1)==' ')
                company = company.substring(0,company.length()-1);
            if (title!="" && title.charAt(title.length()-1)==' ')
                title = title.substring(0,title.length()-1);
            if (timePeriod!="" && timePeriod.charAt(timePeriod.length()-1)==' ')
                timePeriod = timePeriod.substring(0, timePeriod.length() - 1);
            if (description!="" && description.charAt(description.length()-1)==' ')
                description = description.substring(0,description.length()-1);

            experienceList2.add(company);
            experienceList2.add(title);
            experienceList2.add(timePeriod);
            experienceList2.add(description);





                JSONObject jsonString = new JSONObject();
                        jsonString.put("company", company);
                        jsonString.put("title", title);
                        jsonString.put("time duration", jsonStringService.getLongDuration(timePeriod));
                        jsonString.put("description", description);

                positionsList.add(jsonString);





             if (hasOnlyServiceDuration) {

                String jsonString2 = new JSONObject()
                        .put("company",mainCompany)

                        .put("timePeriod",jsonStringService.getShortDuration(timePeriod))

                        .put("positions", positionsList)
                        .toString();

                jsonOutput.add(jsonString2);

                hasOnlyServiceDuration = false;



                mainCompany="";
                mainTimeDuration="";



                positionsList.clear();



            } else{

                String jsonString2 = new JSONObject()
                        .put("company",company)

                        .put("timePeriod",jsonStringService.getLongDuration(timePeriod))

                        .put("positions", positionsList)
                        .toString();

                jsonOutput.add(jsonString2);

                 positionsList.clear();

            }






//            positionsList.clear();


            experienceListArray.add(experienceList2);


            company = "";
            title = "";
            description = "";
            timePeriod = "";
            location="";

//				havingMoreTitles = false;

        }


        if(styleIntigerValue1 ==11 && styleIntigerValue2 ==10 && title.isEmpty() && timePeriod.isEmpty()){



            mainCompany = company;
            mainTimeDuration = description;

            description = "";



            hasOnlyServiceDuration = true;


        }
        if(styleIntigerValue1 ==11 && styleIntigerValue2 ==10 && !title.isEmpty()){





            ArrayList<String> experienceList2 = new ArrayList<>();

            if(location.isEmpty()){

                Pattern pattern = Pattern.compile("[A-Za-z]*.\\d{4}.-.(([A-Za-z]*.\\d{4})|(Present)).\\((\\d*.year)?.\\d*.months?\\)");
                Matcher matcher = pattern.matcher(description);
                Boolean matchFound = matcher.find();



                if(matchFound){
                    timePeriod = matcher.group(0);

                    description = description.substring(timePeriod.length(),description.length()-1);
                }


            }

            if(location.isEmpty() && timePeriod.isEmpty()){
                Pattern pattern = Pattern.compile("(\\d*.years?.)?(\\d*.months?)");
                Matcher matcher = pattern.matcher(description);
                Boolean matchFound = matcher.find();






                if(matchFound){
                    timePeriod = matcher.group(0);
                    description = description.substring(timePeriod.length(),description.length()-1);
                }

            }

            if(!location.isEmpty() && timePeriod.isEmpty()) {
                timePeriod = description;
                description = "";
            }

            if (company!="" && company.charAt(company.length()-1)==' ')
                company = company.substring(0,company.length()-1);
            if (title!="" && title.charAt(title.length()-1)==' ')
                title = title.substring(0,title.length()-1);
            if (timePeriod!="" && timePeriod.charAt(timePeriod.length()-1)==' ')
                timePeriod = timePeriod.substring(0, timePeriod.length() - 1);
            if (description!="" && description.charAt(description.length()-1)==' ')
                description = description.substring(0,description.length()-1);

            experienceList2.add(company);
            experienceList2.add(title);
            experienceList2.add(timePeriod);
            experienceList2.add(description);

            JSONObject jsonString = new JSONObject();
                    jsonString.put("company", company);
                    jsonString.put("title", title);
                    jsonString.put("time period", jsonStringService.getLongDuration(timePeriod));
                    jsonString.put("description", description);

            positionsList.add(jsonString);


            experienceListArray.add(experienceList2);



            title = "";
            description = "";
            timePeriod = "";
            location="";


        }





        if (styleIntigerValue1==12 ){

            company += element.text() + " ";
        }

        if (styleIntigerValue1==11){



            title += element.text()+ " ";


        }

        //identify location
        if(styleIntigerValue1==10 && fontColor.matches("color:#b0b0b0")){
            location += location+ " ";
        }


        if (styleIntigerValue1==10 && fontColor.matches("color:#181818")){



            if(beforeFontColor.matches("color:#b0b0b0")) {

                timePeriod = description;

                description = "";



            }

            description += element.text() + " ";

        }


    }

    fileManageService.discardFiles(savedFileLocation);
    fileManageService.discardFiles(savedFileLocation.substring(0,savedFileLocation.length()-4)+".html");

    return jsonOutput.toString();

}

}
