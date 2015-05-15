package org.exoplatform.bch.sonar.plugin;

import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bdechateauvieux on 5/12/15.
 */
public class ExoJavaRulesPlugin extends SonarPlugin {

    @Override
    public List getExtensions() {
        return Arrays.asList(
                // server extensions -> objects are instantiated during server startup
                ExoJavaRulesDefinition.class,

                // batch extensions -> objects are instantiated during code analysis
                ExoJavaFileCheckRegistrar.class);
    }
}
