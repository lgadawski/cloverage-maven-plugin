package com.gadawski.maven.plugins.cloverage.goals;

import java.util.Arrays;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * Instrument mojo that produces text report.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "text", aggregator = false, requiresProject = true, threadSafe = true,
requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class TextMojo extends AbstractInstrumentMojo {

    public TextMojo() {
        // empty
    }

    @Override
    public void execute() throws MojoExecutionException {
        super.execute();
        executeCloverage(Arrays.asList("--text"));
    }
}
