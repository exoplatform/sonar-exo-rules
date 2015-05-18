package org.exoplatform.swf.sonar.plugin.rules;

/**
 * Created by bdechateauvieux on 5/15/15.
 */

import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.*;

/**
 *
 */
@Rule(key = "RepositoryServiceUsage",
        name = "Avoid using RepositoryService.getDefaultRepository()",
        description = "In order to support multi-tenancy, only RepositoryService.getCurrentRepository() must be used.",
        tags = {"multi-tenancy"},
        priority = Priority.BLOCKER
)
public class RepositoryServiceUsageCheck extends BaseTreeVisitor implements JavaFileScanner {
    private static final String GET_DEFAULT_REPO_METHOD_NAME = "getDefaultRepository";
    private static final String GET_DEFAULT_REPO_CLASS_NAME  = "RepositoryService";

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitMethodInvocation(MethodInvocationTree tree) {
        if (tree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
            MemberSelectExpressionTree mset = (MemberSelectExpressionTree) tree.methodSelect();
            if (GET_DEFAULT_REPO_METHOD_NAME.equals(mset.identifier().name())) {
                if (mset.expression().is(Tree.Kind.IDENTIFIER)) {
                    Symbol symbol = ((IdentifierTree) mset.expression()).symbol();
                    if (symbol.declaration().is(Tree.Kind.VARIABLE)) {
                        VariableTree variable = (VariableTree) symbol.declaration();
                        if (variable.type().is(Tree.Kind.IDENTIFIER)) {
                            String variableClassName = ((IdentifierTree) variable.type()).name();
                            if (GET_DEFAULT_REPO_CLASS_NAME.equals(variableClassName)) {
                                context.addIssue(tree, this, "Don't use RepositoryService.getDefaultRepository()");
                            }
                        }
                    }
                }
            }
        }
        super.visitMethodInvocation(tree);
    }

}
