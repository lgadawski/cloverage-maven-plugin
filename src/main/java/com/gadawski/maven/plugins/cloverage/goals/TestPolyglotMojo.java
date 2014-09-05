package com.gadawski.maven.plugins.cloverage.goals;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import com.gadawski.maven.plugins.cloverage.ClojureExecutor;
import com.gadawski.maven.plugins.cloverage.ClojureExecutorImpl;

/**
 * @author l.gadawski@gmail.com
 *
 */
@Mojo(name = "test-polyglot", requiresProject = false)
public class TestPolyglotMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    private ClojureExecutor clojureExecutor;

    public void execute() throws MojoExecutionException {
        try {
            showFiles();
            clojureExecutor = new ClojureExecutorImpl(getLog());
            clojureExecutor.executeCloverage("-x", "drugi.lukasz-test", "pierwszy.lukasz");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (DependencyResolutionRequiredException e) {
            e.printStackTrace();
        }
    }

    private void showFiles() throws IOException, URISyntaxException, DependencyResolutionRequiredException {
        getLog().debug("*******************************************");
        try {
            Set<URL> urls = new HashSet<>();
            List<String> elements = project.getTestClasspathElements();
            // getRuntimeClasspathElements()
            // getCompileClasspathElements()
            // getSystemClasspathElements()
            for (String element : elements) {
                urls.add(new File(element).toURI().toURL());
            }
            for (URL url : urls) {
                System.out.println(url);
            }
            ClassLoader contextClassLoader = URLClassLoader.newInstance(urls.toArray(new URL[0]), Thread
                    .currentThread().getContextClassLoader());

            Thread.currentThread().setContextClassLoader(contextClassLoader);

        } catch (DependencyResolutionRequiredException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        getLog().debug("*******************************************");
        getLog().debug("****************** resources ************");
        List<String> resources = project.getResources();
        getLog().debug(resources.toString());
        getLog().debug("*******************************************");
        getLog().debug("******************test resources***************");
        List testResources = project.getTestResources();
        getLog().debug(testResources.toString());
        getLog().debug("*******************************************");
        getLog().debug("******************system classpathc elements***************");
        List systemClasspathElements = project.getSystemClasspathElements();
        getLog().debug(systemClasspathElements.toString());
        getLog().debug("*******************************************");
    }
}
