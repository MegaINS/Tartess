package ru.megains.old.util;


import org.joml.Vector3f;
import ru.megains.tartess.utils.Vec3f;
import ru.megains.tartess.block.Block;
import ru.megains.tartess.block.data.BlockDirection;
import ru.megains.tartess.block.data.BlockPos;

public class RayTraceResult
{
    /** Used to determine what sub-segment is hit */
    public int subHit = -1;

    /** Used to add extra hit info */
    public Object hitInfo = null;

    public BlockPos blockPos;
    /** What type of ray trace hit was this? 0 = block, 1 = entity */
    public RayTraceResult.Type typeOfHit;
    public BlockDirection sideHit;
    /** The vector position of the hit */
    public Vec3f hitVec;
    public Block block;
    /** The hit entity */
   // public Entity entityHit;

    public RayTraceResult(Vector3f hitVecIn, BlockDirection sideHitIn, BlockPos blockPosIn, Block block)
    {
        this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, blockPosIn,block);
    }

    public RayTraceResult(Vec3f hitVecIn, BlockDirection sideHitIn)
    {
        this.sideHit = sideHitIn;
        this.hitVec = hitVecIn;
     //   this(RayTraceResult.Type.BLOCK, hitVecIn, sideHitIn, null,null);
    }

//    public RayTraceResult(Entity entityIn)
//    {
//        this(entityIn, new Vec3d(entityIn.posX, entityIn.posY, entityIn.posZ));
//    }

    public RayTraceResult(RayTraceResult.Type typeIn, Vector3f hitVecIn, BlockDirection sideHitIn, BlockPos blockPosIn, Block block)
    {
        this.typeOfHit = typeIn;
        this.blockPos = blockPosIn;
        this.sideHit = sideHitIn;
        this.hitVec = new Vec3f(Math.abs((hitVecIn.x% 1+1f) % 1),Math.abs((hitVecIn.y% 1+1f) % 1), Math.abs((hitVecIn.z % 1 +1f)% 1));

        if(sideHit==BlockDirection.UP$.MODULE$&&hitVec.y==0.0){
            hitVec.add(0,1,0);
        }
        if(sideHit==BlockDirection.EAST$.MODULE$&&hitVec.x==0.0){
            hitVec.add(1,0,0);
        }
        if(sideHit==BlockDirection.SOUTH$.MODULE$&&hitVec.z==0.0){
            hitVec.add(0,0,1);
        }

        this.block  =block;
    }

//    public RayTraceResult(Entity entityHitIn, Vec3d hitVecIn)
//    {
//        this.typeOfHit = RayTraceResult.Type.ENTITY;
//        this.entityHit = entityHitIn;
//        this.hitVec = hitVecIn;
//    }

    public BlockPos getBlockWorldPos()
    {
        return this.blockPos;
    }



    public static enum Type
    {
        MISS,
        BLOCK,
        ENTITY;
    }
}