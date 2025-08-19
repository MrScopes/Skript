package org.skriptlang.skript.bukkit.interactions.elements;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.entity.Interaction;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Name("Interaction Height/Width")
@Description({
    "Returns height or width of interaction entity."
})
@Examples("set interaction height of the last spawned interaction to 10")
@Since("INSERT VERSION")
public class ExprInteractionHeightWidth extends SimplePropertyExpression<Interaction, Float> {

	static {
		registerDefault(ExprInteractionHeightWidth.class, Float.class, "interaction (:height|width)", "interactions");
	}

	private boolean height;

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		height = parseResult.hasTag("height");
		return super.init(exprs, matchedPattern, isDelayed, parseResult);
	}

	@Override
	public @Nullable Float convert(Interaction interaction) {
		return height ? interaction.getInteractionHeight() : interaction.getInteractionWidth();
	}

	public Class<?> @Nullable [] acceptChange(ChangeMode mode) {
		return switch (mode) {
			case ADD, REMOVE, RESET, SET -> CollectionUtils.array(Number.class);
			default -> null;
		};
	}

	@Override
	public void change(Event event, Object @Nullable [] delta, ChangeMode mode) {
		Interaction[] interactions = getExpr().getArray(event);

		float change = delta == null ? 0F : ((Number) delta[0]).floatValue();
		if (Float.isInfinite(change) || Float.isNaN(change))
			return;

		switch (mode) {
			case REMOVE:
				change = -change;
                //$FALL-THROUGH$
			case ADD:
				for (Interaction interaction : interactions) {
					if (height) {
						float value = Math.max(0F, interaction.getInteractionHeight() + change);
						if (Float.isInfinite(value))
							continue;
						interaction.setInteractionHeight(change);
					} else {
						float value = Math.max(0F, interaction.getInteractionWidth() + change);
						if (Float.isInfinite(value))
							continue;
						interaction.setInteractionWidth(change);
					}
				}
				break;
			case RESET:
			case SET:
				change = Math.max(0F, change);
				for (Interaction interaction : interactions) {
					if (height) {
						interaction.setInteractionHeight(change);
					} else {
						interaction.setInteractionWidth(change);
					}
				}
				break;
		}
	}

	@Override
	public Class<? extends Float> getReturnType() {
		return Float.class;
	}

	@Override
	protected String getPropertyName() {
		return height ? "height" : "width";
	}

}
