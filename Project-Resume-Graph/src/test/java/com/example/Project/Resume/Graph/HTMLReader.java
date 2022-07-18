package com.example.Project.Resume.Graph;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class HTMLReader {

 File input = new File("G:\\professional\\NIMI\\Project Resume Graph\\malith gammanpila.html");
 Document doc = Jsoup.parse(input, "UTF-8");

 public HTMLReader() throws Exception {
 }

 @Test
 public void readHTML()  {

  //    File file = new File("G:\\professional\\NIMI\\Project Resume Graph\\malith gammanpila.html");
//    Document document = Jsoup.parse(file);
//    Element link = document.select("a").first();

//     String html = "<html><head><title>Sample Title</title></head>"
//             + "<body>"
//             + "<p>Sample Content</p>"
//             + "<div id='sampleDiv'><a href='www.google.com'>Google</a>"
//             + "<h3><a>Sample</a><h3>"
//             +"</div>"
//             +"</body></html>";
//     Document document = Jsoup.parse(html);
//
//
//     Element link = document.getElementById("sampleDiv").select("h3").first().select("a").first();
//
//     System.out.println("Outer HTML: " + link.outerHtml());
//     System.out.println("Inner HTML: " + link.html());




  String idStart = getSpecificElementwithSpecificAttribute2("style", "font-size:15.75pt", 1, "id") ;
  String idEnd = getSpecificElementwithSpecificAttribute2("style", "font-size:15.75pt", 2, "id");

  int startingId = getIntigerValue(idStart);
  int endingId = getIntigerValue(idEnd);

//  System.out.println(startingId +" "+ endingId);

  Elements elems = getSpecificElements("Style","font-size:15.75pt");
  Elements elems2 = getSpecificElements("Style","font-size:12.0pt");
  Elements elems3 = getSpecificElements("Style","font-size:11.5pt");
  Elements elems4 = getSpecificElements("Style","font-size:10.5pt");

  Element experiencesBegin = doc.getElementsByAttributeValueMatching("style", "font-size:15.75pt" ).get(1);
  Element experienceEnds = doc.getElementsByAttributeValueMatching("style","font-size:15.75pt").get(2);



  Elements allElemsz = doc.select("div");
  String str  = allElemsz.attr("style");
  System.out.println(str);








//  Elements elementList = allElems.sub

//  Element eel = experiencesBegin.nextElementSibling();
//
//  System.out.println(eel.text());

//  int staringIndex = 0;
//  int endingIndex = 0;

  ArrayList<Element> allDivElements = new ArrayList<>();

  for(Element element2 : allElemsz ) {

   allDivElements.add(element2);

    }

  for (Element elementt : allDivElements){
   if(elementt.text().equals("Experience")){
    System.out.println(elementt.siblingIndex());
   }
   if(elementt.text().equals("Education")){
    System.out.println(elementt.siblingIndex());
    break;
   }

//   System.out.println(elementt.text());




  }



 }

//  String experienceList = StringUtils.substringBetween(doc.text(), "Experience", "Education");


//
//  List<Element> elemList= elems4.stream().collect(Collectors.toList());
//
//  for(Element elee: elemList){
//   System.out.print(elee.text()+" ");
//  }



//  System.out.println(experienceList);


//  System.out.println(experiencesBegin.nextElementSiblings().before(""));

//  Elements eles = doc.select("div[style$=font-size/:15/.75pt;]");
//  for (Element elem : eles) {
//   System.out.println( elem.text());
//  }







//  elems.forEach(el -> System.out.println("section: " + el));






//  while (!doc.getElementsByAttributeValueMatching("style","font-size:12.0pt").isEmpty()){

//  for (int i = 0; i < 20; i++)
//
//   if ( doc.getElementsByAttributeValueMatching("style", "font-size:15.75pt").get(2);
//  String idEnd = elementEnd.attr("id") >= idStart){
//
//
//   String s = doc.getElementsByAttributeValueMatching("style", "font-size:12.0pt").get(i).html();
//
//   System.out.println(s);
//  }
//
// }

//   if (doc.getElementsByAttributeValueMatching("style", "font-size:12.0pt").get(i).attr("id")+1 ==
//           doc.getElementsByAttributeValueMatching("style", "font-size:12.0pt").get(i+1).attr("id")){
//
//    System.out.println("hello");
//
//   }


//   for(int i =0 ; i<2; i++) {
//    if (doc.getElementsByAttributeValueMatching("style", "font-size:12.0pt").get(i) ==
//            doc.getElementsByAttributeValueMatching("style", "font-size:12.0pt").get(i + 1)){
//     System.out.println("he");
//    }
//
//   }
//
//  }


//  Element ele3 = doc.getElementsByAttributeValueMatching("style","font-size:11.5pt").get(1);
//  Element ele4 = doc.getElementsByAttributeValueMatching("style","font-size:10.5pt").get(1);

//  System.out.println(idStart + idEnd);




 public int getIntigerValue(String idString) {
  Pattern pattern = Pattern.compile("[0-9]+");
  Matcher matcher = pattern.matcher(idString);
  matcher.find();
  int integerValue = Integer.parseInt(matcher.group());
  return integerValue;
 }

 public Element getSpecificElement(String attribute, String regex , int index) {

 Element specificElement = doc.getElementsByAttributeValueMatching(attribute, regex ).get(index);
 return specificElement;
}

 public Elements getSpecificElements(String attribute, String regex) {

  Elements specificElement = doc.getElementsByAttributeValueMatching(attribute, regex );
  return specificElement;
 }

 public String getSpecificElementwithSpecificAttribute2(String attribute, String regex , int index, String secondAttribute) {

  String specificElement = doc.getElementsByAttributeValueMatching(attribute, regex ).get(index).attr(secondAttribute);
  return specificElement;
 }


 public String getSpecificElementContent (String attribute, String regex , int index) {

  String specificElementContent = doc.getElementsByAttributeValueMatching(attribute, regex).get(index).html();
  return specificElementContent;
 }

}
