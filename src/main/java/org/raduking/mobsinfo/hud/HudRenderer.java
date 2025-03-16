package org.raduking.mobsinfo.hud;

import java.util.function.Consumer;

import org.raduking.mobsinfo.MobsInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class HudRenderer {

	public static final Identifier MOBS_INFO_LAYER = Identifier.of(MobsInfo.MOD_ID, "mobsinfo-layer");

	private final DisplayedInfo displayedInfo = new DisplayedInfo();

	// TODO: make these configurable
	private static final int HUD_POSITION_X = 5;
	private static final int HUD_POSITION_Y = 5;
	private static final int COLOR = 0xFFFFFF;
	private static final float FONT_SCALE = 0.5f;

	private static final int LINE_HEIGHT = 10;

	public void onHudRender(final DrawContext context, final RenderTickCounter tickDelta) {
		if (MobsInfo.isModDisabled()) {
			return;
		}
		MinecraftClient client = MinecraftClient.getInstance();
		if (null == client.world || null == client.player) {
			return;
		}
		boolean hideInfo = true;
		if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.ENTITY) {
			EntityHitResult entityHit = (EntityHitResult) client.crosshairTarget;
			if (entityHit.getEntity() instanceof LivingEntity mob) {
				displayedInfo.update(mob);
				hideInfo = false;
			}
		}
		if (hideInfo) {
			return;
		}

		onMatrix(context, matrixStack ->
				render(context, tickDelta, matrixStack)
		);
	}

	private void render(final DrawContext context, final RenderTickCounter tickDelta, final MatrixStack matrixStack) {
		matrixStack.scale(FONT_SCALE, FONT_SCALE, 1.0f);

		int yi = HUD_POSITION_Y;
		for (Pair<String, ?> customInfo : displayedInfo.getPairList()) {
			drawText(customInfo, HUD_POSITION_X, yi, context);
			yi += LINE_HEIGHT;
		}
	}

	private void drawText(final Pair<String, ?> info, final int x, final int y, final DrawContext context) {
		MinecraftClient client = MinecraftClient.getInstance();

		String nonTranslatableInfo = null != info.getRight() ? String.valueOf(info.getRight()) : "";
		Text text = Text
				.translatable(info.getLeft())
				.append(": ")
				.append(nonTranslatableInfo);

		context.drawText(client.textRenderer, text, x, y, COLOR, false);
	}

	private void onMatrix(final DrawContext context, final Consumer<MatrixStack> matrixStackConsumer) {
		MatrixStack matrixStack = context.getMatrices();
		matrixStack.push();
		matrixStackConsumer.accept(matrixStack);
		matrixStack.pop();
	}

}
