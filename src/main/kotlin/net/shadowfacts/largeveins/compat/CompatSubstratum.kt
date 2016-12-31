package net.shadowfacts.largeveins.compat

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.shadowfacts.largeveins.VeinType
import net.shadowfacts.largeveins.VeinTypeStore
import net.shadowfacts.shadowmc.compat.Compat

/**
 * @author shadowfacts
 */
@Compat("substratum")
object CompatSubstratum: CompatHelper() {

	@JvmStatic
	@Compat.PostInit
	fun postInit(event: FMLPostInitializationEvent) {
		VeinTypeStore.defaultCompatHandlers.add {
			val state = getBlock("substratum", "ore").defaultState

			val copper = getState(state, "variant", "copper")
			add(0, VeinType(copper, 20, 80, 70, (167 + 333) / 2 * 60))

			val tin = getState(state, "variant", "tin")
			add(0, VeinType(tin, 20, 52, 70, (188 + 375) / 2 * 60))

			val zinc = getState(state, "variant", "zinc")
			add(0, VeinType(zinc, 8, 48, 70, 175 * 60))

			val nickel = getState(state, "variant", "nickel")
			add(0, VeinType(nickel, 8, 36, 70, (107 + 214) / 2 * 60))

			val silver = getState(state, "variant", "silver")
			add(0, VeinType(silver, 8, 30, 70, (136 + 182) / 2 * 60))

			val lead = getState(state, "variant", "lead")
			add(0, VeinType(lead, 8, 48, 70, (125 + 150) / 2 * 60))

			val platinum = getState(state, "variant", "platinum")
			add(0, VeinType(platinum, 2, 12, 30, (100 + 150) / 2 * 60))

			val alumina = getState(state, "variant", "alumina")
			add(0, VeinType(alumina, 2, 16, 70, (125 + 312) / 2 * 60))

			val chromium = getState(state, "variant", "chrome")
			add(0, VeinType(chromium, 2, 8, 50, (100 + 150) / 2 * 60))
		}
	}

}