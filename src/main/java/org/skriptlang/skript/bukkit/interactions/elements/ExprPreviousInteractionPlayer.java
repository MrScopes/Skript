package org.skriptlang.skript.bukkit.interactions.elements;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Interaction.PreviousInteraction;
import org.jetbrains.annotations.Nullable;

import ch.njol.skript.expressions.base.SimplePropertyExpression;

public class ExprPreviousInteractionPlayer extends SimplePropertyExpression<PreviousInteraction, OfflinePlayer> {

	static {
		registerDefault(ExprPreviousInteractionPlayer.class, OfflinePlayer.class, "player", "previousinteractions");
	}

    @Override
    public @Nullable OfflinePlayer convert(PreviousInteraction interaction) {
        return interaction.getPlayer();
    }

    @Override
    public Class<? extends OfflinePlayer> getReturnType() {
        return OfflinePlayer.class;
    }

    @Override
    protected String getPropertyName() {
        return "player";
    }

}
