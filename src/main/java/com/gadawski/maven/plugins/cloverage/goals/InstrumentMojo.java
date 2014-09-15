package com.gadawski.maven.plugins.cloverage.goals;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.sonatype.inject.Parameters;

import com.gadawski.maven.plugins.cloverage.ClojureExecutor;
import com.gadawski.maven.plugins.cloverage.ClojureExecutorImpl;
import com.gadawski.maven.plugins.cloverage.util.ClassLoaderUtil;
import com.gadawski.maven.plugins.cloverage.util.NamespaceUtil;

/**
 * Main cloverage-maven-plugin mojo that performs clojure files Instrumentation.
 * 
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "instrument", aggregator = false, requiresProject = true, threadSafe = true)
public class InstrumentMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    @Parameter(property = "source.directory")
    private File srcDirCljs;

    @Parameter(name = "test.source.directory")
    private File testSrcDirCljs;

//    @Parameter( = "${project.build.srcDirClj}")
//    private String srcDirClj;
//
//    @Parameters
//    private String testSrcDirClj;

    /**
     * Executor for cloverage library.
     */
    private ClojureExecutor clojureExecutor;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println(srcDirCljs);
        System.out.println(testSrcDirCljs);
//        System.out.println(srcDirClj);
//        System.out.println(testSrcDirClj);

        ClassLoaderUtil.setContextClassLoader(project);
        clojureExecutor = new ClojureExecutorImpl(getLog());

        List<String> clojureProjectNamespaces = NamespaceUtil.getClojureNamespaces(testSrcDirCljs, srcDirCljs);
        if (!clojureProjectNamespaces.isEmpty()) {
            clojureExecutor.executeCloverageInvoker(clojureProjectNamespaces);
        }
    }

}
