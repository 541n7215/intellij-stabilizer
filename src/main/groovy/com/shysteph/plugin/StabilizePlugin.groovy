package com.shysteph.plugin

import nebula.plugin.release.ReleasePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class StabilizePlugin implements Plugin<Project> {
  static final String EXTENSION_NAME = 'stabilize'
  static final String TASK_NAME = 'stabilize'

  Project project

    @Override
    void apply(Project project) {
      this.project = project


      final StabilizeConfig config = project.extensions.create(EXTENSION_NAME, StabilizeConfig)

      final StabilizeTask stabilizeTask = project.tasks.create(TASK_NAME, StabilizeTask)
      stabilizeTask.config = config
      stabilizeTask.description = 'Order dependencies in module files and add external annotation support'

      project.plugins.withType(JavaPlugin) {
        project.tasks.build.finalizedBy project.tasks.stabilize
      }


      project.plugins.withType(ReleasePlugin) {
        project.tasks.release.dependsOn project.tasks.stabilize
      }

    }
}
