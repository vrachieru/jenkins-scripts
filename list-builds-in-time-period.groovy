import static java.util.Calendar.*

Calendar after = Calendar.getInstance()
after.set(year: 2017, month: JULY, date: 13, hourOfDay: 0, minute: 0, second: 0)

Calendar before = Calendar.getInstance()
before.set(year: 2017, month: JULY, date: 15, hourOfDay: 0, minute: 0, second: 0)

println "Jobs executed between ${after.time} - ${before.time}"

Jenkins.instance.getAllItems(Job).each { job ->
    job.getBuilds()
        .filter { run -> run.timestamp?.before(before) && run.timestamp?.after(after) }
        .each { run -> 
          println "${job.lastBuild.time}: ${job.name}#${run.number} ${run.result}"
        }
}