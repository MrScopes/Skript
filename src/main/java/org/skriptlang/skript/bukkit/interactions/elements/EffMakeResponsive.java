package org.skriptlang.skript.bukkit.interactions.elements;

import org.bukkit.entity.Interaction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Make Interaction Responsive")
@Description("Forces an interaction entity to play the punch sound when hit or swing the player's arm when right clicked")
@Examples({"make last spawned interaction responsive", "make last spawned interaction unresponsive"})
@Since("INSERT VERSION")
public class EffMakeResponsive extends Effect {

	static {
		Skript.registerEffect(EffMakeResponsive.class, "make %interactions% [:un]responsive");
	}

    private Expression<Interaction> interactions;
	private boolean responsive;

    @Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        interactions = (Expression<Interaction>) exprs[0];
		responsive = !parseResult.hasTag("un");
		return true;
	}

	@Override
	protected void execute(Event e) {
		for (Interaction interaction : interactions.getArray(e)) {
			interaction.setResponsive(responsive);
		}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "make " + interactions.toString(e, debug) + (!responsive ? " un" : " ") + "responsive";
	}

}
