package org.exoplatform.bch.sonar.plugin;

import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.Arrays;

/**
 * Created by bdechateauvieux on 5/12/15.
 */
public class ExoJavaFileCheckRegistrar implements CheckRegistrar {

    /**
     * Register the classes that will be used to instantiate checks during analysis.
     */
    @Override
    public void register(CheckRegistrar.RegistrarContext registrarContext) {
        //Call to registerClassesForRepository to associate the classes with the correct repository key
        registrarContext.registerClassesForRepository(ExoJavaRulesDefinition.REPOSITORY_KEY, Arrays.asList(checkClasses()));
    }

    /**
     * Lists all the checks provided by the plugin
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends JavaCheck>[] checkClasses() {
        return new Class[] {WebUiCsrfCheck.class};
    }

}
