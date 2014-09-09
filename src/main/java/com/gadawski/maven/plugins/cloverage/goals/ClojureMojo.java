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
@Mojo(name = "clojure", requiresProject = false)
public class ClojureMojo extends AbstractMojo {

    private ClojureExecutor clojureExecutor;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        ClassLoaderUtil.setContextClassLoader(project);
        clojureExecutor = new ClojureExecutorImpl(getLog());
        clojureExecutor.getClasspath();
        clojureExecutor.executeCloverage("-x", "clojure.test-clojure.data", "clojure.data");
    }
}
