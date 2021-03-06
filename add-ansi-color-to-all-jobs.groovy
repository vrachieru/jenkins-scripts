import jenkins.model.*
import hudson.model.*
import hudson.util.*
import hudson.tasks.*
import hudson.maven.*
import hudson.plugins.ansicolor.AnsiColorBuildWrapper

colorMapName = colorMapName ?: "xterm"

Jenkins.instance.getAllItems(Job)
    .findAll { it instanceof FreeStyleProject || it instanceof MavenModuleSet }
    .findAll { ! it.buildWrappersList.collect {it.class}.contains(AnsiColorBuildWrapper.class) }
    .each {
        println "${it.fullName} - ${it.class}"

        def buildWrappers = new DescribableList<BuildWrapper, Descriptor<BuildWrapper>>()
        buildWrappers.add(new AnsiColorBuildWrapper(colorMapName, 0, 0))

        buildWrappers.addAll(it.getBuildWrappersList())
        it.getBuildWrappersList().clear()
        it.getBuildWrappersList().addAll(buildWrappers)

        it.save()
      }