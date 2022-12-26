//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class test {
    static Scanner in;

    public test() {
    }

    public static void runCommand(File var0, String var1) throws Exception {
        System.out.println("Running in: " + var0);
        System.out.println("Command: " + var1);
        String[] var2 = new String[]{"sh", "-c", var1};
        Process var3 = Runtime.getRuntime().exec(var2, (String[])null, var0);
        OutputStream var4 = var3.getOutputStream();
        InputStream var5 = var3.getInputStream();
        InputStream var6 = var3.getErrorStream();
        printStream(var5);
        printStream(var6);
        boolean var7 = var3.waitFor(30L, TimeUnit.SECONDS);
        var4.flush();
        var4.close();
        if (!var7) {
            var3.destroyForcibly();
        }

    }

    private static void printStream(InputStream var0) throws IOException {
        BufferedReader var1 = new BufferedReader(new InputStreamReader(var0));
        Throwable var2 = null;

        try {
            String var3;
            try {
                while((var3 = var1.readLine()) != null) {
                    System.out.println(var3);
                }
            } catch (Throwable var11) {
                var2 = var11;
                throw var11;
            }
        } finally {
            if (var1 != null) {
                if (var2 != null) {
                    try {
                        var1.close();
                    } catch (Throwable var10) {
                        var2.addSuppressed(var10);
                    }
                } else {
                    var1.close();
                }
            }

        }

    }

    public static void runShellCommand(String var0, String var1) {
        try {
            File var2 = new File(var0);
            runCommand(var2, var1);
        } catch (Exception var3) {
            System.out.println("Command" + var1 + " was unsuccessful");
        }

    }

    public static long filesCompareByLine(Path var0, Path var1) throws IOException {
        BufferedReader var2 = Files.newBufferedReader(var0);
        Throwable var3 = null;

        try {
            BufferedReader var4 = Files.newBufferedReader(var1);
            Throwable var5 = null;

            try {
                long var6 = 1L;
                String var8 = "";

                long var10;
                for(String var9 = ""; (var8 = var2.readLine()) != null; ++var6) {
                    var9 = var4.readLine();
                    if (var9 == null || !var8.equals(var9)) {
                        var10 = var6;
                        return var10;
                    }
                }

                if (var4.readLine() == null) {
                    var10 = -1L;
                    return var10;
                } else {
                    var10 = var6;
                    return var10;
                }
            } catch (Throwable var40) {
                var5 = var40;
                throw var40;
            } finally {
                if (var4 != null) {
                    if (var5 != null) {
                        try {
                            var4.close();
                        } catch (Throwable var39) {
                            var5.addSuppressed(var39);
                        }
                    } else {
                        var4.close();
                    }
                }

            }
        } catch (Throwable var42) {
            var3 = var42;
            throw var42;
        } finally {
            if (var2 != null) {
                if (var3 != null) {
                    try {
                        var2.close();
                    } catch (Throwable var38) {
                        var3.addSuppressed(var38);
                    }
                } else {
                    var2.close();
                }
            }

        }
    }


    public static void test_generic(String var0, String var1,String path) throws IOException {
        String var2 = var0 + path;
        FileReader var3 = new FileReader(var1);
        PrintWriter var4 = new PrintWriter(var2);
        var4.print("");
        var4.flush();

        String var5;
        int var6;
        for(var5 = ""; (var6 = var3.read()) != -1; var5 = var5 + (char)var6) {
        }

        var4.write(var5);
        var4.flush();
        var4.close();
    }

    public static void main(String[] var0) {
        int var1 = 0;
        int var2 = 0;
        int varE = 0;

        try {
            PrintWriter var3 = new PrintWriter("../../Compilation_Tests/test_folder_proj3/most_recent_output.txt");

            for(int var4 = 0; var4 <= 63; ++var4) {
                try {
                    test_generic("../../", "./input/test" + var4 + ".txt","/ex3/input/Input.txt");
                } catch (IOException var11) {
                }

                runShellCommand("../../ex3/", "make debug");

                try {
                    Path var5 = Paths.get("../../Compilation_Tests/test_folder_proj3/output/output" + var4 + ".txt");
                    Path var6 = Paths.get("../../ex3/output/SemanticStatus.txt");
                    FileReader var11 = new FileReader("../../ex3/output/SemanticStatus.txt");




                    long var7 = filesCompareByLine(var5, var6);

                    String var9;
                    int var10;
                    for(var9 = ""; (var10 = var11.read()) != -1; var9 = var9 + (char)var10) {
                    }

                    var3.write(var9);



                    if (var7 == -1L) {
                        System.out.println("Files match");
                        if (var4 <= 20) {
                            ++var1;
                        } else if(var4 <= 37){
                            ++var2;
                        } else{
                            ++varE;
                        }

                        System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ - Passed test " + var4 + "! $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
                        var3.write("Passed test " + var4 + "!\n");
                    } else {
                        System.out.println("\n######################## File " + var4 +" do not match, difference at line  " + var7 + " ###########################\n");
                        var3.write("Files do not match, difference at line  " + var7 + " in test " + var4 + "!\n");
                    }

                } catch (IOException var10) {
                    System.out.println("Could not compare files");
                    var3.write("Could not compare files in test" + var4 + "\n");
                }

                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException var9) {
                }
            }

            System.out.println("Passed OG tests: " + var1 + " out of 20");
            var3.write("Passed OG: " + var1 + " out of 26\n");
            System.out.println("Passed tests for OK: " + var2 + " out of 17");
            var3.write("Passed tests for OK: " + var2 + " out of 17\n");
            System.out.println("Passed tests for Errors: " + varE + " out of 26");
            var3.write("Passed tests for Errors: " + varE + " out of 26\n");
            if (var1 + var2 + varE == 63) {
                System.out.println("Passed all the tests! Well done!");
                var3.write("Passed all the tests! Well done!\n");
            }

            var3.flush();
            var3.close();
        } catch (IOException var12) {
        }

    }

    static {
        in = new Scanner(System.in);
    }
}
