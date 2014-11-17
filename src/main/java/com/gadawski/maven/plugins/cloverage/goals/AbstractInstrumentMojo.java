package com.gadawski.maven.plugins.cloverage.goals;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.gadawski.maven.plugins.cloverage.ClojureExecutor;
import com.gadawski.maven.plugins.cloverage.util.ClassLoaderUtil;
import com.gadawski.maven.plugins.cloverage.util.NamespaceUtil;

/**
 * Abstract cloverage-maven-plugin mojo that performs clojure files Instrumentation.
 * 
 * @author l.gadawski@gmail.com
 *
 */
public abstract class AbstractInstrumentMojo extends AbstractMojo {

    private static final String NOT_FOUND_ANY_NAMESPACES_INFO = "Not found any namespaces!";

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    protected MavenProject project;

    @Parameter(defaultValue="src/main/clojure")
    protected File clojureSourceDirectory;

    @Parameter(defaultValue="src/test/clojure")
    protected File clojureTestSourceDirectory;

    /**
     * Executor for cloverage library.
     */
    @Component(role = ClojureExecutor.class, hint = "default")
    protected ClojureExecutor clojureExecutor;

    @Override
    public void execute() throws MojoExecutionException {
        ClassLoaderUtil.setContextClassLoader(project, getLog());
    }

    /**
     * Executes cloverage library with given params.
     * 
     * @param params
     */
    protected void executeCloverage(List<String> params) {
        List<String> clojureProjectNamespaces = 
                NamespaceUtil.getClojureNamespaces(clojureTestSourceDirectory, clojureSourceDirectory);
        if (!clojureProjectNamespaces.isEmpty()) {
            // add parameters
            clojureProjectNamespaces.addAll(params);
            clojureExecutor.executeCloverageInvoker(clojureProjectNamespaces);
        } else {
            getLog().info(NOT_FOUND_ANY_NAMESPACES_INFO);
        }
    }

}
