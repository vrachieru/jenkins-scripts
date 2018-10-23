import jenkins.model.Jenkins

Jenkins.instance.items.each { job ->
  if(!job.isBuilding()) {
    println("Wiping out workspace for job ${job.name}")
    job.doDoWipeOutWorkspace()
  } else {
    println("Skipping job ${job.name}, currently building")
  }
}
