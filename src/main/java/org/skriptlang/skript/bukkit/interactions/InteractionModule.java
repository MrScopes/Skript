package org.skriptlang.skript.bukkit.interactions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.data.DefaultChangers;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.BlockUtils;
import ch.njol.skript.util.Date;

import org.bukkit.entity.Interaction;
import org.bukkit.entity.Interaction.PreviousInteraction;
import org.skriptlang.skript.addon.AddonModule;
import org.skriptlang.skript.addon.SkriptAddon;

import java.io.IOException;

import javax.annotation.Nullable;

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

		Classes.registerClass(new ClassInfo<>(PreviousInteraction.class, "previousinteraction")
			.user("previousinteractions?")
			.name("Previous Interaction")
			.description("The previous interaction with an interaction entity.")
			.since("INSERT VERSION")
			.parser(new Parser<PreviousInteraction>() {
					@Override
					public @Nullable PreviousInteraction parse(String input, ParseContext context) {
						return null;
					}

					@Override
					public boolean canParse(final ParseContext context) {
						return false;
					}

					@Override
					public String toString(PreviousInteraction interaction, int flags) {
						return "interaction clicked by " + interaction.getPlayer().getName() + " at world tick " + interaction.getTimestamp();
					}

					@Override
					public String toVariableNameString(PreviousInteraction interaction) {
						return "previousinteraction:" + interaction.getPlayer() + "@" + interaction.getTimestamp();
					}
				})
			.requiredPlugins("Minecraft 1.19.4+")
			.defaultExpression(new EventValueExpression<>(PreviousInteraction.class)));
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
