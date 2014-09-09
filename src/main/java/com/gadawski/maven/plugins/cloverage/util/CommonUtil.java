package com.gadawski.maven.plugins.cloverage.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import clojure.lang.RT;
import clojure.lang.Var;

/**
 * Common utility class
 * 
 * @author l.gadawski@gmail.com
 *
 */
public class CommonUtil {

    private static final String USER_DIR = "user.dir";

    // prevent instantiations
    private CommonUtil() {
        // empty
    }

    /**
     * @return project relative path on property "user.dir"
     */
    public static String getRelativePathToProject() {
        return System.getProperty(USER_DIR);
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
            e.printStackTrace();
        }
    }

    public static void executeClojurePrintTest() throws IOException {
        RT.loadResourceScript("lukasz.clj");

        Var foo = RT.var("lukasz", "main");

        Object result = foo.invoke();
        System.out.println("print clojure result: ");
        System.out.println(result);
    }
}
