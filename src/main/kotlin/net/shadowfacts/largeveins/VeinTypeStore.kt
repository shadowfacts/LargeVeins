package net.shadowfacts.largeveins

import com.google.gson.*
import net.minecraft.init.Blocks
import java.lang.reflect.Type
import java.util.*

/**
 * @author shadowfacts
 */
class VeinTypeStore: HashMap<Int, MutableSet<VeinType>>() {

	fun add(dim: Int, type: VeinType) {
		if (dim !in this) this[dim] = mutableSetOf(type)
		else this[dim]!!.add(type)
	}

	companion object {
		val defaultCompatHandlers = mutableSetOf<VeinTypeStore.() -> Unit>()

		fun defaults(): VeinTypeStore {
			val store = VeinTypeStore()

			store.add(0, VeinType(Blocks.COAL_ORE, 0, 72, 140, 1000))
			store.add(0, VeinType(Blocks.IRON_ORE, 0, 72, 90, 2000))
			store.add(0, VeinType(Blocks.GOLD_ORE, 0, 32, 90, 17000))
			store.add(0, VeinType(Blocks.REDSTONE_ORE, 0, 16, 80, 5000))
			store.add(0, VeinType(Blocks.DIAMOND_ORE, 0, 16, 80, 47000))
			store.add(0, VeinType(Blocks.LAPIS_ORE, 0, 32, 70, 47000))

			// Quartz should be 2x as rare as coal, but it needs more spawn chances b/c random number distribution most of the spawn chances are in the air
			store.add(-1, VeinType(Blocks.QUARTZ_ORE, 7, 117, 110, 1000, toReplace = Blocks.NETHERRACK))

			defaultCompatHandlers.forEach {
				store.it()
			}

			return store
		}
	}

	object Serializer: JsonSerializer<VeinTypeStore>, JsonDeserializer<VeinTypeStore> {

		override fun serialize(store: VeinTypeStore, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
			return JsonObject().apply {
				store.forEach { dim, types ->
					val arr = JsonArray()
					types.forEach {
						arr.add(context.serialize(it))
					}
					add(dim.toString(), arr)
				}
			}
		}

		override fun deserialize(obj: JsonElement, typeOfT: Type, context: JsonDeserializationContext): VeinTypeStore {
			return VeinTypeStore().apply {
				if (obj is JsonObject) {
					obj.entrySet().forEach {
						val dim = it.key.toInt()
						val set = mutableSetOf<VeinType>()
						it.value.asJsonArray.forEach {
							set.add(context.deserialize(it, VeinType::class.java))
						}
						this[dim] = set
					}
				}
			}
		}

	}
}
