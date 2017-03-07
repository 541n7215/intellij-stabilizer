package com.shysteph.plugin

import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFiles

class StabilizeConfig {
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

  def moduleFiles(FileCollection files) {
    moduleFiles.add(files)
  }

  def libraryFiles(FileCollection files) {
  libraryFiles.add(files)
  }

  def gradleExtensionFiles(FileCollection files) {
    gradleExtensionFiles.add(files)
  }

  def annotationsUrl(String url) {
    annotationsUrl = url;
  }
}
