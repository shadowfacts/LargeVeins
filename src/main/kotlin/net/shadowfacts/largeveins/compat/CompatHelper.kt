package net.shadowfacts.largeveins.compat

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.shadowfacts.largeveins.util.JavaUtils

/**
 * Helper functions so I don't have to hard-depend on anything
 *
 * @author shadowfacts
 */
abstract class CompatHelper {

	fun getBlock(modid: String, name: String): Block {
		return ForgeRegistries.BLOCKS.getValue(ResourceLocation(modid, name))!!
	}

	fun getState(state: IBlockState, propName: String, valueName: String): IBlockState {
		val prop = state.propertyKeys.first {
			it.getName() == propName
		}
		val value = prop.parseValue(valueName).get()
		return JavaUtils.withProperty(state, prop, value)
	}

}