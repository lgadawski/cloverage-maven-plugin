package com.gadawski.maven.plugins.cloverage.goals;

import java.util.Arrays;
import java.util.Locale;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.reporting.MavenReportException;

/**
 * Evaluate code coverage to Emma XML report.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "emma-xml", threadSafe = true, requiresDependencyResolution = ResolutionScope.TEST,
        defaultPhase=LifecyclePhase.SITE)
public class EmmaXmlMojo extends AbstractCloverageMojo {

    public EmmaXmlMojo() {
        // empty
    }

    @Override
    protected void executeReport(Locale arg0) throws MavenReportException {
        super.executeReport(arg0);
        executeCloverage(Arrays.asList("--text"));
    }
}
