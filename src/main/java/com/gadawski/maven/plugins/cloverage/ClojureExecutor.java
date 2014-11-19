package com.gadawski.maven.plugins.cloverage;

import java.io.File;
import java.util.List;

/**
 * Executor for cloverage library for processing clojure code with cloverage library.
 * 
 * @author l.gadawski@gmail.com
 *
 */
public interface ClojureExecutor {

    /**
     * Invoker cloverage library.
     * 
     * @param params
     */
    void executeCloverageInvoker(List<String> params);

    /**
     * Executed tools.namespaces.find-namespace-in-dir
     * 
     * @param sourceDirectory
     * @return
     */
    Object executeFindNamespacesInDir(File sourceDirectory);

}
