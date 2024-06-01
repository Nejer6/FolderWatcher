package com.github.nejer6

import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds

fun main() {
    val directoryPath = Paths.get("test_folder")
    val newFilePath = directoryPath.resolve("new_file.txt")

    val watchService = FileSystems.getDefault().newWatchService()
    directoryPath.register(watchService, StandardWatchEventKinds.ENTRY_DELETE)

    while (true) {
        val key = watchService.take()
        key.pollEvents().forEach { _ ->
            val files = directoryPath.toFile().listFiles()
            if (files != null && files.size == 1) {
                createNewFile(newFilePath)
            }
        }
        if (!key.reset()) {
            break
        }
    }
}

fun createNewFile(path: Path) {
    try {
        val file = File(path.toString())
        if (!file.exists()) {
            file.createNewFile()
            println("New file created: ${file.absolutePath}")
        } else {
            println("File already exists: ${file.absolutePath}")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
