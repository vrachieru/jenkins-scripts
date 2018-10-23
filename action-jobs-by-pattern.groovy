import jenkins.model.Jenkins

def pattern = "some-pattern-.*"
def action = "enable" // "enable", "disable", "scheduleBuild", or "delete"; case-sensitive.

Jenkins.instance.items
    .findAll { job -> job.name =~ /$pattern/ }
    .each { job ->
        println "Triggering action '${action}' for matching job '${job.name}'"
        job."$action"()
    }
