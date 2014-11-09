package com.gadawski.maven.plugins.cloverage.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
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
     * @throws MojoExecutionException
     */
    @SuppressWarnings("unchecked")
    public static final void setContextClassLoader(MavenProject project) throws MojoExecutionException {
        Set<URL> urls = Sets.newHashSet();
        try {
            addClasspathElements(urls, project.getTestClasspathElements());
            addClasspathElements(urls, project.getRuntimeClasspathElements());
            addClasspathElements(urls, project.getCompileClasspathElements());
            addClasspathElements(urls, project.getSystemClasspathElements());

            ClassLoader contextClassLoader = URLClassLoader.newInstance(urls.toArray(new URL[0]), Thread
                    .currentThread().getContextClassLoader());

            Thread.currentThread().setContextClassLoader(contextClassLoader);
        } catch (DependencyResolutionRequiredException | MalformedURLException e) {
            e.printStackTrace();
            throw new MojoExecutionException("Problem while setting context ClassLoader!");
        }
    }

    private static void addClasspathElements(Set<URL> urls, List<String> classpathElements)
            throws DependencyResolutionRequiredException, MalformedURLException {
        for (String element : classpathElements) {
            urls.add(new File(element).toURI().toURL());
        }
    }
}
