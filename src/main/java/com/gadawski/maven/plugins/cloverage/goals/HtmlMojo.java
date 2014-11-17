package com.gadawski.maven.plugins.cloverage.goals;

import java.util.Arrays;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * Instrument mojo that produces html report.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "html", aggregator = false, requiresProject = true, threadSafe = true,
requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class HtmlMojo extends AbstractInstrumentMojo {

    public HtmlMojo() {
        // empty
    }

    @Override
    public void execute() throws MojoExecutionException {
        super.execute();
        executeCloverage(Arrays.asList("--html"));
    }
}
