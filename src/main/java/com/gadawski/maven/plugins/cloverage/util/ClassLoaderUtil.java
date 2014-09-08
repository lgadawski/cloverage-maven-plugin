package com.gadawski.maven.plugins.cloverage.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * @author l.gadawski@gmail.com
 *
 */
public class ClassLoaderUtil {

    // prevent instantiation
    private ClassLoaderUtil() {
        // empty
    }

    public static void setContextClassLoader(MavenProject project) throws MojoExecutionException {
        Set<URL> urls = new HashSet<>();
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
