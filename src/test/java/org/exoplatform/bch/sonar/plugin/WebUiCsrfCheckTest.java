package org.exoplatform.bch.sonar.plugin;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.java.JavaAstScanner;
import org.sonar.java.model.VisitorsBridge;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

import java.io.File;

/**
 * Created by bdechateauvieux on 5/13/15.
 */
public class WebUiCsrfCheckTest {

    @Rule
    public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

    @Test
    public void missingCsrfDetected() {
        //Given
        WebUiCsrfCheck check = new WebUiCsrfCheck();
        //When
        SourceFile file = JavaAstScanner
                .scanSingleFile(new File("src/test/resources/UIExperienceSection.java"), new VisitorsBridge(check));

        //Then
        checkMessagesVerifier.verify(file.getCheckMessages())
                .next().atLine(66).withMessage("WebUI CSRF protection must be activated")
                .next().atLine(67).withMessage("WebUI CSRF protection must be activated")
                .next().atLine(68).withMessage("WebUI CSRF protection must be activated");
    }

    @Test
    public void noMissingCsrfDetected() {
        //Given
        WebUiCsrfCheck check = new WebUiCsrfCheck();
        //When
        SourceFile file = JavaAstScanner
                .scanSingleFile(new File("src/test/resources/UIBasicInfoSection.java"), new VisitorsBridge(check));

        //Then
        checkMessagesVerifier.verify(file.getCheckMessages()).noMore();
    }
}
