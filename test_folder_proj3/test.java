import java.io.*; 
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class test{


static Scanner in = new Scanner(System.in);


	// run commands in shell

    public static void runCommand(File whereToRun, String command) throws Exception {
        System.out.println("Running in: " + whereToRun);
        System.out.println("Command: " + command);
        String[] commands;

        
         commands = new String[] { "sh", "-c", command };
        
        
        Process process =  Runtime.getRuntime().exec(commands, null, whereToRun);

        OutputStream outputStream = process.getOutputStream();
        InputStream inputStream = process.getInputStream();
        InputStream errorStream = process.getErrorStream();

        printStream(inputStream);
        printStream(errorStream);

        boolean isFinished = process.waitFor(30, TimeUnit.SECONDS);
        outputStream.flush();
        outputStream.close();

        if(!isFinished) {
            process.destroyForcibly();
        }
    }

	//print the output stream

    private static void printStream(InputStream inputStream) throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

        }
    }


	//run make command
	public static void runShellCommand(String path, String command){
	try {
		File f = new File(path);
		runCommand(f,command);
		
	}
		
	catch(Exception e){
	System.out.println("Command "+command+" was unsuccessful");
	
	}
	}

	// compare line by line 

	public static long filesCompareByLine(Path path1, Path path2) throws IOException {
    	try (BufferedReader bf1 = Files.newBufferedReader(path1);
         BufferedReader bf2 = Files.newBufferedReader(path2)) {
        
        long lineNumber = 1;
        String line1 = "", line2 = "";
        while ((line1 = bf1.readLine()) != null) {
            line2 = bf2.readLine();
            if (line2 == null || !line1.equals(line2)) {
                return lineNumber;
            }
            lineNumber++;
        }
        if (bf2.readLine() == null) {
            return -1;
        }
        else {
            return lineNumber;
        }
    }
}






	/**** TESTS *****/


	
	/** GENERIC TEST FUNCTION **/ 
	
	
	public static void test_generic(String parent_folder,boolean input, String input_file)  throws IOException{

	String path_for_write = parent_folder+((input)?"/ex3/input/Input.txt" :"/ex3/Makefile") ;
	FileReader fr = new FileReader(input_file);
	PrintWriter writer = new PrintWriter(path_for_write);
	writer.print("");
	writer.flush();
 	// whole content of file is to be stored
            String str = "";
 
            int i;
 
           
            while ((i = fr.read()) != -1) {
 
                str += (char)i;
            }
 
 
            // Writing above string data to
            // FileWriter object
	writer.write(str);                                                   
                         writer.flush();  
        writer.close();
	}

	

	
	



	public static void main(String[]args){
	//variables
	int semantPassedCount = 0;
	int otherPassedCount = 0;
	int semantTests = 1;
	int otherTests = 2;
	int testNum = 3;

	try{
	PrintWriter output_writer = new PrintWriter("../../Compilation_Tests/test_folder_proj3/most_recent_output.txt");
	test_generic("../../",false,"./input/Makefile.txt");
	

	for(int i =1; i<=testNum; i++) {


	/** TRYING TEST1 **/
	//1.TEST
	try {
		test_generic("../../",true,"./input/input"+i+".txt");
	
	}

	catch (IOException e){
	}

	//2. RUN MAKE COMMAND

	
	
	//run jar file

	runShellCommand("../../ex3/", "make");
	

	//3. COMPARE TEST 1 

	try {
		
		Path path1 = Paths.get("../../Compilation_Tests/test_folder_proj3/output/output"+i+".txt");
		Path path2 = Paths.get("../../ex3/output/SemanticStatus.txt");
		long diff = filesCompareByLine(path1,path2);
		
		if(diff == -1){
			System.out.println("Files match");
			if(i<=semantTests) semantPassedCount++;
			else otherPassedCount++;
			System.out.println("Passed test " +i+"!");
			output_writer.write("Passed test " +i+"!\n");
		}
		else{
			System.out.println("Files do not match, difference at line  "+diff);
			output_writer.write("Files do not match, difference at line  "+diff+" in test " +i+"!\n");
		}	
	
	}

	catch (IOException e){
		System.out.println("Could not compare files");
		output_writer.write("Could not compare files in test" + i+"\n");
	}

	/** Sleep**/

	try{

	TimeUnit.SECONDS.sleep(1);
	}
	
	catch(InterruptedException e){
	}

	}//end for



	System.out.println("Passed tests for Semantic related issues: "+semantPassedCount+ " out of "+semantTests);
	output_writer.write("Passed tests for Semantic related issues: "+semantPassedCount+ " out of "+semantTests +"\n");

	System.out.println("Passed tests for Other issues: "+otherPassedCount+ " out of "+otherTests);
	output_writer.write("Passed tests for Other issues: "+otherPassedCount+  " out of "+otherTests +"\n");

	

	if(semantPassedCount + otherPassedCount == testNum){
		System.out.println("Passed all the tests! Well done!");
		output_writer.write("Passed all the tests! Well done!\n");
	
	}

	output_writer.flush();  
        output_writer.close();

	}//close - try

	catch (IOException e){
	}

	
	
	}// end- main

}//end- class
