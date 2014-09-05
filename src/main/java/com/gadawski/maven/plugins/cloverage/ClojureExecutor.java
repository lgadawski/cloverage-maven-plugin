package com.gadawski.maven.plugins.cloverage;

/**
 * @author l.gadawski@gmail.com
 *
 */
public interface ClojureExecutor {

    /**
     * @param params
     */
    void executeCloverage(String... params);

    /**
     * 
     */
    void getClasspath();
}
