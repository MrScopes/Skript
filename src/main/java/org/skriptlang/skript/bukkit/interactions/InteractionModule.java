package org.skriptlang.skript.bukkit.interactions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.data.DefaultChangers;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.registrations.Classes;

import org.bukkit.entity.Interaction;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.SkriptAddon;

import java.io.IOException;

public class InteractionModule implements AddonModule {
	
	@Override
	public boolean canLoad(SkriptAddon addon) {
		return Skript.classExists("org.bukkit.entity.Interaction");
	}

	@Override
	public void init(SkriptAddon addon) {
		Classes.registerClass(new ClassInfo<>(Interaction.class, "interaction")
			.user("interactions?")
			.name("Interaction Entity")
			.description("An interaction entity.")
			.since("INSERT VERSION")
			.requiredPlugins("Minecraft 1.19.4+")
			.defaultExpression(new EventValueExpression<>(Interaction.class))
			.changer(DefaultChangers.nonLivingEntityChanger));
	}

	@Override
	public void load(SkriptAddon addon) {
		try {
			Skript.getAddonInstance().loadClasses("org.skriptlang.skript.bukkit.interactions", "elements");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
