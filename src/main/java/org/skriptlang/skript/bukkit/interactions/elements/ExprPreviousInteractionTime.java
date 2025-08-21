package org.skriptlang.skript.bukkit.interactions.elements;

import org.bukkit.entity.Interaction.PreviousInteraction;
import org.jetbrains.annotations.Nullable;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Date;

public class ExprPreviousInteractionTime extends SimplePropertyExpression<PreviousInteraction, Date> {

	static {
		registerDefault(ExprInteractionHeightWidth.class, Float.class, "time", "previousinteractions");
	}

    @Override
    public @Nullable Date convert(PreviousInteraction interaction) {
        return new Date(interaction.getTimestamp());
    }

    @Override
    public Class<? extends Date> getReturnType() {
        return Date.class;
    }

    @Override
    protected String getPropertyName() {
        return "time";
    }

}
