package dev.syoritohatsuki.bluemapftbchunksintegration.util

import com.flowpowered.math.vector.Vector2d
import dev.ftb.mods.ftbchunks.data.FTBChunksAPI
import net.minecraft.server.network.ServerPlayerEntity

object FTBChunksUtil {
    private val ftbChunksAPI = FTBChunksAPI.manager

    fun getOverWorldPlayerChunks(serverPlayerEntity: ServerPlayerEntity): List<Vector2d> {
        return mutableListOf<Vector2d>().apply {
            val xCord: MutableSet<Int> = mutableSetOf()
            val zCord: MutableSet<Int> = mutableSetOf()

            ftbChunksAPI.getData(serverPlayerEntity).claimedChunks.forEach {
                if (it.pos.dimension.value.path.contains("world")) {
                    xCord.add(it.pos.chunkPos.startX)
                    xCord.add(it.pos.chunkPos.endX)

                    zCord.add(it.pos.chunkPos.startZ)
                    zCord.add(it.pos.chunkPos.endZ)
                }
            }
            add(Vector2d(xCord.minBy { it }.toFloat(), zCord.minBy { it }.toFloat()))
            add(Vector2d(xCord.maxBy { it }.toFloat() + 1, zCord.maxBy { it }.toFloat() + 1))
        }
    }
}