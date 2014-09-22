package com.gadawski.maven.plugins.cloverage.util;

import java.io.File;
import java.util.Collection;
import java.util.List;

import com.gadawski.maven.plugins.cloverage.ClojureExecutorImpl;
import com.google.common.collect.Lists;

/**
 * Utility class for finding clojure namespaces in project.
 * 
 * @author l.gadawski@gmail.com
 *
 */
public class NamespaceUtil {

    // prevent instantiations
    private NamespaceUtil() {
        // empty
    }

    /**
     * Gather instrumenting project clojure namespaces.
     * 
     * @param sourceDirectory
     *            - project resources
     * @param testSourceDirectory
     *            - project test resources
     * 
     * @return list of clojure namespaces with following convention (acceptable
     *         for cloverage library): ["-x", "test1", "-x", "test2", "-x",
     *         "test3", {... "-x", "testn"}, "source1", "source2", "source3",
     *         {..."sourcen"}]
     */
    public static List<String> getClojureNamespaces(File testSourceDirectory, File sourceDirectory) {
        List<String> result = Lists.newArrayList();

        List<String> testNss = getTestNamespaces(testSourceDirectory);
        List<String> modifiedTestNssList = addXElementToTestNamespacesList(testNss);

        result.addAll(modifiedTestNssList);
        result.addAll(getSourcesNamespaces(sourceDirectory));
        return result;
    }

    /**
     * @param sourceDirectory
     * @return collection of sources clojure namespaces
     */
    private static List<String> getSourcesNamespaces(File sourceDirectory) {
        if (sourceDirectory == null) {
            throw new IllegalArgumentException("Source directory is null!");
        }
        List<String> result = Lists.newArrayList();
        result.addAll(getNamespacesInDir(sourceDirectory));
        return result;
    }

    /**
     * @param testSourceDirectory
     * @return collection of test sources clojure namespaces
     */
    private static List<String> getTestNamespaces(File testSourceDirectory) {
        if (testSourceDirectory == null) {
            throw new IllegalArgumentException("Test source directory is null!");
        }
        List<String> result = Lists.newArrayList();
        result.addAll(getNamespacesInDir(testSourceDirectory));
        return result;
    }

    /**
     * Invokes clojure function that finds recursively for clojure namespaces in
     * given directory.
     * 
     * @param sourceDirectory
     * @return Collection of clojure namespaces in given directory.
     */
    @SuppressWarnings("unchecked")
    private static List<String> getNamespacesInDir(File sourceDirectory) {
        ClojureExecutorImpl clojureExecutor = new ClojureExecutorImpl();
        Object sourceNamespaces = clojureExecutor.executeFindNamespacesInDir(sourceDirectory);
        if (sourceNamespaces instanceof Collection<?>) {
            sourceNamespaces = (Collection<?>) sourceNamespaces;
        } else {
            throw new IllegalStateException("Wrong type returned from Clojure function!");
        }
        return (List<String>) sourceNamespaces;
    }

    /**
     * For given list add "-x" elements on even positions.
     * 
     * @param testNss
     *            list to be modified
     */
    private static List<String> addXElementToTestNamespacesList(List<String> testNss) {
        if (testNss == null) {
            throw new IllegalArgumentException("Test namespaces list cannot be null!");
        }
        List<String> result = Lists.newArrayList();
        for (String string : testNss) {
            result.add("-x");
            result.add(string);
        }
        return result;
    }
}
