package com.gadawski.maven.plugins.cloverage.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import com.google.common.collect.Sets;

/**
 * 
 * @link http://stackoverflow.com/a/16263473/1024079
 *
 */
public final class ClassLoaderUtil {

    // prevent instantiation
    private ClassLoaderUtil() {
        // empty
    }

    /**
     * @param project
     * @param log
     * @throws DependencyResolutionRequiredException
     * @throws MojoExecutionException
     */
    @SuppressWarnings("unchecked")
    public static final void setContextClassLoader(MavenProject project, Log log) {
        Set<URL> urls = Sets.newHashSet();
        try {
            addClasspathElements(urls, project.getTestClasspathElements(), log);
            addClasspathElements(urls, project.getRuntimeClasspathElements(), log);
            addClasspathElements(urls, project.getCompileClasspathElements(), log);
            addClasspathElements(urls, project.getSystemClasspathElements(), log);
        } catch (MalformedURLException | DependencyResolutionRequiredException e) {
            System.out.println("Problem while setting context class loader!!");
            e.printStackTrace();
        }

        ClassLoader contextClassLoader = URLClassLoader.newInstance(urls.toArray(new URL[0]), Thread.currentThread()
                .getContextClassLoader());

        Thread.currentThread().setContextClassLoader(contextClassLoader);
    }

    private static void addClasspathElements(Set<URL> urls, List<String> classpathElements, Log log)
            throws MalformedURLException {
        log.info("######################################## adding deps to classpath");
        for (String element : classpathElements) {
            log.info("adding classpath element: " + element);
            urls.add(new File(element).toURI().toURL());
        }
        log.info("######################################## ");
    }
}
