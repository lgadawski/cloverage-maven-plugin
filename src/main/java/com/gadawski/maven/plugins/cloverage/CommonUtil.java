package com.gadawski.maven.plugins.cloverage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import clojure.lang.RT;
import clojure.lang.Var;

public class CommonUtil {

    // prevent instantiations
    private CommonUtil() {
        // empty
    }

    public static void printOutFileContent(File testFile) {
        Charset charset = Charset.forName("US-ASCII");
        try {
            BufferedReader reader = Files.newBufferedReader(testFile.toPath(), charset);
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void testClojureCoreInvoke() throws IOException {
        RT.loadResourceScript("clojure/data.clj");
        Var test = RT.var("clojure.data", "diff");
        Object testresult = test.invoke(1, 2);
        System.out.println("####Test result: " + testresult);
    }

    public static void executeClojurePrintTest() throws IOException {
        RT.loadResourceScript("lukasz.clj");

        Var foo = RT.var("lukasz", "main");

        Object result = foo.invoke();
        System.out.println("print clojure result: ");
        System.out.println(result);
    }
}
