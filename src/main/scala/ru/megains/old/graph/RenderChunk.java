package ru.megains.old.graph;


import ru.megains.old.block.Block;
import ru.megains.old.blockdata.BlockWorldPos;
import ru.megains.old.graph.renderer.mesh.Mesh;
import ru.megains.old.graph.renderer.mesh.MeshMaker;
import ru.megains.old.physics.AxisAlignedBB;
import ru.megains.old.register.GameRegister;
import ru.megains.old.world.World;
import ru.megains.old.world.chunk.Chunk;
import ru.megains.tartess.renderer.texture.TextureManager;

public class RenderChunk {

    public static int  chunkSize =16;
    private static int rend =0;
    public static int chunkRender =0;
    public static int chunkUpdate =0;
    private boolean isReRender = true;
    public final Chunk chunk;
    private AxisAlignedBB cube;
    private int blockRender;
    private Mesh[] meshes = new Mesh[2];
    private World world;
    private int minX ;
    private int minY ;
    private int minZ ;
    private TextureManager textureManager;


    public RenderChunk(Chunk chunk, TextureManager textureManager){
        this.textureManager =textureManager;
       this.chunk = chunk;
        this.world = chunk.world();
         minX =chunk.position().minX();
         minY =chunk.position().minY();
         minZ =chunk.position().minZ();
        cube = new AxisAlignedBB(minX,minY,minZ,chunk.position().maxX(),chunk.position().maxY(),chunk.position().maxZ());
    }


    public void render(int layer){
        if(isReRender){
           if(rend<1) {
               rend++;
               blockRender =0;
               cleanUp();
               reRenderChunk(0);
               isReRender = false;
               chunkUpdate++;
           }
       }
        renderChunk(layer);
    }

    private void renderChunk(int layer){

        if(blockRender!=0) {
            if(meshes[layer]!=null){
                meshes[layer].render(textureManager);
                chunkRender++;
            }

        }
    }
    private void reRenderChunk(int layer){


        MeshMaker.startMakeTriangles();
        MeshMaker.setTexture(TextureManager.locationBlockTexture());
        BlockWorldPos blockPos ;
        BlockWorldPos renderPos ;
        Block block;


        for (int x = 0; x <  chunkSize; ++x) {
            for (int y = 0; y <   chunkSize; ++y) {
                for (int z = 0; z <  chunkSize; ++z) {
                    blockPos= new BlockWorldPos(x+minX,y+minY,z+minZ);
                    renderPos = new BlockWorldPos(x, y, z);
                    if(!world.isAirBlock(blockPos)) {
                        block = chunk.getBlockWorldCord(blockPos);
                        GameRegister.getBlockRender(block).render(block, world, blockPos, renderPos);
                        blockRender += chunk.isAirBlockWorldCord(blockPos) ? 0 : 1;
                               // block.renderBlocks(world, blockPos, renderPos);
                    }
                }
            }
        }

        meshes[layer] = MeshMaker.makeMesh();
}



    public void cleanUp() {
        for(Mesh mesh:meshes){
            if(mesh!=null){
                mesh.cleanUp();
            }
        }

    }


    public void reRender(){

        isReRender =true;
    }

    public static void clearRend() {
            rend = 0;
    }

    public AxisAlignedBB getCube() {
        return cube;
    }



}
