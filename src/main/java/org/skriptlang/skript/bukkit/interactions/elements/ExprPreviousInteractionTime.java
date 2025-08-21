package org.skriptlang.skript.bukkit.interactions.elements;

import org.bukkit.entity.Interaction.PreviousInteraction;
import org.jetbrains.annotations.Nullable;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
public class ExprPreviousInteractionTime extends SimplePropertyExpression<PreviousInteraction, Long> {

	static {
		registerDefault(ExprPreviousInteractionTime.class, Long.class, "world tick", "previousinteractions");
	}

    @Override
    public @Nullable Long convert(PreviousInteraction interaction) {
        return interaction.getTimestamp();
    }

    @Override
    public Class<? extends Long> getReturnType() {
        return Long.class;
    }

    @Override
    protected String getPropertyName() {
        return "world tick";
    }

}
