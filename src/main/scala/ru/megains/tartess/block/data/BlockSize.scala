package ru.megains.tartess.block.data

class BlockSize(val x: Int = 0, val y: Int = 0, val z: Int = 0) {
    def this(size:Int){
        this(size,size,size)
    }


}
