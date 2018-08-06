package ru.megains.tartess.server

import java.util.concurrent.Semaphore

class ServerStatusResponse {
    private var description: String = null
    private var version: ServerStatusResponse.Version = null
    private var favicon: String = null

    def getServerDescription: String = {
        description
    }

    def setServerDescription(descriptionIn: String) {
        this.description = descriptionIn
        invalidateJson()
    }



    def getVersion: ServerStatusResponse.Version = {
        return this.version
    }

    def setVersion(versionIn: ServerStatusResponse.Version) {
        this.version = versionIn
        invalidateJson()
    }

    def setFavicon(faviconBlob: String) {
        this.favicon = faviconBlob
        invalidateJson()
    }

    def getFavicon: String = {
        return this.favicon
    }

    private val mutex: Semaphore = new Semaphore(1)
    private var json: String = null


    def invalidateJson() {
        this.json = null
    }
}

object ServerStatusResponse {



    class Version(val name: String, val protocol: Int) {
        def getName: String = this.name

        def getProtocol: Int = this.protocol
    }

}

