package com.gadawski.maven.plugins.cloverage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.apache.maven.plugin.logging.Log;

import clojure.lang.RT;
import clojure.lang.Var;

public class ClojureExecutorImpl implements ClojureExecutor {

    private static final String CLOVERAGE_COVERAGE_CLJ = "cloverage/coverage.clj";
    private static final String USER_DIR = "user.dir";
    private final Log log;

    public ClojureExecutorImpl(Log log) {
        this.log = log;
    }

    @Override
    public void executeCloverage(String... params) {
        try {
            RT.loadResourceScript(CLOVERAGE_COVERAGE_CLJ);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("### Error while reading clojure file!!");
            return;
        }

        Var coverage = RT.var("cloverage.coverage", "-main");

        String relativePathToProject = System.getProperty(USER_DIR);
        System.out.println("#Working dir: " + relativePathToProject);

        coverage.invoke(params[0], params[1], params[2]);
    }

    @Override
    public void getClasspath() {
        System.out.println("############ GET CLASSPATH");
        ClassLoader classLoader = this.getClass().getClassLoader();
        Enumeration<URL> roots = null;
        try {
            roots = classLoader.getResources("");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("### Error while reading resources!!");
            return;
        }
        while (roots.hasMoreElements()) {
            URL url = (URL) roots.nextElement();
            log.debug("URL: " + url);
            File root = new File(url.getPath());
            printChildren(root);
        }
    }

    private void printChildren(File root) {
        log.debug("ROOT: " + root);
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                for (File childFile : file.listFiles()) {
                    printChildren(childFile);
                }
            } else {
                log.debug("## FILE_NAME: " + file.getName());
            }
        }
    }
}
