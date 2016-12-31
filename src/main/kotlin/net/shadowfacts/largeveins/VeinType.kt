package net.shadowfacts.largeveins

import com.google.gson.*
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.shadowfacts.largeveins.util.JavaUtils
import java.lang.reflect.Type

/**
 * @author shadowfacts
 */
data class VeinType(val ore: IBlockState, val minY: Int, val maxY: Int, val size: Int, val chance: Int, val toReplace: IBlockState = Blocks.STONE.defaultState) {

	constructor(ore: Block, minY: Int, maxY: Int, size: Int, chance: Int, toReplace: Block = Blocks.STONE): this(ore.defaultState, minY, maxY, size, chance, toReplace = toReplace.defaultState)

	object Serializer: JsonSerializer<VeinType>, JsonDeserializer<VeinType> {

		private fun serializeState(obj: JsonObject, state: IBlockState) {
			obj.addProperty("id", state.block.registryName.toString())
			obj.add("state", JsonObject().apply {
				state.properties.forEach {
					addProperty(it.key.getName(), JavaUtils.getName(it.key, it.value))
				}
			})
		}

		private fun deserializeState(obj: JsonObject): IBlockState {
			val block = ForgeRegistries.BLOCKS.getValue(ResourceLocation(obj["id"].asString))
			var state = block.defaultState
			obj["state"].asJsonObject.entrySet().forEach { e ->
				val prop = state.propertyKeys.first {
					it.getName() == e.key
				}
				state = JavaUtils.withProperty(state, prop, prop.parseValue(e.value.asString).get())
			}
			return state
		}

		override fun serialize(type: VeinType, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
			return JsonObject().apply {
				add("ore", JsonObject().apply {
					serializeState(this, type.ore)
				})
				add("toReplace", JsonObject().apply {
					serializeState(this, type.toReplace)
				})
				addProperty("minY", type.minY)
				addProperty("maxY", type.maxY)
				addProperty("size", type.size)
				addProperty("chance", type.chance)
			}
		}

		override fun deserialize(obj: JsonElement, typeOfT: Type, context: JsonDeserializationContext): VeinType {
			if (obj is JsonObject) {
				val ore = deserializeState(obj["ore"].asJsonObject)
				val toReplace = deserializeState(obj["toReplace"].asJsonObject)
				val minY = obj["minY"].asInt
				val maxY = obj["maxY"].asInt
				val size = obj["size"].asInt
				val chance = obj["chance"].asInt
				return VeinType(ore, minY, maxY, size, chance, toReplace = toReplace)
			}
			throw JsonParseException("Element must be an object to deserialize to VeinType")
		}

	}

}