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
 * @author l.gadawski@gmail.com
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
    public static final void setContextClassLoader(MavenProject project) throws MojoExecutionException {
        Set<URL> urls = Sets.newHashSet();
        List<String> elements;
        try {
            elements = project.getTestClasspathElements();
            // getRuntimeClasspathElements()
            // getCompileClasspathElements()
            // getSystemClasspathElements()
            for (String element : elements) {
                urls.add(new File(element).toURI().toURL());
            }
            ClassLoader contextClassLoader = URLClassLoader.newInstance(urls.toArray(new URL[0]), 
                    Thread.currentThread().getContextClassLoader());

            Thread.currentThread().setContextClassLoader(contextClassLoader);
        } catch (DependencyResolutionRequiredException | MalformedURLException e) {
            e.printStackTrace();
            throw new MojoExecutionException("Problem while setting context ClassLoader!");
        }
    }
}
