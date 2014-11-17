package com.gadawski.maven.plugins.cloverage.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
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

    private static final String LOADER_ERROR_MSG = "Problem while setting context class loader!!";

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
            log.error(LOADER_ERROR_MSG);
            e.printStackTrace();
        }

        ClassLoader contextClassLoader = URLClassLoader.newInstance(urls.toArray(new URL[0]), Thread.currentThread()
                .getContextClassLoader());

        Thread.currentThread().setContextClassLoader(contextClassLoader);
    }

    private static void addClasspathElements(Set<URL> urls, List<String> classpathElements, Log log)
            throws MalformedURLException {
        log.debug("######################################## adding deps to classpath");
        for (String element : classpathElements) {
            log.debug("adding classpath element: " + element);
            urls.add(new File(element).toURI().toURL());
        }
        log.debug("######################################## ");
    }

    public void getClasspath(Log log) {
        log.debug("GET CLASSPATH");
        ClassLoader classLoader = this.getClass().getClassLoader();
        Enumeration<URL> roots = null;
        try {
            roots = classLoader.getResources("");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Error reading resource!");
            return;
        }
        while (roots.hasMoreElements()) {
            URL url = (URL) roots.nextElement();
            log.debug("URL: " + url);
            File root = new File(url.getPath());
            printChildren(root, log);
        }
    }

    private void printChildren(File root, Log log) {
        log.debug("ROOT: " + root);
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                for (File childFile : file.listFiles()) {
                    printChildren(childFile, log);
                }
            } else {
                log.debug("FILE_NAME: " + file.getName());
            }
        }
    }
}
