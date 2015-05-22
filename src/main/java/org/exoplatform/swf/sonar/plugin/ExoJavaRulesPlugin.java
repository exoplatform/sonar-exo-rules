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

import org.sonar.api.SonarPlugin;

import java.util.Arrays;
import java.util.List;

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
