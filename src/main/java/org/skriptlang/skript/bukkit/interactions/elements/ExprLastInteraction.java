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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Interaction;
import org.bukkit.entity.Interaction.PreviousInteraction;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.Nullable;

@Name("Previous Interaction of Interaction Entity")
@Description({
})
@Examples({
})
@Since("INSERT VERSION")

public class ExprLastInteraction extends SimplePropertyExpression<Interaction, PreviousInteraction> {

	static {
		List<String> patterns = new ArrayList<>();
		patterns.addAll(Arrays.asList(getPatterns("last right click interaction", "interactions")));
		patterns.addAll(Arrays.asList(getPatterns("last (attack|left click) interaction", "interactions")));
		patterns.addAll(Arrays.asList(getPatterns("last interaction", "interactions")));

		Skript.registerExpression(ExprLastInteraction.class, PreviousInteraction.class, ExpressionType.PROPERTY, patterns.toArray(String[]::new));
	}

	private ClickType clicktype;

	/*
	 * 0 = right click
	 * 1 = left click
	 * 2 = last interaction
	 */
	private int mark;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		this.mark = matchedPattern / 2;

        clicktype = switch (mark) {
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
