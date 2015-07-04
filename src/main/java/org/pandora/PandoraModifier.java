/*
 * Copyright (c) 2012-2015 Sean Porter <glitchkey@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.azthec;

//* IMPORTS: JDK/JRE
	import java.util.HashMap;
	import java.util.Map;
//* IMPORTS: SPONGE
	import org.spongepowered.api.data.DataContainer;
	import org.spongepowered.api.util.gen.BiomeBuffer;
	import org.spongepowered.api.util.gen.MutableBlockBuffer;
	import org.spongepowered.api.world.gen.GeneratorPopulator;
	import org.spongepowered.api.world.World;
	import org.spongepowered.api.world.WorldCreationSettings;
//* IMPORTS: PANDORA
	//* NOT NEEDED
//* IMPORTS: OTHER
	//* NOT NEEDED

public abstract class PandoraModifier implements GeneratorPopulator
{
	private Map<World, SimplexNoise> noises;

	public abstract void populate(World world, MutableBlockBuffer buffer,
		BiomeBuffer biomes);

	public final int getNoise(World world, int x, int z, double range,
		double scale, int octaves, double amplitude, double frequency)
	{
		if (world == null)
			return 0;
		else if (noises == null)
			noises = new HashMap<World, SimplexNoise>();

		if (!noises.containsKey(world))
			noises.put(world, (new SimplexNoise()));

		SimplexNoise noise = noises.get(world);

		if (noise == null)
			return 0;

		range /= 2;
		int xs = (int) Math.round(((double) x) / scale);
		int zs = (int) Math.round(((double) z) / scale);
		// TODO: Implement octaves, frequency, and amplitude

		double seed = world.getCreationSettings().getSeed();
		double cnoise = noise.noise(seed, xs, zs);
		//double cnoise = noise.getNoise(xs, zs, octaves, frequency, amplitude);
		return (int) ((range * cnoise) + range);
	}

	public final SimplexNoise getNoiseGenerator(World world) {
		if (world == null)
			return null;
		else if (noises == null)
			noises = new HashMap<World, SimplexNoise>();

		if (!noises.containsKey(world))
			noises.put(world, (new SimplexNoise()));

		return noises.get(world);
	}
}
