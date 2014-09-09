package com.gadawski.maven.plugins.cloverage.goals;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.gadawski.maven.plugins.cloverage.ClojureExecutorImpl;
import com.gadawski.maven.plugins.cloverage.util.ClassLoaderUtil;

/**
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "instrument", requiresProject = false)
public class InstrumentMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;
    /**
     * Executor for cloverage library.
     */
    private ClojureExecutorImpl cloverageExecutor;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        ClassLoaderUtil.setContextClassLoader(project);
        cloverageExecutor = new ClojureExecutorImpl(getLog());
        
        List<String> testStrings = new ArrayList<>();
        testStrings.add("-x");
        testStrings.add("drugi.dalszy.lukasz-test");
        testStrings.add("pierwszy.kolejny.lukasz");
        cloverageExecutor.executeCloverageInvoker(testStrings);
    }

}
