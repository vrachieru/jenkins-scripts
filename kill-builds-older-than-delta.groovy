import hudson.model.FreeStyleBuild
import hudson.model.Job
import hudson.model.Result
import java.util.Calendar
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowRun
import org.jenkinsci.plugins.workflow.support.steps.StageStepExecution

def delta = 24 * 3600 * 1000 // 1 day, in millis
def now = Calendar.getInstance()

Jenkins.instance.getAllItems(Job)
    .findAll { job -> job.isBuilding() }
    .collect { job ->
        job.builds.findAll { run ->
            run.isBuilding() && ((now.getTimeInMillis() - run.getStartTimeInMillis()) > delta)
        } ?: []}
    .sum()
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