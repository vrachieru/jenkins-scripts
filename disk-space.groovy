columns = ["path", "size", "used", "free", "used %"]
println columns.join("\t")

File.listRoots().each {
  columns = []
  columns << it.getAbsolutePath()
  columns << Math.ceil(it.totalSpace / 1024 / 1024)
  columns << Math.ceil((it.totalSpace - it.freeSpace) / 1024 / 1024)
  columns << Math.ceil(it.freeSpace / 1024 / 1024)
  columns << "${Math.ceil((it.totalSpace - it.freeSpace) * 100 / it.totalSpace)} %"

  println columns.join("\t")
}