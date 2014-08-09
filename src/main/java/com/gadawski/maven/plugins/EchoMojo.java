package com.gadawski.maven.plugins;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import clojure.lang.RT;
import clojure.lang.Var;

@Mojo(name = "echo", requiresProject = false)
public class EchoMojo extends AbstractMojo {

    @Parameter(property = "echo.msg", defaultValue = "goodby cruel world!")
    private Object msg;

    @Parameter(defaultValue = "testFile.txt")
    private File testFile;

    public void execute() throws MojoExecutionException {
        getLog().info(msg.toString());
        printOutFileContent();
        try {
            executeClojurePrintTest();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("### Error while reading clojure file!!");
        }
    }

    private void executeClojurePrintTest() throws IOException {
        RT.loadResourceScript("main.clj");

        Var foo = RT.var("main", "main");

        Object result = foo.invoke();
        System.out.println("print clojure result: ");
        System.out.println(result);
    }

    private void printOutFileContent() {
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
}
