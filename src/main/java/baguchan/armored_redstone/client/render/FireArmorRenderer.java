package baguchan.armored_redstone.client.render;

import baguchan.armored_redstone.ArmoredRedstone;
import baguchan.armored_redstone.client.ModModelLayers;
import baguchan.armored_redstone.client.model.FireArmorModel;
import baguchan.armored_redstone.entity.FireArmorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FireArmorRenderer<T extends FireArmorEntity> extends MobRenderer<T, FireArmorModel<T>> {
	private static final ResourceLocation TEXTURES = new ResourceLocation(ArmoredRedstone.MODID, "textures/entity/fire_armor.png");

	public FireArmorRenderer(EntityRendererProvider.Context context) {
		super(context, new FireArmorModel<>(context.bakeLayer(ModModelLayers.FIRE_ARMOR)), 0.75F);
	}

	@Override
	public void render(T p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
		if (Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
			p_114488_.pushPose();
			Vec3 vec3d = p_114485_.getViewVector(1.0F);
			AABB aabb = p_114485_.getFireBoundingBox().move(-p_114485_.getX(), -p_114485_.getY(), -p_114485_.getZ());
			LevelRenderer.renderLineBox(p_114488_, p_114489_.getBuffer(RenderType.lines()), aabb, 1.0F, 0.5F, 0.5F, 1.0F);
			p_114488_.popPose();
		}
		super.render(p_114485_, p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
	}

	@Override
	protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
		super.scale(p_115314_, p_115315_, p_115316_);
		p_115315_.scale(2.5F, 2.5F, 2.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_114482_) {
		return TEXTURES;
	}
}
