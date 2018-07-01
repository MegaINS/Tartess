package ru.megains.old.graph.text;




import ru.megains.tartess.periphery.Window;

import java.util.HashMap;

public class Hud implements IHud {

    private final HashMap<String, Text> texts;
    private Window window;

    long lastTime;
    public static int frames;
    public Hud(){

        //this.window = megaGame.window;
        texts = new HashMap<>();
        putText("playerPos", new Text("Player position:"));
        putText("playerPos.x",new Text("x:"));
        putText("playerPos.y", new Text("y:"));
        putText("playerPos.z", new Text("z:"));
        putText("BlockWorldPos", new Text("Select block:"));
        putText("BlockWorldPos.name", new Text(""));
        putText("BlockWorldPos.side", new Text(""));
        putText("BlockWorldPos.x", new Text(""));
        putText("BlockWorldPos.y",new Text(""));
        putText("BlockWorldPos.z", new Text(""));
        putText("fps", new Text("FPS: "));
        putText("Memory use", new Text("Memory use: "));

        updatePos();
        lastTime = System.currentTimeMillis();
    }

    @Override
    public HashMap<String, Text> getGameItems() {
        return texts;
    }
   public void putText(String name, Text text){
       texts.put(name,text);
   }

    public Text getText(String name){
        return texts.get(name);
    }

    public void update() {


      //  EntityPlayer player = megaGame.player;


//        if(window.isResized()){
//            updatePos();
//        }
       // getText("playerPos.x").setText("x: " + player.posX());
       // getText("playerPos.y").setText("y: " + player.posY() );
       // getText("playerPos.z").setText("z: " + player.posZ());





        while(System.currentTimeMillis() >= lastTime + 1000L) {
            lastTime += 1000L;
            long usedBytes = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
            getText("fps").setText("FPS: "+ frames);
            getText("Memory use").setText("Memory use: " +usedBytes / 1048576);
        }

//        if(megaGame.result!=ray){
//            ray=megaGame.result;
//            if (ray != null) {
//                BlockWorldPos posB = ray.getBlockWorldPos();
//                Vector3f vec = ray.hitVec;
//                getText("BlockWorldPos").setText("Select block:");
//                getText("BlockWorldPos.name").setText("Name: " + ray.block.name());
//                getText("BlockWorldPos.x").setText("x: " + posB.worldX() + "  " + vec.x );
//                getText("BlockWorldPos.y").setText("y: " + posB.worldY() + "  " + vec.y );
//                getText("BlockWorldPos.z").setText("z: " + posB.worldZ() + "  " + vec.z );
//                getText("BlockWorldPos.side").setText("side: " + ray.sideHit.name());
//            } else {
//                getText("BlockWorldPos").setText("");
//                getText("BlockWorldPos.name").setText("");
//                getText("BlockWorldPos.x").setText("");
//                getText("BlockWorldPos.y").setText("");
//                getText("BlockWorldPos.z").setText("");
//                getText("BlockWorldPos.side").setText("");
//            }
//        }
    }


    public void updatePos(){

        getText("playerPos").setPosition(0, window.height() - 20, 0);
        getText("playerPos.x").setPosition(0, window.height() - 40, 0);
        getText("playerPos.y").setPosition(0, window.height() - 60, 0);
        getText("playerPos.z").setPosition(0, window.height() - 80, 0);
        getText("BlockWorldPos").setPosition(0, window.height() - 100, 0);
        getText("BlockWorldPos.name").setPosition(0, window.height() - 120, 0);
        getText("BlockWorldPos.side").setPosition(0, window.height() - 140, 0);
        getText("BlockWorldPos.x").setPosition(0, window.height() - 160, 0);
        getText("BlockWorldPos.y").setPosition(0, window.height() - 180, 0);
        getText("BlockWorldPos.z").setPosition(0, window.height()- 200, 0);
        getText("Memory use").setPosition(window.width() - 200, window.height()- 20, 0);
        getText("fps").setPosition(window.width()-200, window.height() - 40, 0);

    }
}
