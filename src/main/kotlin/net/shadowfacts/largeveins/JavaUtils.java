package net.shadowfacts.largeveins;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

/**
 * @author shadowfacts
 */
// Because you can't have raw types in Kotlin
public class JavaUtils {

	@SuppressWarnings("unchecked")
	public static String getName(IProperty prop, Comparable val) {
		return prop.getName(val);
	}

	@SuppressWarnings("unchecked")
	public static IBlockState withProperty(IBlockState state, IProperty prop, Comparable val) {
		return state.withProperty(prop, val);
	}

}
