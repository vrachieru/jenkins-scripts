import hudson.model.FreeStyleBuild
import hudson.model.Job
import hudson.model.Result
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowRun
import org.jenkinsci.plugins.workflow.support.steps.StageStepExecution

def pattern = "some-pattern-.*"

Jenkins.instance.getAllItems(Job)
    .findAll { job -> job.isBuilding() && job.name =~ /$pattern/ }
    .each { item ->
        if (item in WorkflowRun) {
            WorkflowRun run = (WorkflowRun) item
            run.doKill() // hard kill
            StageStepExecution.exit(run) // release pipeline concurrency locks
            println "Killed ${run}"
        } else if (item in FreeStyleBuild) {
            FreeStyleBuild run = (FreeStyleBuild) item
            run.executor.interrupt(Result.ABORTED)
            println "Killed ${run}"
        } else {
            println "WARNING: Don't know how to handle ${item.class}"
        }
}