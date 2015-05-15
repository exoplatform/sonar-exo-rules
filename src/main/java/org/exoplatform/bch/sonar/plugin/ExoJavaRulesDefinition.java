package org.exoplatform.bch.sonar.plugin;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;

/**
 * Created by bdechateauvieux on 5/12/15.
 */
public class ExoJavaRulesDefinition implements RulesDefinition {

    public static final String REPOSITORY_KEY = "exo-java-rules";

    @Override
    public void define(Context context) {
        RulesDefinition.NewRepository repo = context.createRepository(REPOSITORY_KEY, "java");
        repo.setName("eXo Java Rules");

        RulesDefinitionAnnotationLoader annotationLoader = new RulesDefinitionAnnotationLoader();
        annotationLoader.load(repo, ExoJavaFileCheckRegistrar.checkClasses());
        repo.done();
    }
}
