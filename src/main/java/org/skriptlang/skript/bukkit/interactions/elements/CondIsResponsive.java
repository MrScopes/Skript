package org.skriptlang.skript.bukkit.interactions.elements;

import org.bukkit.block.Block;
import org.bukkit.entity.Interaction;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Is Responsive")
@Description("Checks whether an interaction entity should play the punch sound when hit or swing the player's arm when right clicked")
@Examples("""
	if last spawned interaction isn't responsive:
		make last spawned interaction responsive
	""")
@Since("INSERT VERSION")
public class CondIsResponsive extends PropertyCondition<Interaction> {
	
	static {
		register(CondIsResponsive.class, "[:un]responsive", "interactions");
	}

	private boolean responsive;
	
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		responsive = !parseResult.hasTag("un");
		return super.init(exprs, matchedPattern, isDelayed, parseResult);
	}

	@Override
	public boolean check(Interaction interaction) {
		return interaction.isResponsive() == responsive;
	}
	
	@Override
	protected String getPropertyName() {
		return !responsive ? "un" : "" + "responsive";
	}
	
}
