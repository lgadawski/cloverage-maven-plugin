package com.gadawski.maven.plugins.cloverage.goals;

import java.io.File;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.doxia.siterenderer.Renderer;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.reporting.AbstractMavenReport;
import org.apache.maven.reporting.MavenReportException;

import com.gadawski.maven.plugins.cloverage.ClojureExecutor;
import com.gadawski.maven.plugins.cloverage.util.ClassLoaderUtil;
import com.gadawski.maven.plugins.cloverage.util.NamespaceUtil;
import com.google.common.collect.Lists;

/**
 * Abstract cloverage-maven-plugin mojo that process clojure sources with
 * cloverage lib to get Clojure code coverage.
 * 
 * @author l.gadawski@gmail.com
 *
 */
public abstract class AbstractCloverageMojo extends AbstractMavenReport {

    private static final String NOT_FOUND_ANY_NAMESPACES_INFO = "Not found any namespaces!";
    private static final String CLOVERAGE_REPORT_NAME = "Cloverage Report";
    private static final String OUTPUT_NAME = "coverage/index";
    private static final String CLOVERAGE_REPORT_DESCRIPTION = "Clojure code coverage report.";

    /** Args for cloverage invoke. */
    protected List<String> cloverageArgs = Lists.newArrayList();

    @Parameter(defaultValue = "${project.reporting.outputDirectory}/coverage")
    protected String outputDirectory;

    @Parameter(defaultValue = "${project.reporting.outputDirectory}/coverage")
    protected File reportOutputDirectory;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    protected MavenProject project;

    @Parameter(defaultValue = "src/main/clojure")
    protected File clojureSourceDirectory;

    @Parameter(defaultValue = "src/test/clojure")
    protected File clojureTestSourceDirectory;

    /**
     * Executor for cloverage library.
     */
    @Component(role = ClojureExecutor.class, hint = "default")
    protected ClojureExecutor clojureExecutor;

    @Component
    private Renderer siteRenderer;

    @Override
    public String getDescription(Locale arg0) {
        return CLOVERAGE_REPORT_DESCRIPTION;
    }

    @Override
    public String getName(Locale arg0) {
        return CLOVERAGE_REPORT_NAME;
    }

    @Override
    public String getOutputName() {
        return OUTPUT_NAME;
    }

    @Override
    protected void executeReport(Locale arg0) throws MavenReportException {
        ClassLoaderUtil.setContextClassLoader(project, getLog());

        if (StringUtils.isNotBlank(getOutputDirectory())) {
            cloverageArgs.add("-o");
            cloverageArgs.add(getOutputDirectory());
        }
    }

    @Override
    public void execute() throws MojoExecutionException {
        // generate report in default location
        setOutputDirectory("");
        super.execute();
    }

    @Override
    protected String getOutputDirectory() {
        return outputDirectory;
    }
    
    protected void setOutputDirectory(String outputDirectory) {
    	this.outputDirectory = outputDirectory;
    }

    @Override
    protected MavenProject getProject() {
        return project;
    }

    @Override
    protected Renderer getSiteRenderer() {
        return siteRenderer;
    }

    @Override
    public boolean isExternalReport() {
        return true;
    }

    /**
     * Executes cloverage library with given params.
     * 
     * @param params
     */
    protected void executeCloverage(List<String> params) {
        List<String> clojureProjectNamespaces = NamespaceUtil.getClojureNamespaces(clojureTestSourceDirectory,
                clojureSourceDirectory);
        if (!clojureProjectNamespaces.isEmpty()) {
            // add parameters
            clojureProjectNamespaces.addAll(params);
            clojureExecutor.executeCloverageInvoker(clojureProjectNamespaces);
        } else {
            getLog().info(NOT_FOUND_ANY_NAMESPACES_INFO);
        }
    }
}
