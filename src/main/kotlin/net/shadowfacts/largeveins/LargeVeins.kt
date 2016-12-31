package net.shadowfacts.largeveins

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import net.shadowfacts.largeveins.compat.CompatActAdd
import net.shadowfacts.largeveins.compat.CompatSubstratum
import net.shadowfacts.shadowmc.compat.CompatManager
import java.io.File

/**
 * @author shadowfacts
 */
@Mod(modid = MOD_ID, name = NAME, version = "@VERSION@", dependencies = "required-after:shadowmc;", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object LargeVeins {

	lateinit var store: VeinTypeStore
		private set
	val gson: Gson = GsonBuilder()
			.registerTypeAdapter(VeinType::class.java, VeinType.Serializer)
			.registerTypeAdapter(VeinTypeStore::class.java, VeinTypeStore.Serializer)
			.setPrettyPrinting()
			.create()
	lateinit var file: File
		private set

	val compatManager = CompatManager("LargeVeins")

	@Mod.EventHandler
	fun preInit(event: FMLPreInitializationEvent) {
		compatManager.registerModule(CompatActAdd::class.java)
		compatManager.registerModule(CompatSubstratum::class.java)

		file = File(event.modConfigurationDirectory, "shadowfacts/LargeVeins.json")

		GameRegistry.registerWorldGenerator(WorldGenerator, 3)
	}

	@Mod.EventHandler
	fun postInit(event: FMLPostInitializationEvent) {
		compatManager.postInit(event)

		load()
	}

	private fun load() {
		if (file.exists()) {
			store = gson.fromJson(file.readText(), VeinTypeStore::class.java)
		} else {
			store = VeinTypeStore.defaults()
			save()
		}
	}

	private fun save() {
		if (!file.parentFile.exists()) {
			file.parentFile.mkdirs()
		}
		if (!file.exists()) {
			file.createNewFile()
		}
		file.writeText(gson.toJson(store))
	}

}