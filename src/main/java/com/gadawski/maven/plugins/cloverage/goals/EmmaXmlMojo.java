package com.gadawski.maven.plugins.cloverage.goals;

import java.util.Arrays;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * Instrument mojo that produces Emma XML report.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "emma-xml", aggregator = false, requiresProject = true, threadSafe = true,
requiresDependencyResolution = ResolutionScope.TEST)
public class EmmaXmlMojo extends AbstractInstrumentMojo {

    public EmmaXmlMojo() {
        // empty
    }

    @Override
    public void execute() throws MojoExecutionException {
        super.execute();
        executeCloverage(Arrays.asList("--emma-xml"));
    }
}
