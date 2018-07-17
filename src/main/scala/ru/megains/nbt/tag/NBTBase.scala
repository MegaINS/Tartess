package ru.megains.nbt.tag

import java.io.{DataInput, DataOutput}

import ru.megains.nbt.NBTType._
import ru.megains.nbt.tag.NBT.NBT

import scala.runtime.BoxedUnit


private[nbt] object NBTBase{

    abstract class NBTBase[T] extends NBT {
        var data:T = _
    }

    class NBTEnd() extends NBTBase[Any] {
        override val nbtType:NBTType = EnumNBTEnd
        override def write(output: DataOutput): Unit = output.writeByte(0)
        override def read(input: DataInput): Unit = {}
        override def toString: String = "NBTEnd()"
    }
    class NBTNull() extends NBTBase[Any] {
        override val nbtType:NBTType = EnumNBTNull
        override def write(output: DataOutput): Unit = {}
        override def read(input: DataInput): Unit = {}
        override def toString: String = "NBTNull()"
    }
    class NBTByte() extends NBTBase[Byte](){
       def this(dataIn:Byte){this();data = dataIn}
        override val nbtType:NBTType = EnumNBTByte
        override def write(output: DataOutput): Unit = output.writeByte(data)
        override def read(input: DataInput): Unit = data = input.readByte()
        override def toString: String = s"NBTByte($data)"
    }
    class NBTBoolean() extends NBTBase[Boolean](){
        def this(dataIn:Boolean){this();data = dataIn}
        override val nbtType:NBTType = EnumNBTBoolean
        override def write(output: DataOutput): Unit = output.writeBoolean(data)
        override def read(input: DataInput): Unit = data = input.readBoolean()
        override def toString: String = s"NBTBoolean($data)"
    }
    class NBTShort() extends NBTBase[Short](){
        def this(dataIn:Short){this();data = dataIn}
        override val nbtType:NBTType = EnumNBTShort
        override def write(output: DataOutput): Unit = output.writeShort(data)
        override def read(input: DataInput): Unit = data = input.readShort()
        override def toString: String = s"NBTShort($data)"
    }
    class NBTInt() extends NBTBase[Int](){
        def this(dataIn:Int){this();data = dataIn}
        override val nbtType:NBTType = EnumNBTInt
        override def write(output: DataOutput): Unit = output.writeInt(data)
        override def read(input: DataInput): Unit = data = input.readInt()
        override def toString: String = s"NBTInt($data)"
    }
    class NBTLong() extends NBTBase[Long](){
        def this(dataIn:Long){this();data = dataIn}
        override val nbtType:NBTType = EnumNBTLong
        override def write(output: DataOutput): Unit = output.writeLong(data)
        override def read(input: DataInput): Unit = data = input.readLong()
        override def toString: String = s"NBTLong($data)"
    }
    class NBTFloat() extends NBTBase[Float](){
        def this(dataIn:Float){this();data = dataIn}
        override val nbtType:NBTType = EnumNBTFloat
        override def write(output: DataOutput): Unit = output.writeFloat(data)
        override def read(input: DataInput): Unit = data = input.readFloat()
        override def toString: String = s"NBTFloat($data)"
    }
    class NBTDouble() extends NBTBase[Double](){
        def this(dataIn:Double){this();data = dataIn}
        override val nbtType:NBTType = EnumNBTDouble
        override def write(output: DataOutput): Unit = output.writeDouble(data)
        override def read(input: DataInput): Unit = data = input.readDouble()
        override def toString: String = s"NBTDouble($data)"
    }
    class NBTString() extends NBTBase[String](){
        def this(dataIn:String){this();data = dataIn}
        override val nbtType:NBTType = EnumNBTString
        override def write(output: DataOutput): Unit = output.writeUTF(data)
        override def read(input: DataInput): Unit = data = input.readUTF()
        override def toString: String = s"NBTString($data)"
    }
    class NBTArrayByte() extends NBTBase[Array[Byte]](){
        def this(dataIn:Array[Byte]){this();data = dataIn}
        override val nbtType:NBTType = EnumNBTArrayByte
        override def write(output: DataOutput): Unit ={
            output.writeInt(data.length)
            output.write(data)
        }
        override def read(input: DataInput): Unit = {
            val length = input.readInt
            data = new Array[Byte](length)
            input.readFully(data)
        }
        override def toString: String = s"NBTArrayByte(${data.length})"
    }
    class NBTArrayShort() extends NBTBase[Array[Short]](){
        def this(dataIn:Array[Short]){this();data = dataIn}
        override val nbtType:NBTType = EnumNBTArrayShort
        override def write(output: DataOutput): Unit ={
            output.writeInt(data.length)
            data.foreach(output.writeShort(_))
        }
        override def read(input: DataInput): Unit = {
            val length = input.readInt
            data = new Array[Short](length)
            for (i<-data.indices){
                data(i) = input.readShort()
            }
        }
        override def toString: String = s"NBTArrayShort(${data.length})"
    }
    class NBTArrayInt() extends NBTBase[Array[Int]](){
        def this(dataIn:Array[Int]){this();data = dataIn}
        override val nbtType:NBTType = EnumNBTArrayInt
        override def write(output: DataOutput): Unit ={
            output.writeInt(data.length)
            data.foreach(output.writeInt)
        }
        override def read(input: DataInput): Unit = {
            val length = input.readInt
            data = new Array[Int](length)
            for (i<-data.indices){
                data(i) = input.readInt()
            }
        }
        override def toString: String = s"NBTArrayInt(${data.length})"
    }

    def apply(data:Any): NBTBase[_] = data match {
        case _:Byte => new NBTByte(data.asInstanceOf[Byte])
        case _:Boolean => new NBTBoolean(data.asInstanceOf[Boolean])
        case _:Short => new NBTShort(data.asInstanceOf[Short])
        case _:Int => new NBTInt(data.asInstanceOf[Int])
        case _:Long => new NBTLong(data.asInstanceOf[Long])
        case _:Float => new NBTFloat(data.asInstanceOf[Float])
        case _:Double => new NBTDouble(data.asInstanceOf[Double])
        case _:String => new NBTString(data.asInstanceOf[String])
        case _:Array[Byte] => new NBTArrayByte(data.asInstanceOf[Array[Byte]])
        case _:Array[Short] => new NBTArrayShort(data.asInstanceOf[Array[Short]])
        case _:Array[Int] => new NBTArrayInt(data.asInstanceOf[Array[Int]])
        case _:BoxedUnit => new NBTEnd()
        case _ => new NBTNull()
    }
}





