package com.shysteph.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class StabilizeTask extends DefaultTask {


  private static String getXml(Node node) {
    //serialize the document back into xml as close as we can to how we got it
    def writer = new StringWriter()
    def nodePrinter = new XmlNodePrinter(new PrintWriter(writer))
    nodePrinter.setNamespaceAware(false)
    nodePrinter.setPreserveWhitespace(true)
    nodePrinter.print(node)

    //IntelliJ starts the module with a prolog, ends in a blank line and likes spaces before its end tags.  Try to match
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + writer.toString().replaceAll('(\\S)/>', '$1 />') + "\n"
  }

  @TaskAction
  void stabilize() {
    project.stabilizer.moduleFiles.each { File file ->
      def fileText = file.text
      def root = new XmlParser().parse(file)

      //getthe component that holds all of the libraries
      def newModuleRootManager = root.component.find { it.'@name' == 'NewModuleRootManager' }

      //<orderEntry> can represent a variety of classpath components, we want type=library to get gradle dependencies
      newModuleRootManager.findAll { orderEntry -> orderEntry.'@type' == 'library' }
        .sort { a, b -> a.'@scope' == b.'@scope' ? a.'@name' <=> b.'@name' : a.'@scope' <=> b.'@scope' }
        .each {
        //pull dependencies out from whatever order they are in and append them in the desired order
        newModuleRootManager.remove(it)
        newModuleRootManager.append(it)
      }

      //compare so we know if we did any actual work
      def newText = getXml(root)
      if (fileText != newText) {
        this.didWork = true
        file.text = newText
      }
    }

    project.stabilizer.libraryFiles.each { File file ->
      def fileText = file.text
      def root = new XmlParser().parse(file)

      root.library.each { Node library ->
        //check for existing external annotations configuration.  If we see it assume we/someone else has already configuredthis
        if (library.ANNOTATIONS.isEmpty()) {
          def annotation = new Node(null, 'ANNOTATIONS', [])
          //use the children list instead of append because we need to be at the beginning
          library.children().add(0, annotation)


          annotation.append(new Node(null, 'root', [url: project.stabilizer.annotationsUrl]))

          def newText = getXml(root)

          //compare so we know if we did any actual work
          if(fileText != newText) {
            this.didWork = true
            file.text = newText
          }
        }
      }
    }
  }
}
