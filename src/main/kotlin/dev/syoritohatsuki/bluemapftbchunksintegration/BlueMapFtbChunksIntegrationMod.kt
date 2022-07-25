package dev.syoritohatsuki.bluemapftbchunksintegration

import com.flowpowered.math.vector.Vector3d
import com.mojang.logging.LogUtils
import de.bluecolored.bluemap.api.BlueMapAPI
import de.bluecolored.bluemap.api.marker.Line
import de.bluecolored.bluemap.fabric.events.PlayerJoinCallback
import dev.ftb.mods.ftbchunks.data.FTBChunksAPI
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import kotlin.random.Random
import kotlin.random.nextUInt

object BlueMapFtbChunksIntegrationMod : ModInitializer {

    private val LOGGER: Logger = LogUtils.getLogger()

    override fun onInitialize() {
        LOGGER.info("Template initialized")

        PlayerJoinCallback.EVENT.register(PlayerJoinCallback { _, _ ->
            BlueMapAPI.getInstance().ifPresent { blueMapAPI ->
                val vectorsWorld = mutableListOf<Vector3d>()
                val vectorsNether = mutableListOf<Vector3d>()
                val vectorsEnd = mutableListOf<Vector3d>()
                val markerAPI = blueMapAPI.markerAPI
                blueMapAPI.maps.forEach {
                    FTBChunksAPI.manager.claimedChunks.forEach { claimedChunkEntry ->
                        if (claimedChunkEntry.value.pos.dimension.value.path.contains(it.id, true)) {
                            when (it.id) {
                                "world" -> {
                                    vectorsWorld.add(
                                        Vector3d(
                                            claimedChunkEntry.value.pos.chunkPos.centerX.toFloat(),
                                            0F,
                                            claimedChunkEntry.value.pos.chunkPos.centerZ.toFloat()
                                        )
                                    )
                                }
                                "nether" -> {
                                    vectorsNether.add(
                                        Vector3d(
                                            claimedChunkEntry.value.pos.chunkPos.centerX.toFloat(),
                                            0F,
                                            claimedChunkEntry.value.pos.chunkPos.centerZ.toFloat()
                                        )
                                    )
                                }
                                "end" -> {
                                    vectorsEnd.add(
                                        Vector3d(
                                            claimedChunkEntry.value.pos.chunkPos.centerX.toFloat(),
                                            0F,
                                            claimedChunkEntry.value.pos.chunkPos.centerZ.toFloat()
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                LOGGER.info(vectorsWorld.toTypedArray().size.toString())
                LOGGER.info(vectorsNether.toTypedArray().size.toString())
                LOGGER.info(vectorsEnd.toTypedArray().size.toString())
                if (vectorsWorld.size >= 2) {
                    markerAPI.createMarkerSet("ftb-world")
                        .createLineMarker(
                            Random(System.currentTimeMillis()).nextUInt().toString(),
                            blueMapAPI.getMap("world").get(),
                            Line(*vectorsWorld.toTypedArray())
                        )
                    markerAPI.save()
                }
                if (vectorsNether.size >= 2) {
                    markerAPI.createMarkerSet("ftb-nether")
                        .createLineMarker(
                            Random(System.currentTimeMillis()).nextUInt().toString(),
                            blueMapAPI.getMap("nether").get(),
                            Line(*vectorsNether.toTypedArray())
                        )
                    markerAPI.save()
                }
                if (vectorsEnd.size >= 2) {
                    markerAPI.createMarkerSet("ftb-end")
                        .createLineMarker(
                            Random(System.currentTimeMillis()).nextUInt().toString(),
                            blueMapAPI.getMap("end").get(),
                            Line(*vectorsEnd.toTypedArray())
                        )
                    markerAPI.save()
                }

            }


//            BlueMapAPI.getInstance().ifPresent { blueMapAPI ->
//                blueMapAPI.markerAPI.apply {
//                    createMarkerSet(serverPlayerEntity.entityName).apply {
//                        FTBChunksAPI.getManager().claimedChunks.forEach {
//                            LOGGER.info("sX: ${it.value.pos.chunkPos.startX} | sZ: ${it.value.pos.chunkPos.startZ}")
//                            LOGGER.info("eX: ${it.value.pos.chunkPos.endX} | eZ: ${it.value.pos.chunkPos.endZ}")
//                            createLineMarker(serverPlayerEntity.entityName, blueMapAPI.getMap("world").get(), Line())
//                        }
//                    }
//                    save()
//                }
//            }
//
//            FTBChunksAPI.getManager().claimedChunks.forEach {
//                LOGGER.info("sX: ${it.value.pos.chunkPos.startX} | sZ: ${it.value.pos.chunkPos.startZ}")
//                LOGGER.info("eX: ${it.value.pos.chunkPos.endX} | eZ: ${it.value.pos.chunkPos.endZ}")
//            }
        })
    }
}

/*
[18:54:50] [Server thread/INFO]: sX: 160 | sZ: 224
[18:54:50] [Server thread/INFO]: eX: 175 | eZ: 239

[18:54:50] [Server thread/INFO]: sX: 176 | sZ: 240
[18:54:50] [Server thread/INFO]: eX: 191 | eZ: 255

[18:54:50] [Server thread/INFO]: sX: 160 | sZ: 240
[18:54:50] [Server thread/INFO]: eX: 175 | eZ: 255

[18:54:50] [Server thread/INFO]: sX: 192 | sZ: 208
[18:54:50] [Server thread/INFO]: eX: 207 | eZ: 223

[18:54:50] [Server thread/INFO]: sX: 176 | sZ: 208
[18:54:50] [Server thread/INFO]: eX: 191 | eZ: 223

[18:54:50] [Server thread/INFO]: sX: 192 | sZ: 224
[18:54:50] [Server thread/INFO]: eX: 207 | eZ: 239

[18:54:50] [Server thread/INFO]: sX: 160 | sZ: 208
[18:54:50] [Server thread/INFO]: eX: 175 | eZ: 223

[18:54:50] [Server thread/INFO]: sX: 192 | sZ: 240
[18:54:50] [Server thread/INFO]: eX: 207 | eZ: 255

[18:54:50] [Server thread/INFO]: sX: 176 | sZ: 224
[18:54:50] [Server thread/INFO]: eX: 191 | eZ: 239
* */