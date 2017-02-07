package com.shysteph.plugin

import nebula.plugin.release.ReleasePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class StabilizerPlugin implements Plugin<Project> {
  static final String EXTENSION_NAME = 'stabilizer'
  static final String TASK_NAME = 'stabilize'

  Project project

    @Override
    void apply(Project project) {
      this.project = project

      project.extensions.create(EXTENSION_NAME, StabilizerExtension)

      def stabilize = project.tasks.create(TASK_NAME, StabilizeTask)
      stabilize.description = 'Order dependencies in module files and add external annotation support'


      project.plugins.withType(JavaPlugin) {
        project.plugins.tasks.build.finalizedBy project.plugins.tasks.stabilize
      }

      project.plugins.withType(ReleasePlugin) {
        project.plugins.tasks.release.dependsOn project.plugins.tasks.stabilize
      }

    }
}
