package ru.megains.tartess

import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11
import ru.megains.tartess.block.data.{BlockPos, BlockState}
import ru.megains.tartess.entity.item.EntityItem
import ru.megains.tartess.entity.mob.EntityCube
import ru.megains.tartess.entity.player.EntityPlayer
import ru.megains.tartess.item.ItemBlock
import ru.megains.tartess.item.itemstack.ItemStack
import ru.megains.tartess.periphery.{Keyboard, Mouse, Window}
import ru.megains.tartess.register.Bootstrap
import ru.megains.tartess.renderer.font.FontRender
import ru.megains.tartess.renderer.gui.{GuiInGameMenu, GuiManager}
import ru.megains.tartess.renderer.item.ItemRender
import ru.megains.tartess.renderer.texture.TextureManager
import ru.megains.tartess.renderer.world.{RenderChunk, WorldRenderer}
import ru.megains.tartess.renderer.{Camera, Renderer}
import ru.megains.tartess.utils.EnumActionResult.EnumActionResult
import ru.megains.tartess.utils._
import ru.megains.tartess.world.World

import scala.reflect.io.Directory
import scala.util.Random

class Tartess(clientDir: Directory) extends Logger[Tartess]  {


    var frames: Int = 0
    var lastFrames: Int = 0
    var tick: Int = 0
    val MB: Double = 1024 * 1024
    val TARGET_FPS: Float = 60
    var running = true
    var rightClickDelayTimer:Int = 0
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
    var itemRender:ItemRender = _
    var playerController:PlayerControllerMP = _
    var objectMouseOver: RayTraceResult = _

    var blockSelectPosition: BlockState = _

    def startGame(): Unit = {


        log.info("Start Game")
        log.info("Tartess v0.0.1.2")
        try {
            log.info("Display creating...")
            window.create()
            Mouse.init(window,this)
            Keyboard.init(window,this)
            GL11.glClearColor(0.5f, 0.6f, 0.7f, 1.0F)
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
         itemRender = new ItemRender(this)


        log.info("GuiManager init...")
        guiManager.init()
        world = new World()
        //world = new World(saveLoader.getSaveLoader("world"))
        worldRenderer = new WorldRenderer(world)
        renderer.worldRenderer = worldRenderer
        player = new EntityPlayer
        playerController = new PlayerControllerMP(this)
        world.spawnEntityInWorld(player)
        // guiManager.setGuiScreen(new GuiPlayerSelect())

        camera.setPosition(player.posX/16f, (player.posY + player.levelView)/16f, player.posZ/16f)
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

        for (_ <- 0 until timer.tick) {
            update()
            tick += 1
        }

        render()
        frames += 1

        window.update()

    }

    def update(): Unit = {
        Mouse.update(window)

        cameraInc.set(0, 0, 0)

        if (glfwGetKey(window.id, GLFW_KEY_W) == GLFW_PRESS) cameraInc.z = -1
        if (glfwGetKey(window.id, GLFW_KEY_S) == GLFW_PRESS) cameraInc.z = 1
        if (glfwGetKey(window.id, GLFW_KEY_A) == GLFW_PRESS) cameraInc.x = -1
        if (glfwGetKey(window.id, GLFW_KEY_D) == GLFW_PRESS) cameraInc.x = 1
        if (glfwGetKey(window.id, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) cameraInc.y = -1
        if (glfwGetKey(window.id, GLFW_KEY_SPACE) == GLFW_PRESS) cameraInc.y = 1


        if (glfwGetKey(window.id, GLFW_KEY_O) == GLFW_PRESS){
            val stack: ItemStack = player.inventory.getStackSelect
            if(stack ne null){

                val entityItem = new EntityItem()
                entityItem.setItem(new ItemStack(stack.item))
                entityItem.setPosition(player.posX +Random.nextInt(32)-16,player.posY+8+Random.nextInt(16),player.posZ+Random.nextInt(32)-16)

                world.spawnEntityInWorld(entityItem)

            }


        }
        if (glfwGetKey(window.id, GLFW_KEY_U) == GLFW_PRESS){
            val entityCube = new EntityCube()
            entityCube.setPosition(player.posX + Random.nextInt(50)-25,player.posY+Random.nextInt(50),player.posZ+Random.nextInt(50)-25)
            world.spawnEntityInWorld(entityCube)
        }

        guiManager.tick()



        if (world ne null) {
            world.update()
            if (!guiManager.isGuiScreen) player.turn(Mouse.getDY * -1 toFloat,Mouse.getDX   toFloat)


            player.update(cameraInc.x, cameraInc.y, cameraInc.z)
            camera.setPosition(player.posX/16, (player.posY + player.levelView)/16, player.posZ/16)
            camera.setRotation(player.xRot, player.yRot, 0)


            player.inventory.changeStackSelect(Mouse.getDWheel * -1)


            objectMouseOver = player.rayTrace(20*16, 0.1f)


            if (objectMouseOver != null) {
                worldRenderer.updateBlockMouseOver( world.getBlock(objectMouseOver.blockPos))

                val stack: ItemStack = player.inventory.getStackSelect
                if(stack ne null){
                    blockSelectPosition = stack.item match {
                        case itemBlock:ItemBlock =>itemBlock.block.getSelectPosition(world, player, objectMouseOver)
                        case _ => null
                    }
                } else  blockSelectPosition = null



            }else blockSelectPosition = null

            if (blockSelectPosition != null) {
                worldRenderer.updateBlockSelect(blockSelectPosition)
            }

        }




    }

    def render(): Unit = {
        renderer.render(camera)
    }

    def cleanup(): Unit = {
        log.info("Game stopped...")
        running = false
    }

    def runTickMouse(button: Int, buttonState: Boolean): Unit = {


        if (button == 1 && buttonState) {
            rightClickMouse()
        }

        if (button == 0 && buttonState && objectMouseOver != null) {

            world.setAirBlock(objectMouseOver.blockPos)
        }

    }

    def runTickKeyboard(key: Int, action: Int, mods: Int): Unit = {
        if (action == GLFW_PRESS) {
            key match {
                case GLFW_KEY_E => player.openInventory()
                case GLFW_KEY_ESCAPE => guiManager.setGuiScreen(new GuiInGameMenu())
                case GLFW_KEY_R => worldRenderer.reRenderWorld()
                case GLFW_KEY_N => grabMouseCursor()
                case GLFW_KEY_M => ungrabMouseCursor()
                case GLFW_KEY_L => renderer.isLight = !renderer.isLight
                case _ =>
            }
        }
    }

    def rightClickMouse() {
        val itemstack: ItemStack = player.getHeldItem

        if(objectMouseOver!= null) {
            val blockpos: BlockPos = objectMouseOver.blockPos
            if (!world.isAirBlock(blockpos)) {


                val enumactionresult: EnumActionResult = playerController.processRightClickBlock(player, world, itemstack, blockpos, objectMouseOver.sideHit, objectMouseOver.hitVec)
                if (enumactionresult eq EnumActionResult.SUCCESS) {
                    if (itemstack != null) if (itemstack.stackSize == 0) player.setHeldItem(null)
                    return
                }
            }
        }

        if (itemstack != null && (playerController.processRightClick(player, world, itemstack) eq EnumActionResult.SUCCESS)) {
            return
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
