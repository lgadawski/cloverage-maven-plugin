package com.gadawski.maven.plugins.cloverage.goals;

import java.util.Arrays;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * Evaluate code coverage to html report.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "html", threadSafe = true, requiresDependencyResolution = ResolutionScope.TEST)
public class HtmlMojo extends AbstractCloverageMojo {

    public HtmlMojo() {
        // empty
    }

    @Override
    public void execute() throws MojoExecutionException {
        super.execute();
        executeCloverage(Arrays.asList("--html"));
    }
}
