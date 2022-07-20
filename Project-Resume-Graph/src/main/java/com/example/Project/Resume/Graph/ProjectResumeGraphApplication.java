package com.example.Project.Resume.Graph;




import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

import java.util.ArrayList;

@SpringBootApplication
public class ProjectResumeGraphApplication {

	public static void main(String[] args) throws Exception {

		SpringApplication.run(ProjectResumeGraphApplication.class, args);

		File input = new File("G:\\professional\\NIMI\\Project Resume Graph\\malith gammanpila.html");
		Document doc = Jsoup.parse(input, "UTF-8");



		Elements allElements = doc.getAllElements();

		int expBegins = 0;
		int expEnds = 0;

		ArrayList<Element> listOfElements = new ArrayList<>();

		ArrayList<Element> cleanListOfElements = new ArrayList<>();


		ArrayList<Element> companyName = new ArrayList<>();
		ArrayList<Element> title = new ArrayList<>();
		ArrayList<Element> description = new ArrayList<>();



		ArrayList<ArrayList<Element>> experienceListArray = new ArrayList<ArrayList<Element>>();



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

			if (styleValue.startsWith("top") && !styleValues[4].matches("font-size:9.0pt")) {

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
				companyName.add(cleanListOfElements.get(0));

			if (styleIntigerValue1==10 && fontColor!="#b0b0b0" && i==cleanListOfElements.size()-1){
				description.add(element);

			}

			if((styleIntigerValue1 ==12 && styleIntigerValue2 ==10) || i==cleanListOfElements.size()-1){

				ArrayList<Element> experienceList = new ArrayList<>();

				experienceList.addAll(companyName);
				experienceList.addAll(title);

				experienceList.addAll(description);



				experienceListArray.add(experienceList);

				companyName.clear();
				title.clear();
				description.clear();

			}



			if(i==cleanListOfElements.size()-1)
				description.add(cleanListOfElements.get(cleanListOfElements.size()-1));

			if (styleIntigerValue1==12 ){
				companyName.add(element);
			}

			if (styleIntigerValue1==11){
				title.add(element);
			}

			if (styleIntigerValue1==10 && fontColor.matches("color:#181818")){
				description.add(element);
			}


		}


		for(int i=0; i<experienceListArray.size();i++){

			System.out.println();
			System.out.println("Experience "+(i+1));
			System.out.println();

			for(int j=0;j<experienceListArray.get(i).size();j++){

				System.out.println(experienceListArray.get(i).get(j).text());

			}
		}

	}

}



