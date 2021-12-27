import java.util.ArrayList;
import java.io.File;  // Import the File class
import java.io.FileReader;  // Import this class to handle errors
import java.io.BufferedReader; 
import java.io.IOException; // Import the Scanner class to read text files
import java.util.Collections; //place with imports
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry; 
import java.util.Properties;
import java.util.Map; //place with imports
import java.util.Collections; //place with imports


//citation: https://www.w3schools.com/java/java_files_read.asp
public class Driver {
  private static void readTwitterData (ArrayList<Sentence> tweets){
        try {
      File myObj = new File("testdata.manual.2009.06.14.csv");
      BufferedReader myReader = new BufferedReader(new FileReader(myObj));
      String line = null;
      //revised code part 1
      ArrayList<Sentence> data = new ArrayList<>(); 
      
      while ((line = myReader.readLine()) != null){
        String a = line.strip();
        Sentence b =  Sentence.convertLine(a);
        tweets.add(b);
        
      }
      myReader.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
   
      ArrayList<Sentence> data = new ArrayList<Sentence>(); 
      readTwitterData(data);
      HashMap<String, Integer> map = new HashMap<>();
       
       for (int i = 0; i < data.size(); i++){
          Sentence sent = data.get(i); 
          
          ArrayList <String> words1 = sent.splitSentence(); 
          
          for (int j = 0; j < words1.size(); j++){
            String word = words1.get(j);
            
            if (map != null && map.containsKey(word)){
                //get the value of the specified key
              map.put(word, map.get(word) + 1);
              //if the map contains no mapping for the key,
               //map the key with a value of 1      
        }
              else {
              map.put(word, 1);
        }
            } 
          }
for (int x = 0; x < data.size(); x++){
  System.out.println("" + data.get(x).getText() + "" + data.get(x).getSentiment());
    }

    
  //loops through hashmap and prints out all entries
Map.Entry<String, Integer> maxEntry = null;
for (Map.Entry<String, Integer> entry : map.entrySet()){

    if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0){
        maxEntry = entry;
    }
}
int maxValueLen = maxEntry.getValue().toString().length();
ArrayList <String> results = new ArrayList<String>();
for (Map.Entry set : map.entrySet()){
    String value = set.getValue().toString();
    while(value.length() < maxValueLen){
        value = " " + value;
    results.add(value + " of " + set.getKey());
    }
  
}
Collections.sort(results);
Collections.reverse(results);
for (int i = 0; i < 100; i++){
    System.out.println(results.get(i));
}

String filter = "June 1 2010-June 30 2010";
ArrayList <Sentence> filteredTweets = new ArrayList<>();
for (int i = 0; i < data.size(); i++){
   if (data.get(i).keep(filter)){
      filteredTweets.add(data.get(i));

   }
}
}
}

//After filtering the Twitter data set, we can see some of the most commonly used during 
//the month of June in 2010. Among the top words were san, francisco, watching, iphone, and work. By logically combining the words
//"san" and "francisco"(each occurring 9 times within the string filter for the month of June), we researched major events occurring in San Francisco in 2010. We discovered that there was a major
//general election in San Francisco at this time, where property taxes, healthcare plans, and transportation issues caused 
//some contentious debate. "iPhone" and "phone" were other significantly used words in the dataset (used 9 and 8 times in June, respectively),
// and it turns out that on June 4th, 2010, the iPhone 4 was released. This project was really interesting because it gave us the 
//coding skills to tell a computer how to do something that our brains tend to do naturally. Using this code, we can analyze discussion
//trends and draw our own conclusions based on the filtered data. 
