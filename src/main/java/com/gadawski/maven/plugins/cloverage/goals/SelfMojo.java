package com.gadawski.maven.plugins.cloverage.goals;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import com.gadawski.maven.plugins.cloverage.ClojureExecutor;

/**
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "self", requiresProject = false)
public class SelfMojo extends AbstractMojo {

    private ClojureExecutor clojureExecutor;

    public void execute() throws MojoExecutionException {
        clojureExecutor = new ClojureExecutor(getLog());
        clojureExecutor.executeCloverage("-x", "drugi.lukasz-test", "pierwszy.lukasz");
    }
}
