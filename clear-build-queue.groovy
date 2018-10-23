import hudson.model.Hudson

def queue = Hudson.instance.queue

println "Queue contains ${queue.items.length} items"
queue.items.each { println "- ${it}" }

if (queue.items) {
  queue.clear()
  println "Queue cleared"
}