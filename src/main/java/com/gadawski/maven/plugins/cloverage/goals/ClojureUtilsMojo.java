package com.gadawski.maven.plugins.cloverage.goals;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import com.gadawski.maven.plugins.cloverage.ClojureExecutor;

/**
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "clojure-utils", requiresProject = false)
public class ClojureUtilsMojo extends AbstractMojo {

    private ClojureExecutor clojureExecutor;

    public void execute() throws MojoExecutionException {
        clojureExecutor = new ClojureExecutor(getLog());
        clojureExecutor.executeCloverage("-x", "mikera.cljutils.test-arrays", "mikera.cljutils.arrays");
    }
}
