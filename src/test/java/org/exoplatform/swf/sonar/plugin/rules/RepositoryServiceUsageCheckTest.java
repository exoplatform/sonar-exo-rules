package org.exoplatform.swf.sonar.plugin.rules;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.java.JavaAstScanner;
import org.sonar.java.model.VisitorsBridge;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

import java.io.File;

/**
 * Created by bdechateauvieux on 5/15/15.
 */
public class RepositoryServiceUsageCheckTest {

    @Rule
    public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

    @Test
    public void useOfGetDefaultRepositoryDetected() {
        //Given
        RepositoryServiceUsageCheck check = new RepositoryServiceUsageCheck();
        //When
        SourceFile file = JavaAstScanner
                .scanSingleFile(new File("src/test/resources/repositoryservice/AvatarAttachment.java"), new VisitorsBridge(check));

        //Then
        checkMessagesVerifier.verify(file.getCheckMessages())
                .next().atLine(276).withMessage("Don't use RepositoryService.getDefaultRepository()")
                .next().atLine(288).withMessage("Don't use RepositoryService.getRepository(name)");
    }

    @Test
    public void useOfGetRepositoryByNameDetected() {
        //Given
        RepositoryServiceUsageCheck check = new RepositoryServiceUsageCheck();
        //When
        SourceFile file = JavaAstScanner
                .scanSingleFile(new File("src/test/resources/repositoryservice/AvatarAttachment.java"), new VisitorsBridge(check));

        //Then
        checkMessagesVerifier.verify(file.getCheckMessages())
                .next().atLine(276).withMessage("Don't use RepositoryService.getDefaultRepository()")
                .next().atLine(288).withMessage("Don't use RepositoryService.getRepository(name)");
    }
}
