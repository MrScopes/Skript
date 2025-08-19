package org.skriptlang.skript.bukkit.interactions.elements;

import org.bukkit.entity.Interaction;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;

@Name("Is Responsive")
@Description("Checks whether an interaction entity should play the punch sound when hit or swing the player's arm when right clicked")
@Examples("""
	if last spawned interaction isn't responsive:
		make last spawned interaction responsive
	""")
@Since("INSERT VERSION")
public class CondIsResponsive extends PropertyCondition<Interaction> {
	
	static {
		register(CondIsResponsive.class, "responsive", "interactions");
	}
	
	@Override
	public boolean check(Interaction interaction) {
		return interaction.isResponsive();
	}
	
	@Override
	protected String getPropertyName() {
		return "responsive";
	}
	
}
