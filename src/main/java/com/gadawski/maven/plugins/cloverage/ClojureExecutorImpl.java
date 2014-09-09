package com.gadawski.maven.plugins.cloverage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import org.apache.maven.plugin.logging.Log;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.RT;

/**
 * Implementation for {@link ClojureExecutor}
 * 
 * @author l.gadawski@gmail.com
 *
 */
public class ClojureExecutorImpl implements ClojureExecutor {

    private static final String CLOVERAGE_COVERAGE_CLJ = "cloverage/coverage.clj";
    private static final String CLOVERAGE_NS = "cloverage.coverage";
    private static final String FUNCTION = "-main";

    private static final String CLOVERAGE_INVOKER_CLJ = "com/gadawski/maven/plugins/cloverage/cloverage_invoker.clj";
    private static final String CLOVERAGE_INVOKER_NS = "com.gadawski.maven.plugins.cloverage.cloverage_invoker";
    private static final String CLOVERAGE_INVOKER_FUN = "main";

    // logger
    private final Log log;

    public ClojureExecutorImpl(Log log) {
        this.log = log;
    }

    @Override
    public void executeCloverageInvoker(List<String> testStrings) {
        try {
            RT.loadResourceScript(CLOVERAGE_INVOKER_CLJ);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error while reading clojure cloverage library file!!");
            return;
        }
        IFn cloverageInvoker = Clojure.var(CLOVERAGE_INVOKER_NS, CLOVERAGE_INVOKER_FUN);
        cloverageInvoker.invoke(testStrings);
    }

    @Override
    public void executeCloverage(String... params) {
        try {
            RT.loadResourceScript(CLOVERAGE_COVERAGE_CLJ);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error while reading clojure cloverage library file!!");
            return;
        }
        IFn cloverage = Clojure.var(CLOVERAGE_NS, FUNCTION);
        // dummy
        cloverage.invoke(params[0], params[1], params[2]);
    }

    @Override
    public void getClasspath() {
        log.debug("GET CLASSPATH");
        ClassLoader classLoader = this.getClass().getClassLoader();
        Enumeration<URL> roots = null;
        try {
            roots = classLoader.getResources("");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error while reading resources!!");
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
                log.debug("FILE_NAME: " + file.getName());
            }
        }
    }
}
