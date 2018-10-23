import hudson.model.Job
import jenkins.model.Jenkins

Jenkins.instance.getAllItems(Job)
    .collect { job -> job.class.simpleName }
    .countBy { it }
    .each { type, count -> println "${type}: ${count}" }
