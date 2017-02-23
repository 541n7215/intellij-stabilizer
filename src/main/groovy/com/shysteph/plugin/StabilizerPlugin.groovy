package com.shysteph.plugin

import nebula.plugin.release.ReleasePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class StabilizerPlugin implements Plugin<Project> {
  static final String EXTENSION_NAME = 'stabilize'
  static final String TASK_NAME = 'stabilize'

  Project project

    @Override
    void apply(Project project) {
      this.project = project


      final StabilizerExtension stabilizeExtension = project.extensions.create(EXTENSION_NAME, StabilizerExtension)

      final StabilizeTask stabilizeTask = project.tasks.create(TASK_NAME, StabilizeTask)
      stabilizeTask.extension = stabilizeExtension
      stabilizeTask.description = 'Order dependencies in module files and add external annotation support'

      project.plugins.withType(JavaPlugin) {
        project.tasks.build.finalizedBy project.tasks.stabilize
      }


      project.plugins.withType(ReleasePlugin) {
        project.tasks.release.dependsOn project.tasks.stabilize
      }

    }
}
