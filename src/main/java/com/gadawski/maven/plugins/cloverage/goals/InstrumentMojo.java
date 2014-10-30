package com.gadawski.maven.plugins.cloverage.goals;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.gadawski.maven.plugins.cloverage.ClojureExecutor;
import com.gadawski.maven.plugins.cloverage.util.ClassLoaderUtil;
import com.gadawski.maven.plugins.cloverage.util.NamespaceUtil;

/**
 * Main cloverage-maven-plugin mojo that performs clojure files Instrumentation.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "instrument", aggregator = false, requiresProject = true, threadSafe = true)
public class InstrumentMojo extends AbstractMojo {

    private static final String NOT_FOUND_ANY_NAMESPACES_INFO = "Not found any namespaces!";

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(defaultValue="src/main/clojure")
    private File clojureSourceDirectory;

    @Parameter(defaultValue="src/test/clojure")
    private File clojureTestSourceDirectory;

    /**
     * Executor for cloverage library.
     */
    @Component(role = ClojureExecutor.class, hint = "default")
    private ClojureExecutor clojureExecutor;

    public InstrumentMojo() {
        // empty
    }

    @Override
    public void execute() throws MojoExecutionException {
        ClassLoaderUtil.setContextClassLoader(project);

        List<String> clojureProjectNamespaces = 
                NamespaceUtil.getClojureNamespaces(clojureTestSourceDirectory, clojureSourceDirectory);
        getLog().debug("clojureProjectNamespaces: " + clojureProjectNamespaces);
        if (!clojureProjectNamespaces.isEmpty()) {
            clojureExecutor.executeCloverageInvoker(clojureProjectNamespaces);
        } else {
            getLog().info(NOT_FOUND_ANY_NAMESPACES_INFO);
        }
    }
}
