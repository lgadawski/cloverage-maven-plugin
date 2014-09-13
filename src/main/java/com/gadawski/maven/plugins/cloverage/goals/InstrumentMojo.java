package com.gadawski.maven.plugins.cloverage.goals;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.gadawski.maven.plugins.cloverage.ClojureExecutor;
import com.gadawski.maven.plugins.cloverage.ClojureExecutorImpl;
import com.gadawski.maven.plugins.cloverage.util.ClassLoaderUtil;
import com.gadawski.maven.plugins.cloverage.util.NamespaceUtil;

/**
 * Main cloverage-maven-plugin mojo that performs clojure files
 * instrumentations.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "instrument", requiresProject = false)
public class InstrumentMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "sourceDirectory", defaultValue = "src/main/clojure")
    private File sourcesPath;

    @Parameter(property = "testSourceDirectory", defaultValue = "src/test/clojure")
    private File testSourcesDirectory;

    /**
     * Executor for cloverage library.
     */
    private ClojureExecutor clojureExecutor;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        ClassLoaderUtil.setContextClassLoader(project);
        clojureExecutor = new ClojureExecutorImpl(getLog());

        List<String> clojureProjectNamespaces = NamespaceUtil.getClojureNamespaces(testSourcesDirectory,
                sourcesPath);
        clojureExecutor.executeCloverageInvoker(clojureProjectNamespaces);
    }

}
