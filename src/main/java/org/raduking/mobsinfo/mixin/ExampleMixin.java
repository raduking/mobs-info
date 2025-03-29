package org.raduking.mobsinfo.mixin;

import org.raduking.mobsinfo.MobsInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public class ExampleMixin {

	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(final CallbackInfo info) {
		// This code is injected into the start of MinecraftServer.loadWorld()V
		MobsInfo.LOGGER.info("Called ExampleMixin.init");
	}
}
