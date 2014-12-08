package com.gadawski.maven.plugins.cloverage.goals;

import java.util.Locale;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.reporting.MavenReportException;

/**
 * Evaluates clojure code coverage to html report.
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
        super.executeReport(arg0);

        cloverageArgs.add("--html");
        executeCloverage(cloverageArgs);
    }
}
