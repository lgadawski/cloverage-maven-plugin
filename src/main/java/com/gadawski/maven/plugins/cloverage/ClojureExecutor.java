package com.gadawski.maven.plugins.cloverage;

import java.util.List;

/**
 * Executor for cloverage library for instrumenting clojure code.
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
     * Executes cloverage.coverage
     * 
     * @param params
     */
    void executeCloverage(String... params);

    /**
     * 
     */
    void getClasspath();
}
