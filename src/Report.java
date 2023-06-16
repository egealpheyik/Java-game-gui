import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Report {
	Child child;
    public Report(Child child) {
		super();
		this.child = child;
	}
	public String createReport() {
		
        String txtFilePath = "child/" + child.getUsername() + "_" + child.getPassword();
        String csvFilePath =  "Raporlar/" + child.getUsername() + "_" + child.getPassword() + ".csv";
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Finished or not");
        headers.add("seconds");
        headers.add("from");
        headers.add("to");
        headers.add("number of questions");
        headers.add("number of correct answers");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(txtFilePath));
            BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath));

            for (String header : headers) {
                writer.write(header + ";");
            }
            writer.newLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                StringBuilder csvLine = new StringBuilder();

                csvLine.append(data[0]).append(";").append(data[1]).append(";").append(data[2]).append(";")
                        .append(data[3]).append(";").append(data[4]).append(";").append(data[5]);

                writer.write(csvLine.toString());
                writer.newLine();
                line = "\n";
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return child.getUsername() + "_" + child.getPassword() + ".csv file created";
        }
}
