package net.shadowfacts.largeveins.compat

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.shadowfacts.largeveins.VeinType
import net.shadowfacts.largeveins.VeinTypeStore
import net.shadowfacts.shadowmc.compat.Compat

/**
 * @author shadowfacts
 */
@Compat("actuallyadditions")
object CompatActAdd: CompatHelper() {

	@JvmStatic
	@Compat.PostInit
	fun postInit(event: FMLPostInitializationEvent) {
		VeinTypeStore.defaultCompatHandlers.add {
			val block = getBlock("actuallyadditions", "block_misc")
			val state = getState(block.defaultState, "type", "ore_black_quartz")
			add(0, VeinType(state, 0, 45, 60, 5000))
		}
	}

}