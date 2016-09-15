package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileData
{
	private static final String fileName = System.getProperty("user.dir")+"\\data.appdata";
	
	public static void updateFile()
	{
		try {
	 		File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
	
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(String str: Main.pins) {
				bw.write(str+"\n");
			}
			bw.close();


		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public static void removeFromFile(int index)
	{
		Main.pins.remove(index);
		updateFile();
	}
	
	public static void addToFile(String x) throws IOException
	{
		Main.pins.add(x);
		String split[] = x.split(":");
		Main.addPinItem(split[0], split[1], Main.spt.getChildren().size());
		updateFile();
	 	
	 } 
	 
	 private static void readFile() throws IOException 
	 {
		 try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			 String line;
			 int i = 0;
			 while ((line = br.readLine()) != null) {
				 String split[] = line.split(":");
				 Main.pins.add(line);
				 Main.addPinItem(split[0],split[1],i);
				 i++;
			 }
			 if(Main.pins.size() < 1){
				 Main.addNoPinsLabel();
			 }
		 }
	 }
	 
	 public static void initFile()
	 {
		 File file = new File(fileName);
		 if(!file.exists()){
			 try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }else{
			 try {
				readFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
	 }
	 
}