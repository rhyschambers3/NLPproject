import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.ejml.simple.SimpleMatrix;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;


public class Sentence {
  private String text; 
  private String author; 
  private String timeStamp;
  
  public Sentence (){
    
  }
  public Sentence (String text, String author, String timestamp){
    timeStamp = timestamp;
    this.text = text;
    this.author = author;
  }

    public String getText(){
      return text; 
  }
    public void setText(String a){
      text = a;  
  }
    public String getAuthor(){
      return author;
  }
   public void setAuthor(String b){
      author = b; 
  }
   public String getTimeStamp(){
      return timeStamp; 
   }
   public void setTimeStamp(String c){
      timeStamp = c; ;
   }

  public String toString(){
    return "author: " + author + ", " + "sentence: " + text + ", " + "Time Stamp: " + timeStamp; 
  }

  public static Sentence convertLine (String line){
    
    String[] arrOfStr = line.split("\",\"");

    String timeStamp = changeDate(arrOfStr[2]); 
    String Author = arrOfStr[4]; 
    String Text = removePunctuation(arrOfStr[5]); 

    Sentence newLine = new Sentence(Text, Author, timeStamp); 

    return newLine;
  }

  private static String changeDate(String timeStamp){

    String[] date = timeStamp.split(" "); 
    return date[1] + " " + date[2] + " " + date[5].replaceAll("\"",""); 
  }

  private static String removePunctuation(String line){
    return line.replaceAll("\\p{Punct}",""); 
  }

  public ArrayList<String> splitSentence(){
      String [] words1 = text.split (" ");
      List<String> list = Arrays.asList(words1);
      ArrayList<String> words = new ArrayList<String>(); 

      String[] stopwords = {"a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can't", "cannot", "could", "couldn't", "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "few", "for", "from", "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its", "itself", "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "or", "other", "ought", "our", "ours ourselves", "out", "over", "own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn't", "we", "we'd", "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves"}; //from https://www.ranks.nl/stopwords
        ArrayList<String> a = new ArrayList <>(Arrays.asList(stopwords));
        for (int i = 0; i < words1.length; i++) {
            String lowerWords = words1[i].toLowerCase();
           
            //for (int c = 0; c < stopwords.length; c++){
              if (!a.contains(lowerWords)) { 
                words.add(lowerWords);
              }

        }
      
      return words;
  }
   public int getSentiment(){
       Properties props = new Properties();
      props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
       StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
      Annotation annotation = pipeline.process(text);
     CoreMap sentence = annotation.get(CoreAnnotations.SentencesAnnotation.class).get(0);
     Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
     return RNNCoreAnnotations.getPredictedClass(tree);
  }
 
 private static String formatDate(String timeStamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // https://stackoverflow.com/a/19115247
            Date date = sdf.parse(timeStamp);
            SimpleDateFormat newDate = new SimpleDateFormat("MMM dd yyyy");
            newDate.setTimeZone(TimeZone.getTimeZone("UTC")); // https://stackoverflow.com/a/19115247
            String formattedDate = newDate.format(date);

            return formattedDate;
        } catch (ParseException e) { // https://stackoverflow.com/a/28960155
            e.printStackTrace();
            return timeStamp;
        }
        
   }
   
public boolean keep(String temporalRange)
{ 
   try {
      String[] dates = temporalRange.split("-");
      
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy",Locale.ENGLISH);
    sdf.setTimeZone(TimeZone.getTimeZone("UTC")); //https.stackoverflow.com/a/19115247
    Date dateStart = sdf.parse(dates[0]);
    Date dateEnd = sdf.parse(dates[1]);
    Date dateTweet = sdf.parse(timeStamp);
    if(!dateTweet.after(dateEnd) && !dateTweet.before(dateStart))
    {
      return true;
    }
    else
    {
      return false;
    }
   
   } catch(ParseException e) { //this generic but you can control another types of exception
    // look the origin of excption 
    e.printStackTrace();
    return false;
   }

}

 


public int getMonth (String month){
   if (month.equals ("Jan")){
      return 1;
   }
   if (month.equals ("Feb")){
      return 2;
   }
   if (month.equals ("Mar")){
      return 3;
   }
   if (month.equals ("Apr")){
      return 4;
   }
   if (month.equals ("May")){
      return 5;
   }
   if (month.equals ("Jun")){
      return 6;
   }
   if (month.equals ("Jul")){
      return 7;
   }
   if (month.equals ("Aug")){
      return 8;
   }
   if (month.equals ("Sep")){
      return 9;
   }
   if (month.equals ("Oct")){
      return 10;
   }
   if (month.equals ("Nov")){
      return 11;
   }
   if (month.equals ("Dec")){ 
      return 12;
   }
   return 0;
}

}

