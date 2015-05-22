/*
 * Copyright (C) 2003-2010 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.swf.sonar.plugin.rules;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.java.JavaAstScanner;
import org.sonar.java.model.VisitorsBridge;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;

import java.io.File;

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
