package com.shysteph.plugin

import org.gradle.api.tasks.*

class StabilizerExtension {
  @InputFiles
  @OutputFiles
  List<File> moduleFiles

  @InputFiles
  @OutputFiles
  List<File> libraryFiles

  @Input
  @Optional
  String annotationsUrl = 'file://$PROJECT_DIR$/annotations'
}
