package com.example.Project.Resume.Graph;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class test2 {

    File input = new File("G:\\professional\\NIMI\\Project Resume Graph\\malith gammanpila.html");
    Document doc = Jsoup.parse(input, "UTF-8");

    public test2() throws IOException {
    }

    @Test
    public void testing(){

        Elements allElements = doc.getAllElements();

        int expBegins = 0;
        int expEnds = 0;

        ArrayList <Element> listOfElements = new ArrayList<>();

        ArrayList <Element> cleanListOfElements = new ArrayList<>();

        ArrayList<Element> oneExperience = new ArrayList<>();

        ArrayList<Integer> indexesOfBreakings = new ArrayList<>();

        /**adding all elements to a Array list named allElements **/

        for (Element element : allElements){

            listOfElements.add(element);
//            System.out.println(element.attr("style"));

        }

        /** idetifying the required elements by capturing Experience and Education keyword as the index number of the arrayList of allElements ,
         *
         * decaring the index numbers to expBeging and expEnds **/

        for(int i=0;i<listOfElements.size();i++){
            if(listOfElements.get(i).text().equals("Experience"))
                expBegins = i;
            if(listOfElements.get(i).text().equals("Education")) {
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

        for(int i = expBegins+1 ; i<expEnds; i++){

            Element element = allElements.get(i);

//            System.out.println(element.text());
//            System.out.println(element);

            String style = element.attr("style");
            String[] styleValues = style.split(";");
            String styleValue = styleValues[0];

            if(styleValue.startsWith("top")&& !styleValues[4].matches("font-size:9.0pt")){

                cleanListOfElements.add(element);

                }
            }

        /**
         * seperating the captured content into experiences one by one
         **/

        for(int i=0; i<cleanListOfElements.size()-1;i++){

            Element element = cleanListOfElements.get(i);
            Element nextElement = cleanListOfElements.get(i+1);

            String style = element.attr("style");
            String nextStyle = nextElement.attr("style");

            String[] styleValues = style.split(";");
            String[] nextStyleValues = nextStyle.split(";");

            String styleValue = styleValues[4];
            String nextStyleValue = nextStyleValues[4];

            int styleIntigerValue1 = Integer.parseInt(styleValue.substring(10,12));
            int styleIntigerValue2 = Integer.parseInt(nextStyleValue.substring(10,12));

            oneExperience.add(cleanListOfElements.get(i));

            if(styleIntigerValue1<styleIntigerValue2){
                indexesOfBreakings.add(i);
//                break;
            }

        }
//        System.out.println(oneExperience.toString());

        System.out.println(indexesOfBreakings.toString());

        for(int i= ;;){

        }





//        for(Element element : listOfElements){
//
//
//            if(!element.getElementsMatchingText("Experience").isEmpty()){
//                System.out.println(element.elementSiblingIndex());
//            }



//        }

    }




}
