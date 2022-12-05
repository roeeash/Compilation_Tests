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
	System.out.println("Command"+command+" was unsuccessful");
	
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
	
	
	public static void test_generic(String parent_folder,String input_file)  throws IOException{

	String path_for_write = parent_folder+"/ex2/input/Input.txt";
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

	int parse_passed_count = 0;
	int other_passed_count = 0;
	try{
	PrintWriter output_writer = new PrintWriter("../../Compilation_Tests/test_folder_proj2/most_recent_output.txt");
	
	

	for(int i =1; i<=8; i++) {


	/** TRYING TEST1 **/
	//1.TEST
	try {
		test_generic("../../","./input/input"+i+".txt");
	
	}

	catch (IOException e){
	}

	//2. RUN MAKE COMMAND

	
	
	//run jar file

	runShellCommand("../../ex2/", "make");
	

	//3. COMPARE TEST 1 

	try {
		
		Path path1 = Paths.get("../../Compilation_Tests/test_folder_proj2/output/output"+i+".txt");
		Path path2 = Paths.get("../../ex2/output/ParseStatus.txt");
		long diff = filesCompareByLine(path1,path2);
		
		if(diff == -1){
			System.out.println("Files match");
			if(i<=7) parse_passed_count++;
			else other_passed_count++;
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



	System.out.println("Passed tests for Parse Errors: "+parse_passed_count+ " out of 7");
	output_writer.write("Passed tests for Parse Errors: "+parse_passed_count+ " out of 7\n");

	System.out.println("Passed tests for Other Errors: "+other_passed_count+ " out of 1");
	output_writer.write("Passed tests for Other Errors: "+other_passed_count+ " out of 1\n");

	

	if(parse_passed_count + other_passed_count == 8)
		System.out.println("Passed all the tests! Well done!");
		output_writer.write("Passed all the tests! Well done!\n");


	output_writer.flush();  
        output_writer.close();
	}

	catch (IOException e){
	}

	
	
	}// end- main

}//end- class
