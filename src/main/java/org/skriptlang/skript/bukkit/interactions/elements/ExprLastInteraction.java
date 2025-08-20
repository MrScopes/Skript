package org.skriptlang.skript.bukkit.interactions.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Interaction.PreviousInteraction;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.Nullable;

@Name("Previous Interaction of Interaction Entity")
@Description({
})
@Examples({
})
@Since("2.10")

public class ExprLastInteraction extends SimplePropertyExpression<Interaction, PreviousInteraction> {

	static {
		Skript.registerExpression(ExprLastInteraction.class, PreviousInteraction.class, ExpressionType.PROPERTY,
				"last right click interaction of %interactions%", 
				"last (attack|left click) interaction of %interactions%",
				"last interaction of %interactions%"
				);
	}

	private ClickType clicktype;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        clicktype = switch(matchedPattern) {
            case 0 -> ClickType.RIGHT;
            case 1 -> ClickType.LEFT;
            default -> ClickType.UNKNOWN;
        };
            
		return super.init(exprs, matchedPattern, isDelayed, parseResult);
	}

	@Override
	public @Nullable PreviousInteraction convert(Interaction interaction) {
		return switch (clicktype) {
			case RIGHT -> interaction.getLastInteraction();
			case LEFT  -> interaction.getLastAttack();
			default -> {
				PreviousInteraction lastInteraction = interaction.getLastInteraction();
				PreviousInteraction lastAttack = interaction.getLastAttack();
				if (lastInteraction == null) yield lastAttack;
				if (lastAttack == null) yield lastInteraction;
				yield (lastInteraction.getTimestamp() > lastAttack.getTimestamp()) ? lastInteraction : lastAttack;
			}
		};
	}

	@Override
	protected String getPropertyName() {
		return "";
	}

	@Override
	public Class<? extends PreviousInteraction> getReturnType() {
		return PreviousInteraction.class;
	}

}
