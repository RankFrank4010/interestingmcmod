
package net.mcreator.difficultmod.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import net.mcreator.difficultmod.entity.MyfriendEntity;

@OnlyIn(Dist.CLIENT)
public class MyfriendRenderer {
	public static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(MyfriendEntity.entity,
					renderManager -> new MobRenderer(renderManager, new PigModel(), 0.5f) {

						@Override
						public ResourceLocation getEntityTexture(Entity entity) {
							return new ResourceLocation("superdifficultmod:textures/entities/fox.png");
						}
					});
		}
	}
}
