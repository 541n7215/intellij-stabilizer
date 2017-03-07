## Synopsis

Orders dependencies in your .idea/module/.iml files and configure dependencies to support external annotations

## Code Example

plugins {
  id 'com.shysteph.intellij-stabilizer' version '1.5.0'
}
  
## Motivation

I like to use the IntelliJ Idea directory layout and there is a lot that ends up in the modules/.iml files, especially facet configurations.  Without a plugin to create them new project setup is a pain.  JetBrains recommends including them in VCS but they are change heavy.
Most of the changes are not material changes.  For some reason IntelliJ likes to randomly reorder entries for dependencies, in the form of <orderEntry> elements, and this causes a lot of unneeded change.
I realized if they were just in alphabetical order all the time my issues would go away.  It would also help assure a more stable classpath order since I think it is generated out of the same list.

I also like to use external annotations for third party libraries.  I like the (@Nullable/@NotNull) annotations to be visible in my code but can't and don't care how libraries are annotated.  Unfortunately the configuration for external annotations is either global or per library.
The plugin just checks each library/.xml file and if its missing an <ANNOTATIONS> element it puts one in with a root element configured to save the annotations in an annotations directory in project root.
## Installation

Use the plugin via the plugins {} feature or the older style one.
Point it at your module/library files.

There is some configurations available/required:
    stabilize {
      moduleFiles = file('.idea/modules').listFiles().findAll{File file -> file.getName().endsWith('.iml')}
      
      libraryFiles = file('.idea/libraries').listFiles().findAll{File file -> file.getName().endsWith('.xml')}
      
      //you currently have to make this directory yourself
      annotationsUrl = 'file://$PROJECT_DIR$/annotations'
    }
  
## Contributors

Myself.  Most of the build configuration to publish this plugin was HEAVILY inspired by the netflix nebula plugins.

## License

 Copyright 2017 Stephanie Miller

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

