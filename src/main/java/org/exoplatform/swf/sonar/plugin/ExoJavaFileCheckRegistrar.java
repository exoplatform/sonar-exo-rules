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
package org.exoplatform.swf.sonar.plugin;

import org.exoplatform.swf.sonar.plugin.rules.RepositoryServiceUsageCheck;
import org.exoplatform.swf.sonar.plugin.rules.WebUiCsrfCheck;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;

import java.util.Arrays;

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
        return new Class[] {WebUiCsrfCheck.class,
                            RepositoryServiceUsageCheck.class};
    }

}
