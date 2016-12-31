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
			val block = getBlock("actuallyadditions", "blockMisc")
			val state = getState(block.defaultState, "meta", "3")
			add(0, VeinType(state, 0, 45, 60, 5000))
		}
	}

}