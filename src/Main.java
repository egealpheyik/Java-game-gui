import java.io.File;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		File folder = new File("child");
		File reportFolderFile = new File("Raporlar");
    	folder.mkdir(); reportFolderFile.mkdir();
    	
		LoginPage loginPage = new LoginPage();
		
	}

}
