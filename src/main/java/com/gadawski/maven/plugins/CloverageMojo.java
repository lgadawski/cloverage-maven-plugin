package com.gadawski.maven.plugins;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import clojure.lang.RT;
import clojure.lang.Var;

@Mojo(name = "cloverage", requiresProject = false)
public class CloverageMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException {
        try {
            executeCloverage();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("### Error while reading clojure file!!");
        }
    }

    private void executeCloverage() throws IOException {
        RT.loadResourceScript("cloverage/coverage.clj");

        Var coverage = RT.var("cloverage.coverage", "-main");
        
        String relativePathToProject = System.getProperty("user.dir");
        System.out.println("#Working dir: " + relativePathToProject);
        Path path = Paths.get(relativePathToProject + "\\src\\test\\clojure\\testClojure.clj");
        System.out.println(String.format("##path: %s, root: %s", path, path.getRoot()));
        
        Object result = coverage.invoke("-x", "lukasz-test", "lukasz");
        System.out.println("coverage result: " + result);
    }

    
}
