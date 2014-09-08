package com.gadawski.maven.plugins.cloverage.goals;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.gadawski.maven.plugins.cloverage.ClojureExecutor;
import com.gadawski.maven.plugins.cloverage.ClojureExecutorImpl;
import com.gadawski.maven.plugins.cloverage.util.ClassLoaderUtil;

/**
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "test-polyglot", requiresProject = false)
public class TestPolyglotMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    private ClojureExecutor clojureExecutor;

    public void execute() throws MojoExecutionException {
        ClassLoaderUtil.setContextClassLoader(project);
        clojureExecutor = new ClojureExecutorImpl(getLog());
        clojureExecutor.executeCloverage("-x", "drugi.dalszy.lukasz-test", "pierwszy.kolejny.lukasz");
    }
}
