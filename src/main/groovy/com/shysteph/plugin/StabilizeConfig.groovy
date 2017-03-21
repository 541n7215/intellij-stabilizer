package com.shysteph.plugin

import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFiles

class StabilizeConfig {
  private Project project

  @InputFiles
  @OutputFiles
  FileCollection moduleFiles

  @InputFiles
  @OutputFiles
  FileCollection libraryFiles

  @InputFiles
  @OutputFiles
  FileCollection gradleExtensionFiles

  @Input
  @Optional
  String annotationsUrl = 'file://$PROJECT_DIR$/annotations'

  StabilizeConfig(Project project) {
    this.project = project
    this.moduleFiles = project.files('.idea/modules/**/*.iml')
    this.libraryFiles = project.files('.idea/libraries/**/*.xml')
    this.gradleExtensionFiles = project.files('.idea/gradle_extensions.xml')
  }

}
