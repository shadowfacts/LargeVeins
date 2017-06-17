package net.shadowfacts.largeveins

import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.gen.IChunkGenerator
import net.minecraft.world.chunk.IChunkProvider
import net.minecraft.world.gen.feature.WorldGenMinable
import net.minecraftforge.fml.common.IWorldGenerator
import java.util.*

/**
 * @author shadowfacts
 */
object WorldGenerator: IWorldGenerator {

	override fun generate(random: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider) {
		if (world.provider.dimension !in LargeVeins.store) return

		LargeVeins.store[world.provider.dimension]!!.filter {
			random.nextInt(it.chance) == 0
		}.forEach {
			generateOre(it.ore, world, random, chunkX * 16, chunkZ * 16, it.minY, it.maxY, it.size, 1, it.toReplace)
		}
	}

	private fun generateOre(ore: IBlockState, world: World, random: Random, x: Int, z: Int, minY: Int, maxY: Int, size: Int, chances: Int, toReplace: IBlockState) {
		val deltaY = maxY - minY

		for (i in 0.until(chances)) {
			val pos = BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16))
			WorldGenMinable(ore, size) {
				toReplace === it
			}.generate(world, random, pos)
		}
	}

}