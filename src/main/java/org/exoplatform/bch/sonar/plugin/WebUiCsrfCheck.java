package org.exoplatform.bch.sonar.plugin;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.java.model.expression.LiteralTreeImpl;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

/**
 * Created by bdechateauvieux on 5/12/15.
 */
@Rule(key = "WebUiCsrfCheck",
        name = "WebUI CSRF protection check",
        description = "This rule checks that all the WebUI action listeners have CSRF protection activated",
        tags = {"security"},
        priority = Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.SECURITY_FEATURES)
@SqaleConstantRemediation("10min")
public class WebUiCsrfCheck extends BaseTreeVisitor implements JavaFileScanner {
    private static final String EVENT_CONFIG    = "EventConfig";
    private static final String ISSUE_MESSAGE   = "WebUI CSRF protection must be activated";
    private static final String PHASE           = "phase";
    private static final String CSRF_CHECK      = "csrfCheck";
    private static final String DECODE          = "DECODE";

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    /**
     *  Checks that, if phase is not Phase.DECODE,
     *  annotation must have argument csrfCheck=true
     */
    @Override
    public void visitAnnotation(AnnotationTree annotationTree) {
        if (EVENT_CONFIG.equals(getAnnotationName(annotationTree))) {
            if (isProcessWebUIPhase(annotationTree) && !isCsrfTrue(annotationTree)) {
                context.addIssue(annotationTree, this, ISSUE_MESSAGE);
            }
        }
        super.visitAnnotation(annotationTree);
    }

    private boolean isCsrfTrue(AnnotationTree annotationTree) {
        for (ExpressionTree argument : annotationTree.arguments()) {
            if (argument.is(Tree.Kind.ASSIGNMENT)) {
                AssignmentExpressionTree assExpTree = (AssignmentExpressionTree) argument;

                //Check annotation variable is CSRF
                if (assExpTree.variable().is(Tree.Kind.IDENTIFIER)) {
                    IdentifierTree variable = (IdentifierTree) assExpTree.variable();
                    if (CSRF_CHECK.equalsIgnoreCase(variable.name())) {

                        //Check CSRF value is true
                        if (assExpTree.expression().is(Tree.Kind.BOOLEAN_LITERAL)) {
                            LiteralTreeImpl expression = (LiteralTreeImpl) assExpTree.expression();
                            if (Boolean.valueOf(expression.getToken().getValue())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isProcessWebUIPhase(AnnotationTree annotationTree) {
        for (ExpressionTree argument : annotationTree.arguments()) {
            if (argument.is(Tree.Kind.ASSIGNMENT)) {
                AssignmentExpressionTree assExpTree = (AssignmentExpressionTree) argument;

                //Check annotation variable is PHASE
                if (assExpTree.variable().is(Tree.Kind.IDENTIFIER)) {
                    IdentifierTree variable = (IdentifierTree) assExpTree.variable();
                    if (PHASE.equalsIgnoreCase(variable.name())) {

                        //Check value of Phase argument
                        String phaseValue = ((MemberSelectExpressionTree)assExpTree.expression()).identifier().name();
                        return !DECODE.equalsIgnoreCase(phaseValue);
                    }
                }
            }
        }
        //Process is the default Phase
        //=> return true if no Phase argument is specified
        return true;
    }

    private String getAnnotationName(ExpressionTree initializer) {
        String result = "";
        if (initializer.is(Tree.Kind.ANNOTATION)) {
            Tree annotationType = ((AnnotationTree) initializer).annotationType();
            if (annotationType.is(Tree.Kind.IDENTIFIER)) {
                result = ((IdentifierTree) annotationType).name();
            } else if (annotationType.is(Tree.Kind.MEMBER_SELECT)) {
                result = fullName((MemberSelectExpressionTree) annotationType);
            }
        }
        return result;
    }

    private String fullName(MemberSelectExpressionTree tree) {
        if (tree.expression().is(Tree.Kind.IDENTIFIER)) {
            return ((IdentifierTree) tree.expression()).name() + "." + tree.identifier().name();
        }
        return fullName((MemberSelectExpressionTree) tree.expression()) + "." + tree.identifier().name();
    }
}
