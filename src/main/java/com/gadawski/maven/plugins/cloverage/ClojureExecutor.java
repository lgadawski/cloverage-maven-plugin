package com.gadawski.maven.plugins.cloverage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.apache.maven.plugin.logging.Log;

import clojure.lang.RT;
import clojure.lang.Var;

public class ClojureExecutor {

    private final Log log;

    public ClojureExecutor(Log log) {
        this.log = log;
    }

    public void executeCloverage(String... params) {
        try {
            RT.loadResourceScript("cloverage/coverage.clj");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("### Error while reading clojure file!!");
        }

        Var coverage = RT.var("cloverage.coverage", "-main");

        String relativePathToProject = System.getProperty("user.dir");
        System.out.println("#Working dir: " + relativePathToProject);

        coverage.invoke(params[0], params[1], params[2]);
    }

    public void getClasspath() {
        System.out.println("############ GET CLASSPATH");
        ClassLoader classLoader = this.getClass().getClassLoader();
        Enumeration<URL> roots = null;
        try {
            roots = classLoader.getResources("");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("### Error while reading resources!!");
        }
        while (roots.hasMoreElements()) {
            URL url = (URL) roots.nextElement();
            System.out.println("URL: " + url);
            log.debug("URL: " + url);
            File root = new File(url.getPath());
            printChildren(root);
        }
    }

    private void printChildren(File root) {
        log.debug("ROOT: " + root);
        System.out.println("ROOT: " + root);
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                for (File childFile : file.listFiles()) {
                    printChildren(childFile);
                }
            } else {
                System.out.println("## FILE_NAME: " + file.getName());
            }
        }
    }
}
