package com.gadawski.maven.plugins.cloverage.goals;

import java.util.Arrays;
import java.util.Locale;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.reporting.MavenReportException;

/**
 * Evaluate code coverage to html report.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "html", threadSafe = true, requiresDependencyResolution = ResolutionScope.TEST,
        defaultPhase=LifecyclePhase.SITE)
public class HtmlMojo extends AbstractCloverageMojo {

    public HtmlMojo() {
        // empty
    }

    @Override
    protected void executeReport(Locale arg0) throws MavenReportException {
        getLog().info("HTML!!");
        super.executeReport(arg0);
        executeCloverage(Arrays.asList("--html"));
    }
}
