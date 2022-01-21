package at.moja05.simpleSinglePlayerSleep

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.command.CommandOutput
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.command.TimeCommand
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec2f
import net.minecraft.util.math.Vec3d

@Suppress("UNUSED")
object SimpleSinglePlayerSleep: ModInitializer {
    private const val MOD_ID = "simpleSinglePlayerSleep"
    override fun onInitialize() {
        println("Simple Single Player Sleep has been initialized.")
        EntitySleepEvents.START_SLEEPING.register(EntitySleepEvents.StartSleeping {entity: LivingEntity, sleepingPos: BlockPos ->
            //println("Detected Start_Sleep event")
            //println(entity.toString())
            if (entity is PlayerEntity) {
                val serverCommandSource = ServerCommandSource(CommandOutput.DUMMY,
                    Vec3d(sleepingPos.x.toDouble(),sleepingPos.y.toDouble(),sleepingPos.z.toDouble()),
                    Vec2f(sleepingPos.x.toFloat(),sleepingPos.y.toFloat()), entity.world.server?.overworld,
                    3,"sleep", Text.of("Sleep"),entity.server,entity)
                TimeCommand.executeSet(serverCommandSource,0)
                for (p in entity.server?.playerManager?.playerList!!) {
                    p.sendMessage(Text.of(entity.displayName.asString()+" skipped the night."),false)
                }
            }
        })
    }
}