cloverage-maven-plugin
======================

Maven plugin responsible for measuring clojure code coverage with [cloverage](https://github.com/lshift/cloverage). 

Configuration
----------------------
### pom.xml

```
<plugin>
    <groupId>com.gadawski.maven.plugins</groupId>
    <artifactId>cloverage-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
    <configuration>
        <clojureSourceDirectory>${clojureSourceDirectory}</clojureSourceDirectory>
        <clojureTestSourceDirectory>${clojureTestSourceDirectory}</clojureTestSourceDirectory>
    </configuration>
</plugin>
```

When configuration is empty default source directory is `src/main/clojure` and test source directory `src/test/clojure`

__Warning__, additionally `<build>` tags must contain following `<resources>` tags:

```
...
<build>
    ...
    <resources>
        <resource>
            <directory>${clojureSourceDirectory}</directory>
        </resource>
        <resource>
            <directory>${clojureTestSourceDirectory}</directory>
        </resource>
    </resources>
    ...
</build>
...
```
Usage
----------------------
Execute: `mvn cloverage:instrument`

License
======================
Distributed under the Eclipse Public License.
