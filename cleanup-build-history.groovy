import hudson.model.Job
import jenkins.model.Jenkins

def MAX_BUILDS = 5 // max builds to keep

Jenkins.instance.getAllItems(Job).each { job ->
    job.builds.drop(MAX_BUILDS).findAll {
        !it.keepLog &&
        !it.building &&
        it != job.lastStableBuild &&
        it != job.lastSuccessfulBuild &&
        it != job.lastUnstableBuild &&
        it != job.lastUnsuccessfulBuild
    }.each { build ->
        build.delete()
    }
}