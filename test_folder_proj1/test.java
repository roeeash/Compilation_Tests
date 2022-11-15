import java.io.*; 
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class test{


static Scanner in = new Scanner(System.in);


	// run make command

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
	public static void make(String path){
	try {
		File f = new File(path);
		runCommand(f,"make");
		
	}
		
	catch(Exception e){
	System.out.println("Make was unsuccessful");
	
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
	
	
	public static void test_generic(String root_user,String input_file)  throws IOException{

	String path = "/home/" + root_user+"/Desktop/compilation/ex1/input/Input.txt";
	FileReader fr = new FileReader(input_file);
	PrintWriter writer = new PrintWriter(path);
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

	boolean passed_one = false, passed_two = false, passed_three = false;


	System.out.println("enter name of root user:");

	String root_user = in.next();


	/** TRYING TEST1 **/
	//1.TEST
	try {
		test_generic(root_user,"./input/input1.txt");
	
	}

	catch (IOException e){
	}

	//2. RUN MAKE COMMAND

	System.out.println("enter path for make command:");

	String path = in.next();
	
	make(path);
	

	//3. COMPARE TEST 1 

	try {
		
		Path path1 = Paths.get("/home/"+ root_user+"/Desktop/Compilation_Tests/test_folder_proj1/output/output1.txt");
		Path path2 = Paths.get("/home/"+ root_user+"/Desktop/compilation/ex1/output/OutputTokens.txt");
		long diff = filesCompareByLine(path1,path2);
		
		if(diff == -1){
			System.out.println("Files match");
			passed_one = true;
		}
		else
			System.out.println("Files do not match, difference at line  "+diff);
	
	}

	catch (IOException e){
		System.out.println("Could not compare files");
	}

	/** Sleep**/

	try{

	TimeUnit.SECONDS.sleep(1);
	}
	
	catch(InterruptedException e){
	}







	/** TRYING TEST2 **/
	//1.TEST
	try {
		test_generic(root_user,"./input/input2.txt");
	
	}

	catch (IOException e){
	}

	//2. RUN MAKE COMMAND

	
	make(path);
	

	//3. COMPARE TEST 2

	try {
		
		Path path1 = Paths.get("/home/"+ root_user+"/Desktop/Compilation_Tests/test_folder_proj1/output/output2.txt");
		Path path2 = Paths.get("/home/"+ root_user+"/Desktop/compilation/ex1/output/OutputTokens.txt");
		long diff = filesCompareByLine(path1,path2);
		
		if(diff == -1){
			System.out.println("Files match");
			passed_two = true;
		}
		else
			System.out.println("Files do not match, difference at line  "+diff);
	
	}

	catch (IOException e){
		System.out.println("Could not compare files");
	}


	/** Sleep**/

	try{

	TimeUnit.SECONDS.sleep(1);
	}
	
	catch(InterruptedException e){
	}



	/** TRYING TEST3 **/
	//1.TEST
	try {
		test_generic(root_user,"./input/input3.txt");
	}

	catch (IOException e){
	}

	//2. RUN MAKE COMMAND

	
	make(path);
	

	//3. COMPARE TEST 2

	try {
		
		Path path1 = Paths.get("/home/"+ root_user+"/Desktop/Compilation_Tests/test_folder_proj1/output/output3.txt");
		Path path2 = Paths.get("/home/"+ root_user+"/Desktop/compilation/ex1/output/OutputTokens.txt");
		long diff = filesCompareByLine(path1,path2);
		
		if(diff == -1){
			System.out.println("Files match");
			passed_three = true;
		}
		else
			System.out.println("Files do not match, difference at line  "+diff);
	
	}

	catch (IOException e){
		System.out.println("Could not compare files");
	}


	

	
	if(passed_one)
		System.out.println("Passed test one!");
	if(passed_two)
		System.out.println("Passed test two!");
	if(passed_three)
		System.out.println("Passed test three!");

	if(passed_one && passed_two && passed_three)
		System.out.println("Passed all the tests! Well done!");
	
		}

}

