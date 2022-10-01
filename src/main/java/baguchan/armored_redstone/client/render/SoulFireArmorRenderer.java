package baguchan.armored_redstone.client.render;

import baguchan.armored_redstone.ArmoredRedstone;
import baguchan.armored_redstone.entity.SoulFireArmorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SoulFireArmorRenderer<T extends SoulFireArmorEntity> extends FireArmorRenderer<T> {
	private static final ResourceLocation TEXTURES = new ResourceLocation(ArmoredRedstone.MODID, "textures/entity/soul_armor.png");

	public SoulFireArmorRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ResourceLocation getTextureLocation(T p_114482_) {
		return TEXTURES;
	}
}
