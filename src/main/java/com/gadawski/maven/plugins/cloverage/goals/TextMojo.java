package com.gadawski.maven.plugins.cloverage.goals;

import java.util.Arrays;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * Evaluate code coverage to text report.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "text", threadSafe = true, requiresDependencyResolution = ResolutionScope.TEST)
public class TextMojo extends AbstractCloverageMojo {

    public TextMojo() {
        // empty
    }

    @Override
    public void execute() throws MojoExecutionException {
        super.execute();
        executeCloverage(Arrays.asList("--text"));
    }
}
