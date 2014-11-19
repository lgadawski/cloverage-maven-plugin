package com.gadawski.maven.plugins.cloverage.goals;

import java.util.Arrays;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * Evaluate code coverage to Emma XML report.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "emma-xml", threadSafe = true, requiresDependencyResolution = ResolutionScope.TEST)
public class EmmaXmlMojo extends AbstractCloverageMojo {

    public EmmaXmlMojo() {
        // empty
    }

    @Override
    public void execute() throws MojoExecutionException {
        super.execute();
        executeCloverage(Arrays.asList("--emma-xml"));
    }
}
