package ru.megains.tartess

import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11
import ru.megains.old.TartessClient
import ru.megains.old.entity.player.EntityPlayer
import ru.megains.old.periphery.{Keyboard, Mouse, Window}
import ru.megains.old.util.RayTraceResult
import ru.megains.old.utils.{Logger, Timer, Vec3f}
import ru.megains.tartess.register.Bootstrap
import ru.megains.tartess.renderer.font.FontRender
import ru.megains.tartess.renderer.gui.GuiManager
import ru.megains.tartess.renderer.texture.TextureManager
import ru.megains.tartess.renderer.world.{RenderChunk, WorldRenderer}
import ru.megains.tartess.renderer.{Camera, Renderer}
import ru.megains.tartess.world.World

class Tartess extends Logger[TartessClient]  {


    var frames: Int = 0
    var lastFrames: Int = 0
    var tick: Int = 0
    val MB: Double = 1024 * 1024
    val TARGET_FPS: Float = 60
    var running = true

    val timer: Timer = new Timer(20)

    val window: Window = new Window()

    var renderer:Renderer  = _
    var worldRenderer: WorldRenderer = _

    var world:World = _
    var player: EntityPlayer = _

    var camera: Camera = _
    var cameraInc: Vec3f = _

    var guiManager: GuiManager = _
    var fontRender:FontRender =_

    var objectMouseOver: RayTraceResult = _
    def startGame(): Unit = {


        log.info("Start Game")
        log.info("Tartess v0.1.0")
        try {
            log.info("Display creating...")
            window.create()
            Mouse.init(window)
            Keyboard.init(window,this)
            GL11.glClearColor(0.5f, 0.6f, 0.7f, 0.0F)
            log.info("Display create successful")
        } catch {
            case e: RuntimeException =>
                e.printStackTrace()
                System.exit(-1000)
        }



        Bootstrap.init()

        log.info("Renderer creating...")
        renderer = new Renderer(this)

        log.info("Camera creating...")
        camera = new Camera
        cameraInc = new Vec3f()
        log.info("GuiManager creating...")
        guiManager = new GuiManager(this)


        log.info("FontRender creating...")
        fontRender = new FontRender(this)

        renderer.init()
        log.info("TextureManager loadTexture...")
        TextureManager.tm.loadTexture(TextureManager.locationBlockTexture, TextureManager.tm.textureMapBlock)
        // GameRegister.tileEntityData.idRender.values.foreach(_.init())

        log.info("RenderItem creating...")
        // itemRender = new RenderItem(this)


        log.info("GuiManager init...")
        guiManager.init()
        world = new World()
        // world = new World(saveLoader.getSaveLoader("world"))
        worldRenderer = new WorldRenderer(world)
        renderer.worldRenderer = worldRenderer
        player = new EntityPlayer
        player.setWorld(world)
        //world.init()
       // worldRenderer.init()
        // playerController = new PlayerControllerMP(this)
        // world.spawnEntityInWorld(player)
        // guiManager.setGuiScreen(new GuiPlayerSelect())

        camera.setPosition(player.posX, player.posY + player.levelView, player.posZ)
        camera.setRotation(player.xRot, player.yRot, 0)
    }



    def run(): Unit = {
        try {
            startGame()
        } catch {
            case e: Exception => log.fatal(e.printStackTrace())
                running = false

        }

        var lastTime: Long = System.currentTimeMillis
        timer.init()
        try {
            while (running) {


                runGameLoop()
                while (System.currentTimeMillis >= lastTime + 1000L) {
                     log.info(s"$frames fps, $tick tick, ${RenderChunk.chunkRender / (if (frames == 0) 1 else frames)} chunkRender, ${RenderChunk.chunkUpdate} chunkUpdate")

                    RenderChunk.chunkRender = 0
                    RenderChunk.chunkUpdate = 0
                    lastTime += 1000L
                    lastFrames = frames
                    frames = 0
                    tick = 0
                    // printMemoryUsage()
                }
            }
        } catch {
            case e: Exception => log.fatal("Crash", e)
        } finally {
            cleanup()
        }
    }

    def runGameLoop(): Unit = {

        if (window.isClose) running = false

        timer.update()

        for (_ <- 0 until timer.getTick) {
            update()
            tick += 1
        }

        render()
        frames += 1

        window.update()

    }

    private def update(): Unit = {
        Mouse.update(window)

        cameraInc.set(0, 0, 0)

        if (glfwGetKey(window.id, GLFW_KEY_W) == GLFW_PRESS) cameraInc.z = -1
        if (glfwGetKey(window.id, GLFW_KEY_S) == GLFW_PRESS) cameraInc.z = 1
        if (glfwGetKey(window.id, GLFW_KEY_A) == GLFW_PRESS) cameraInc.x = -1
        if (glfwGetKey(window.id, GLFW_KEY_D) == GLFW_PRESS) cameraInc.x = 1
        if (glfwGetKey(window.id, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) cameraInc.y = -1
        if (glfwGetKey(window.id, GLFW_KEY_SPACE) == GLFW_PRESS) cameraInc.y = 1

        player.turn( Mouse.getDY * -1 toFloat,Mouse.getDX   toFloat)


        player.update(cameraInc.x, cameraInc.y, cameraInc.z)

        camera.setPosition(player.posX/8, (player.posY + player.levelView)/8, player.posZ/8)
        camera.setRotation(player.xRot, player.yRot, 0)

        guiManager.tick()


        objectMouseOver = player.rayTrace(20, 0.1f)





        if (objectMouseOver != null) {
            worldRenderer.updateBlockMouseOver(objectMouseOver.blockPos, world.getBlock(objectMouseOver.blockPos))

//            val stack: ItemStack = player.inventory.getStackSelect
//            if(stack ne null){
//                blockSelectPosition = stack.item match {
//                    case itemBlock:ItemBlock =>itemBlock.block.getSelectPosition(world,player, objectMouseOver)
//                    case _ => null
//                }
//            } else  blockSelectPosition = null



        }//else blockSelectPosition = null

    }

    private def render(): Unit = {
        renderer.render(camera)
    }

    private def cleanup(): Unit = {
        log.info("Game stopped...")
        running = false
    }


    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {
        if (action == GLFW_PRESS) {
            key match {
                //    case GLFW_KEY_E => player.openInventory()
                //    case GLFW_KEY_ESCAPE => guiManager.setGuiScreen(new GuiInGameMenu())
                //    case GLFW_KEY_R => worldRenderer.reRenderWorld()
                case GLFW_KEY_N => grabMouseCursor()
                case GLFW_KEY_M => ungrabMouseCursor()
                case GLFW_KEY_ESCAPE=> ungrabMouseCursor()
                case _ =>
            }
        }
    }

    def grabMouseCursor(): Unit = {
        Mouse.setGrabbed(true)
    }

    def ungrabMouseCursor(): Unit = {
        //TODO  Mouse.setCursorPosition(Display.getWidth / 2, Display.getHeight / 2)
        Mouse.setGrabbed(false)
    }
}

object Tartess {
    var tartess: Tartess = _
}
