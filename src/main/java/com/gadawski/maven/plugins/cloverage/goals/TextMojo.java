package com.gadawski.maven.plugins.cloverage.goals;

import java.util.Locale;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.reporting.MavenReportException;

/**
 * Evaluate clojure code coverage to text report.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "text", threadSafe = true, requiresDependencyResolution = ResolutionScope.TEST, 
        defaultPhase = LifecyclePhase.SITE)
public class TextMojo extends AbstractCloverageMojo {

    public TextMojo() {
        // empty
    }

    @Override
    protected void executeReport(Locale arg0) throws MavenReportException {
        super.executeReport(arg0);

        cloverageArgs.add("--text");
        executeCloverage(cloverageArgs);
    }
}
