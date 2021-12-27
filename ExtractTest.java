public class ExtractTest{

	public static void main(String[] args){

		Sentence expectedResult1 = new Sentence("Jquery is my new best friend", "drkinga", "May 11 2009");
		Sentence actualResult1 = Sentence.convertLine("\"4\",\"10\",\"Mon May 11 03:26:10 UTC 2009\",\"jquery\",\"dcostalis\",\"Jquery is, my new best friend.\"");
		System.out.println("Authors?" + expectedResult1.getText().equals(actualResult1.getText()));

		Sentence expectedResult2 = new Sentence("Jquery is my new best friend", "drkinga", "May 11 2009");
		Sentence actualResult2 = Sentence.convertLine("\"4\",\"10\",\"Mon May 11 03:26:10 UTC 2009\",\"jquery\",\"dcostalis\",\"Jquery is, my new best friend.\"");
		System.out.println("Date?" + expectedResult2.getTimeStamp().equals(actualResult2.getTimeStamp()));
	}
}