package baguchan.armored_redstone.client.render.item;

import baguchan.armored_redstone.ArmoredRedstone;
import baguchan.armored_redstone.client.ModModelLayers;
import baguchan.armored_redstone.client.model.FireArmorModel;
import baguchan.armored_redstone.client.model.PistonArmorModel;
import baguchan.armored_redstone.client.model.RedMonsModel;
import baguchan.armored_redstone.client.model.SoulArmorModel;
import baguchan.armored_redstone.item.ArmorRedstoneItem;
import baguchan.armored_redstone.register.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ArmorBWLR extends BlockEntityWithoutLevelRenderer {
	private static final ResourceLocation TEXTURES = new ResourceLocation(ArmoredRedstone.MODID, "textures/entity/piston_armor.png");
	private static final ResourceLocation TEXTURES_FIRE = new ResourceLocation(ArmoredRedstone.MODID, "textures/entity/fire_armor.png");
	private static final ResourceLocation TEXTURES_SOUL = new ResourceLocation(ArmoredRedstone.MODID, "textures/entity/soul_armor.png");
	private static final ResourceLocation TEXTURES_REDMONS = new ResourceLocation(ArmoredRedstone.MODID, "textures/entity/redmons_armor.png");

	private PistonArmorModel<?> entityModel;
	private FireArmorModel<?> entityModelFire;
	private SoulArmorModel<?> entityModelSoul;
	private RedMonsModel<?> entityModelRedMons;

	public ArmorBWLR() {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
		this.entityModel = new PistonArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.PISTON_ARMOR));
		this.entityModelFire = new FireArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.FIRE_ARMOR));
		this.entityModelSoul = new SoulArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.SOUL_ARMOR));
		this.entityModelRedMons = new RedMonsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.REDMONS_ARMOR));

	}

	@Override
	public void renderByItem(ItemStack pStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pOverlay) {
		if (pStack.getItem() instanceof ArmorRedstoneItem) {
			if (pStack.is(ModItems.PISTON_ARMOR.get())) {
				pPoseStack.pushPose();
				pPoseStack.scale(1.0F, -1.0F, -1.0F);
				pPoseStack.translate(0.65F, -0.65F, 0);
				VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucentCull(TEXTURES));
				this.entityModel.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, pOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
				pPoseStack.popPose();
			} else if (pStack.is(ModItems.FIRE_ARMOR.get())) {
				pPoseStack.pushPose();
				pPoseStack.scale(1.0F, -1.0F, -1.0F);
				pPoseStack.translate(0.65F, -0.65F, 0);
				VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucentCull(TEXTURES_FIRE));
				this.entityModelFire.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, pOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
				pPoseStack.popPose();
			} else if (pStack.is(ModItems.SOUL_FIRE_ARMOR.get())) {
				pPoseStack.pushPose();
				pPoseStack.scale(1.0F, -1.0F, -1.0F);
				pPoseStack.translate(0.65F, -0.65F, 0);
				VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucentCull(TEXTURES_SOUL));
				this.entityModelSoul.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, pOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
				pPoseStack.popPose();
			} else if (pStack.is(ModItems.REDMONS_ARMOR.get())) {
				pPoseStack.pushPose();
				pPoseStack.scale(1.0F, -1.0F, -1.0F);
				pPoseStack.translate(0.65F, -0.65F, 0);
				VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityTranslucentCull(TEXTURES_REDMONS));
				this.entityModelRedMons.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, pOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
				pPoseStack.popPose();
			}
		}
	}
}