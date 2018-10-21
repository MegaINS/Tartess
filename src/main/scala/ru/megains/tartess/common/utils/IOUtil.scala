package ru.megains.tartess.common.utils

import java.nio.ByteBuffer
import java.nio.channels.Channels
import java.nio.file.{Files, Paths}
import java.util.Scanner

import org.lwjgl.BufferUtils
import org.lwjgl.BufferUtils.createByteBuffer

object IOUtil {

    private def resizeBuffer(buffer: ByteBuffer, newCapacity: Int) = {
        buffer.flip
        BufferUtils.createByteBuffer(newCapacity).put(buffer)
    }


    def ioResourceToByteBuffer(resource: String, bufferSize: Int): ByteBuffer = {
        var buffer: ByteBuffer = null
        val path = Paths.get(resource)
        if (Files.isReadable(path)) try {
            val fc = Files.newByteChannel(path)
            try {
                buffer = BufferUtils.createByteBuffer(fc.size.toInt + 1)
                while (fc.read(buffer) != -1){}
            } finally if (fc != null) fc.close()
        }
        else {
            try {

                val source =  IOUtil.getClass.getClassLoader.getResourceAsStream(resource)
                val rbc = Channels.newChannel(source)
                try {
                    buffer = createByteBuffer(bufferSize)
                    var run = true
                    while (run) {
                        val bytes = rbc.read(buffer)
                        if (bytes == -1) run = false
                        if (buffer.remaining == 0) buffer = resizeBuffer(buffer, buffer.capacity * 2)

                    }

                } finally {
                    if (source != null) source.close()
                    if (rbc != null) rbc.close()
                }
            }
        }
        buffer.flip
        buffer
    }


    def loadResource(fileName: String): String = {
        var result: String = null
        try {
            val in = IOUtil.getClass.getResourceAsStream(fileName)
            try result = new Scanner(in, "UTF-8").useDelimiter("\\A").next
            finally if (in != null) in.close()
        }
        result
    }
}
