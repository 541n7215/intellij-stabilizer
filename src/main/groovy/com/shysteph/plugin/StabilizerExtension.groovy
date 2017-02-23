package com.shysteph.plugin

import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFiles

class StabilizerExtension {
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
}
