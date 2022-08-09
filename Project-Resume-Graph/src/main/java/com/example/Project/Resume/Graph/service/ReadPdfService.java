package com.example.Project.Resume.Graph.service;
import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Data
public class ReadPdfService{

    @Autowired
    FileManageService fileManageService;
    @Autowired
    JsonStringService jsonStringService;
    int expBegins = 0;
    int expEnds = 0;
    int contactIndex = 0;
    int topSkillsIndex = 0;
    ArrayList<Element> listOfElements = new ArrayList<>();
    ArrayList<Element> cleanListOfElements = new ArrayList<>();
    ArrayList<JSONObject> experienceList = new ArrayList<>();
    JSONObject finalJsonOutput = new JSONObject();
    List<JSONObject> positionsList = new ArrayList<>();
    JSONObject profileDetails = new JSONObject();
    JSONObject jsonData = new JSONObject();
    String companyWithMorePositions = "";
    String timePeriodWithMorePositions = "";
    String company = "";
    String title = "";
    String description = "";
    String timePeriod = "";
    String location = "";
    Boolean hasOnlyServiceDuration = false;
    String profileName = "";
    String currentPosition = "";
    String currentLocation = "";
    String currentPositionTemp = "";
    String emailAddress = "";
    String linkedinProfileLink = "";
    String linkedinProfileLinkTemp = "";
    Boolean successResponse = true;
    @Value("${profileNameFontSize}")
    String profileNameFontSize;
    @Value("${companyNameFontSize}")
    String companyNameFontSize;
    @Value("${titleFontSize}")
    String titleFontSize;
    @Value("${commonSmallestFontSize}")
    String commonSmallestFontSize;
    @Value("${linkedInProfileLinkFontSize}")
    String linkedInProfileLinkFontSize;
    @Value("${ExperienceWordingFontSize}")
    String ExperienceWordingFontSize;
    public String extractExperiences(MultipartFile file) throws IOException {
        String savedFileLocation = fileManageService.getSavedFileLocation(file);
        PDDocument uploadedPdf = PDDocument.load(new File(savedFileLocation));
        Writer output = new PrintWriter(savedFileLocation.substring(0, savedFileLocation.length() - 4) + ".html", "utf-8");
        new PDFDomTree().writeText(uploadedPdf, output);
        output.close();
        uploadedPdf.close();
        File input = new File(savedFileLocation.substring(0, savedFileLocation.length() - 4) + ".html");
        Document doc = Jsoup.parse(input, "UTF-8");
        Elements allElements = doc.getAllElements();
        /**adding all elements to a Array list named allElements **/
        for (Element element : allElements) {
            listOfElements.add(element);
        }
        /** idetifying some key words with other relevant properties to break the loop for the ease of use**/
        for (int i = 0; i < listOfElements.size(); i++) {
            if(listOfElements.get(i).text().equals("Contact") && extractStyleValues(listOfElements.get(i),1).matches("21.6pt"))
                contactIndex = i;
            if(listOfElements.get(i).text().equals("Top") && extractStyleValues(listOfElements.get(i),1).matches("21.6pt"))
                topSkillsIndex = i;
            if (listOfElements.get(i).text().equals("Experience"))
                expBegins = i;
            if (listOfElements.get(i).text().equals("Education")) {
                expEnds = i;
                break;
            }
        }
        /**take the loop between contact details and start of the experience list*/
        for(int i = contactIndex;i<=expBegins;i++){
            Element element = allElements.get(i);
            Element previousElement = allElements.get(i-1);
            String style = element.attr("style");
            String[] styleValues = style.split(";");
            if (styleValues[0].startsWith("top") ) {
                if(i<topSkillsIndex){
                    if (extractStyleValues(element, 4).matches(commonSmallestFontSize) && element.text().matches("\\w*@\\w*.com"))
                        emailAddress = element.text();
                    if (extractStyleValues(element, 4).matches(linkedInProfileLinkFontSize)) {
                        if (extractStyleValues(element, 4).matches(linkedInProfileLinkFontSize) && element.text().matches("\\(LinkedIn\\)"))
                            linkedinProfileLink = linkedinProfileLinkTemp;
                        linkedinProfileLinkTemp += element.text();
                    }
                }
                if (extractStyleValues(element, 4).matches(profileNameFontSize))
                    profileName += element.text() + " ";
                if(extractStyleValues(element,4).matches(companyNameFontSize) && extractStyleValues(element,5).matches("#181818")) {
                    if (extractStyleValues(previousElement,4).matches(ExperienceWordingFontSize)){
                        currentPosition = currentPositionTemp;
                        currentPositionTemp="";
                    }
                    currentPositionTemp += element.text()+" ";
                }
                if(extractStyleValues(element,4).matches(companyNameFontSize) && extractStyleValues(element,5).matches("#b0b0b0"))
                    currentLocation += element.text() + " ";
            }
            /**removing un unnecessary last white spaces and adding extracted values for a new json object*/
            if(i==expBegins){
                profileDetails.put("profileName", removeLastWhiteSpace(profileName));
                profileDetails.put("linkedinProfileLink", removeLastWhiteSpace(linkedinProfileLink));
                profileDetails.put("emailAddress", removeLastWhiteSpace(emailAddress));
                profileDetails.put("currentPosition", removeLastWhiteSpace(currentPosition));
                profileDetails.put("currentLocation", removeLastWhiteSpace(currentLocation));
                profileName="";
                linkedinProfileLink="";
                emailAddress="";
                currentPosition="";
                currentLocation="";
                currentPositionTemp="";
            }
        }
        /**
         *  remove un wanted elements from the loop with some identified properties for avoiding getting errors while next operations and
         *  adding them to a new list called cleanList
         **/
        for (int i = expBegins + 1; i < expEnds; i++) {
            Element element = allElements.get(i);
            String style = element.attr("style");
            String[] styleValues = style.split(";");
            String styleValue = styleValues[0];
            if (styleValue.startsWith("top") && !styleValues[4].matches("font-size:9.0pt")
                    && !styleValues[1].matches("left:0.0pt") && !styleValue.matches("top:-2.621973pt")) {
                cleanListOfElements.add(element);
            }
        }
        /**
         * repeat the same pattern with font sizes on each experience and saving into lists
         **/
        int cleanListElementsArraySize = cleanListOfElements.size();
        for (int i = 1; i <cleanListElementsArraySize; i++) {
            Element element = cleanListOfElements.get(i);
            String styleFontSize = extractStyleValues(cleanListOfElements.get(i),4);
            String previousElementStyleFontSize = extractStyleValues(cleanListOfElements.get(i - 1),4);
            String fontColor = extractStyleValues(cleanListOfElements.get(i),5);
            String previousElementFontSize = extractStyleValues(cleanListOfElements.get(i-1),5);
            if (i == 1)
                company = cleanListOfElements.get(0).text();
            if (styleFontSize.matches(commonSmallestFontSize) && i == cleanListOfElements.size() - 1)
                description += element.text();
            /**compare current element font size and previous element font size and identify each experience with company name font size*/
            if ((styleFontSize.matches(companyNameFontSize) && previousElementStyleFontSize.matches(commonSmallestFontSize)) || i == cleanListOfElements.size() - 1) {
                if (location.isEmpty()) {
                    Pattern pattern = Pattern.compile("[A-Za-z]*.\\d{4}.-.(([A-Za-z]*.\\d{4})|(Present)).\\((\\d*.years?)?.\\d*.months?\\)");
                    Matcher matcher = pattern.matcher(description);
                    Boolean matchFound = matcher.find();
                    if (matchFound) {
                        timePeriod = matcher.group(0);
                        description = description.substring(timePeriod.length(), description.length() - 1);
                    }
                }
                if (location.isEmpty() && timePeriod.isEmpty()) {
                    Pattern pattern = Pattern.compile("(\\d*.years?.)?(\\d*.months?)");
                    Matcher matcher = pattern.matcher(description);
                    Boolean matchFound = matcher.find();
                    if (matchFound) {
                        timePeriod = matcher.group(0);
                        description = description.substring(timePeriod.length(), description.length() - 1);
                    }
                }
                if (!location.isEmpty() && timePeriod.isEmpty()) {
                    timePeriod = description;
                    description = "";
                }
                company = removeLastWhiteSpace(company);
                title = removeLastWhiteSpace(title);
                timePeriod = removeLastWhiteSpace(timePeriod);
                description = removeLastWhiteSpace(description);
                JSONObject jsonStringChild = new JSONObject();
                jsonStringChild.put("company", company);
                jsonStringChild.put("title", title);
                jsonStringChild.put("timePeriod", jsonStringService.getLongDuration(timePeriod));
                jsonStringChild.put("description", description);
                positionsList.add(jsonStringChild);
                /**identifying the pattern with only having service duration, which having several positions company details*/
                if (hasOnlyServiceDuration) {
                    JSONObject totalTimePeriod = new JSONObject();
                    totalTimePeriod.put("duration", jsonStringService.getShortDuration(timePeriodWithMorePositions,true));
                    totalTimePeriod.put("starting",positionsList.get(positionsList.size()-1).getJSONObject("timePeriod").getJSONObject("starting"));
                    totalTimePeriod.put("ending",positionsList.get(0).getJSONObject("timePeriod").getJSONObject("ending"));
                    JSONObject jsonStringParent = new JSONObject();
                    jsonStringParent.put("company", companyWithMorePositions);
                    jsonStringParent.put("timePeriod", totalTimePeriod);
                    jsonStringParent.put("positions", positionsList);
                    experienceList.add(jsonStringParent);
                    hasOnlyServiceDuration = false;
                    companyWithMorePositions = "";
                    timePeriodWithMorePositions = "";
                    positionsList.clear();
                } else {
                    JSONObject jsonStringParent = new JSONObject();
                    jsonStringParent.put("company", company);
                    jsonStringParent.put("timePeriod", jsonStringService.getLongDuration(timePeriod));
                    jsonStringParent.put("positions", positionsList);
                    experienceList.add(jsonStringParent);
                    positionsList.clear();
                }
                company = "";
                title = "";
                description = "";
                timePeriod = "";
                location = "";
            }
            /**identifying the pattern with only having service duration, which having several positions company details*/
            if (styleFontSize.matches(titleFontSize) && previousElementStyleFontSize.matches(commonSmallestFontSize) && title.isEmpty() && timePeriod.isEmpty()) {
                companyWithMorePositions = company;
                timePeriodWithMorePositions = description;
                description = "";
                hasOnlyServiceDuration = true;
            }
            /**identify the position from several positions under one company name*/
            if (styleFontSize.matches(titleFontSize) && previousElementStyleFontSize.matches(commonSmallestFontSize) && !title.isEmpty()) {
                if (location.isEmpty()) {
                    Pattern pattern = Pattern.compile("[A-Za-z]*.\\d{4}.-.(([A-Za-z]*.\\d{4})|(Present)).\\((\\d*.year)?.\\d*.months?\\)");
                    Matcher matcher = pattern.matcher(description);
                    Boolean matchFound = matcher.find();
                    if (matchFound) {
                        timePeriod = matcher.group(0);
                        description = description.substring(timePeriod.length(), description.length() - 1);
                    }
                }
                if (location.isEmpty() && timePeriod.isEmpty()) {
                    Pattern pattern = Pattern.compile("(\\d*.years?.)?(\\d*.months?)");
                    Matcher matcher = pattern.matcher(description);
                    Boolean matchFound = matcher.find();
                    if (matchFound) {
                        timePeriod = matcher.group(0);
                        description = description.substring(timePeriod.length(), description.length() - 1);
                    }
                }
                if (!location.isEmpty() && timePeriod.isEmpty()) {
                    timePeriod = description;
                    description = "";
                }
                JSONObject jsonStringChild = new JSONObject();
                jsonStringChild.put("company", removeLastWhiteSpace(company));
                jsonStringChild.put("title", removeLastWhiteSpace(title));
                jsonStringChild.put("timePeriod", jsonStringService.getLongDuration(removeLastWhiteSpace(timePeriod)));
                jsonStringChild.put("description", removeLastWhiteSpace(description));
                positionsList.add(jsonStringChild);
                title = "";
                description = "";
                timePeriod = "";
                location = "";
            }
            if (styleFontSize.matches(companyNameFontSize)) {
                company += element.text() + " ";
            }
            if (styleFontSize.matches(titleFontSize)) {
                title += element.text() + " ";
            }
            if (styleFontSize.matches(commonSmallestFontSize) && fontColor.matches("#b0b0b0")) {
                location += location + " ";
            }
            if (styleFontSize.matches(commonSmallestFontSize) && fontColor.matches("#181818")) {
                if (previousElementFontSize.matches("#b0b0b0")) {
                    timePeriod = description;
                    description = "";
                }
                description += element.text() + " ";
            }
        }
        /**discard saved pdf and html, creating the final response with profile and experience objects in data object*/
        fileManageService.discardFiles(savedFileLocation);
        fileManageService.discardFiles(savedFileLocation.substring(0, savedFileLocation.length() - 4) + ".html");
        jsonData.put("profile", profileDetails);
        jsonData.put("experiences", experienceList);
        finalJsonOutput.put("success", successResponse);
        finalJsonOutput.put("data", jsonData);
        listOfElements.clear();
        cleanListOfElements.clear();
        experienceList.clear();
        positionsList.clear();
        return finalJsonOutput.toString();
    }
    /**get the style property and split that from ";" and get the style attribute value*/
    public String extractStyleValues(Element element, int indexOfAttribute){
        String style = element.attr("style");
        String[] styleValues = style.split(";");
        String styleValue = styleValues[indexOfAttribute];
        String[] keyAndValuePair = styleValue.split(":");
        return keyAndValuePair[1];
    }
    /**remove unnecessary white spaces in the end of strings*/
    public String removeLastWhiteSpace(String string){
        if (string.length() != 0 && string.charAt(string.length() - 1) == ' ')
            string = string.substring(0, string.length() - 1);
        return string;
    }
}