package com.gadawski.maven.plugins.cloverage;

import org.junit.Assert;
import org.junit.Test;

import com.gadawski.maven.plugins.cloverage.util.CommonUtil;

/**
 * @author l.gadawski@gmail.com
 *
 */
public class TestBasics {

    @Test
    public void testExample() {
        Assert.assertTrue(CommonUtil.getRelativePathToProject().endsWith("cloverage-maven-plugin"));
    }
}
