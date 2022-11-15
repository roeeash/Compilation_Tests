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

 
	public static void test1(String root_user)  throws IOException{

	String path = "/home/" + root_user+"/Desktop/compilation/ex1/input/Input.txt";
	PrintWriter writer = new PrintWriter(path);
	writer.print("");
	writer.flush();
	writer.write("abcd moish 9999");                                                   
                         writer.flush();  
        writer.close();
	}



	public static void main(String[]args){

	System.out.println("enter name of root user:");

	String root_user = in.next();

	try {
		test1(root_user);
	
	}

	catch (IOException e){
	}

	//run make command 

	System.out.println("enter path for make command:");

	String path = in.next();

	try {
		File f = new File(path);
		runCommand(f,"make");
		
	}
		
	catch(Exception e){
	System.out.println("Make was unsuccessful");
	
	}

	try {
		
		Path path1 = Paths.get("/home/roee/Desktop/output.txt");
		Path path2 = Paths.get("/home/roee/Desktop/compilation/ex1/output/OutputTokens.txt");
		long diff = filesCompareByLine(path1,path2);
		
		if(diff == -1)
			System.out.println("Files match");
		else
			System.out.println("Files do not match, difference at line  "+diff);
	
	}

	catch (IOException e){
		System.out.println("Could not compare files");
	}

	}

}

