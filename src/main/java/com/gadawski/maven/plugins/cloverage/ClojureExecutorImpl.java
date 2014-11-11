package com.gadawski.maven.plugins.cloverage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;
import org.codehaus.plexus.logging.Logger;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import clojure.lang.RT;

/**
 * Implementation for {@link ClojureExecutor}
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Component(role = ClojureExecutor.class, hint = "default")
public class ClojureExecutorImpl implements ClojureExecutor {

    private static final String READING_CLJ_EXCEPTION_MSG = "Error while reading clojure cloverage library file!!";

    private static final String CLOJURE_INVOKER_CLJ = "com/gadawski/maven/plugins/cloverage/clojure_invoker.clj";
    private static final String CLOJURE_INVOKER_NS = "com.gadawski.maven.plugins.cloverage.clojure_invoker";

    private static final String CLOVERAGE_INVOKER_FUN = "invoke-cloverage";
    private static final Object FIND_NAMESPACES_IN_DIR_FUN = "find-namespaces-in-dir-string";

    @Requirement
    private Logger log;

    public ClojureExecutorImpl() {
        // empty
    }

    @Override
    public void executeCloverageInvoker(List<String> params) {
        try {
            RT.loadResourceScript(CLOJURE_INVOKER_CLJ);
        } catch (IOException e) {
            e.printStackTrace();
            getLog().error(READING_CLJ_EXCEPTION_MSG);
            return;
        }
        IFn cloverageInvoker = Clojure.var(CLOJURE_INVOKER_NS, CLOVERAGE_INVOKER_FUN);
        cloverageInvoker.invoke(params);
    }

    @Override
    public Object executeFindNamespacesInDir(File directory) {
        try {
            RT.loadResourceScript(CLOJURE_INVOKER_CLJ);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(READING_CLJ_EXCEPTION_MSG);
            return null;
        }
        IFn findNamespaceInDir = Clojure.var(CLOJURE_INVOKER_NS, FIND_NAMESPACES_IN_DIR_FUN);
        return findNamespaceInDir.invoke(directory);
    }

    @Override
    public void getClasspath() {
        getLog().debug("GET CLASSPATH");
        ClassLoader classLoader = this.getClass().getClassLoader();
        Enumeration<URL> roots = null;
        try {
            roots = classLoader.getResources("");
        } catch (IOException e) {
            e.printStackTrace();
            getLog().error(READING_CLJ_EXCEPTION_MSG);
            return;
        }
        while (roots.hasMoreElements()) {
            URL url = (URL) roots.nextElement();
            getLog().debug("URL: " + url);
            File root = new File(url.getPath());
            printChildren(root);
        }
    }

    private void printChildren(File root) {
        getLog().debug("ROOT: " + root);
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                for (File childFile : file.listFiles()) {
                    printChildren(childFile);
                }
            } else {
                getLog().debug("FILE_NAME: " + file.getName());
            }
        }
    }

    public Logger getLog() {
        return log;
    }
}
