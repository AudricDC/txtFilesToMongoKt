@file:Suppress("SameParameterValue")

import java.io.File



private fun processJson(pathIn:File, index: Int, jsonRawString:String, db:String, collection: String) {
    val jsonString = "[$jsonRawString]"
    val outputFile = pathIn.resolve(File("out\\data$index.json"))
    outputFile.writeText(jsonString)
    val uri = "mongodb://user:pwd@host:port/$db?authSource=myAuthSource"
    val command = "myPathToMongo\\Server\\4.0\\bin\\mongoimport --jsonArray --uri $uri --collection $collection --file ${outputFile.absoluteFile}"
    println("Running command : $command")
    Runtime.getRuntime().exec(command)
    Thread.sleep(100)
}

private fun processFile(pathIn: File, db: String, collection: String) {
    val listDir = pathIn.walk().toList()
    val inputFile = listDir.filter { it.name.endsWith(".txt") }[0]
    val jsonList = inputFile.readText().replace("*", ";").split(Regex("/; \\d+ ;/"))
    jsonList.forEachIndexed { i, element -> processJson(pathIn, i, element, db, collection)}
}

fun main() {
    processFile(File("my\\Input\\Path"), "myDB", "myCol")
}