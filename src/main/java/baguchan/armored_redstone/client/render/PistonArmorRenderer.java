package baguchan.armored_redstone.client.render;

import baguchan.armored_redstone.entity.PistonArmorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PistonArmorRenderer<T extends PistonArmorEntity> extends EntityRenderer<T> {
	public PistonArmorRenderer(EntityRendererProvider.Context p_174008_) {
		super(p_174008_);
	}

	@Override
	public void render(T p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
		if(Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
			Vec3 vec3d = p_114485_.getViewVector(1.0F);
			AABB aabb = p_114485_.getBoundingBox().inflate(0.75D, 0.25D, 0.75D).move(vec3d.x * 1.6D, vec3d.y * 1.6D, vec3d.z * 1.6D).move(-p_114485_.getX(), -p_114485_.getY(), -p_114485_.getZ());
			LevelRenderer.renderLineBox(p_114488_, p_114489_.getBuffer(RenderType.lines()), aabb, 1.0F, 0.8F, 0.8F, 1.0F);
		}
	}

	@Override
	public ResourceLocation getTextureLocation(T p_114482_) {
		return null;
	}
}
