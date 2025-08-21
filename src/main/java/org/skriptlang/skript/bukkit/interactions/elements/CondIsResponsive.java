package org.skriptlang.skript.bukkit.interactions.elements;

import org.bukkit.entity.Interaction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Is Responsive")
@Description("Checks whether an interaction entity should play the punch sound when hit or swing the player's arm when right clicked")
@Examples("""
	if last spawned interaction is unresponsive:
		make last spawned interaction responsive
	""")
@Since("INSERT VERSION")
public class CondIsResponsive extends Condition {

    static {
        Skript.registerCondition(CondIsResponsive.class,
            "%interactions% (is|are) responsive",
			"%interactions% (isn't|is not|aren't|are not) unresponsive",
            "%interactions% (isn't|is not|aren't|are not) responsive",
            "%interactions% (is|are) unresponsive"
        );
    }

    private Expression<Interaction> interactions;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        interactions = (Expression<Interaction>) exprs[0];
        setNegated(matchedPattern > 1);
        return true;
    }

    @Override
    public boolean check(Event e) {
        return interactions.check(e, Interaction::isResponsive, isNegated());
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return interactions.toString(e, debug) + " is" + (isNegated() ? "n't" : "") + " responsive";
    }

}
