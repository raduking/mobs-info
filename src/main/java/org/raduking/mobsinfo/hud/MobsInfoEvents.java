package org.raduking.mobsinfo.hud;

import org.raduking.mobsinfo.MobsInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class MobsInfoEvents {

	private static final int HUD_POSITION_X = 5;
	private static final int HUD_POSITION_Y = 5;
	private static final int COLOR = 0xFFFFFF;

	private final DisplayedInfo displayedInfo = new DisplayedInfo();

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

		int yi = 0;
		for (Pair<String, ?> customInfo : displayedInfo.getPairList()) {
			drawText(customInfo, HUD_POSITION_X, HUD_POSITION_Y + yi, context, client);
			yi += 10;
		}
	}

	private void drawText(final Pair<String, ?> info, final int x, final int y, final DrawContext context, final MinecraftClient client) {
		String nonTranslatableInfo = null != info.getRight() ? String.valueOf(info.getRight()) : "";
		Text text = Text.translatable(info.getLeft()).append(": " + nonTranslatableInfo);
		context.drawText(client.textRenderer, text, x, y, COLOR, false);
	}

}
