package baguchan.armored_redstone.client.model;// Made with Blockbench 4.4.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.armored_redstone.client.animation.PistonArmorAnimation;
import baguchan.armored_redstone.entity.PistonArmorEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class PistonArmorModel<T extends PistonArmorEntity> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart RightLeg;
	private final ModelPart LeftLeg;
	private final ModelPart body;
	private final ModelPart RightHand;
	private final ModelPart LeftHand;

	private final ModelPart right_leg_base;
	private final ModelPart left_leg_base;

	public PistonArmorModel(ModelPart root) {
		this.root = root;
		this.RightLeg = root.getChild("RightLeg");
		this.LeftLeg = root.getChild("LeftLeg");
		this.body = root.getChild("body");
		this.RightHand = root.getChild("RightHand");
		this.LeftHand = root.getChild("LeftHand");
		this.right_leg_base = this.RightLeg.getChild("right_leg_base");
		this.left_leg_base = this.LeftLeg.getChild("left_leg_base");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(10, 45).addBox(-2.0F, -1.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 18.0F, 1.0F));

		PartDefinition right_leg_base = RightLeg.addOrReplaceChild("right_leg_base", CubeListBuilder.create().texOffs(39, 38).addBox(-2.0F, 4.0F, -3.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(13, 19).addBox(-2.0F, 5.0F, -4.0F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = right_leg_base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(51, 35).addBox(-1.5F, -5.5F, -2.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 43).addBox(-1.0F, -1.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 18.0F, 1.0F));

		PartDefinition left_leg_base = LeftLeg.addOrReplaceChild("left_leg_base", CubeListBuilder.create().texOffs(41, 9).addBox(-1.0F, 4.0F, -3.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 18).addBox(-1.0F, 5.0F, -4.0F, 3.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r2 = left_leg_base.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(10, 51).addBox(-0.5F, -5.5F, -2.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -1.0F, -3.0F, 10.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(44, 49).addBox(-6.5F, -6.0F, -4.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 49).addBox(-6.5F, -6.0F, 3.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(24, 48).addBox(2.5F, -6.0F, 3.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(34, 0).addBox(-3.5F, -6.0F, 4.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 34).addBox(-3.5F, -6.0F, -5.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(34, 49).addBox(2.5F, -6.0F, -4.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(33, 20).addBox(4.5F, -6.0F, -2.0F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(20, 27).addBox(-7.5F, -6.0F, -2.0F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(32, 9).addBox(-1.5F, -3.5F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(36, 44).addBox(-2.5F, -2.0F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 10).addBox(-4.5F, 3.0F, -2.5F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(38, 32).addBox(-2.5F, 2.0F, -1.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 13.0F, 0.0F));

		PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(48, 44).addBox(-4.0F, -4.0F, 2.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -2.0F, -4.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition RightHand = partdefinition.addOrReplaceChild("RightHand", CubeListBuilder.create().texOffs(0, 27).addBox(-5.0F, -1.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 7.0F, 1.0F, 0.0F, 0.0F, 0.3054F));

		PartDefinition right_hand = RightHand.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(50, 0).addBox(-3.5F, 6.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(54, 48).addBox(-3.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(26, 41).addBox(-4.0F, 5.5F, -6.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition piston_right = right_hand.addOrReplaceChild("piston_right", CubeListBuilder.create().texOffs(28, 33).addBox(-1.0F, -1.0F, -1.25F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(15, 27).addBox(-2.0F, -2.0F, -2.25F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 7.5F, -4.75F));

		PartDefinition LeftHand = partdefinition.addOrReplaceChild("LeftHand", CubeListBuilder.create().texOffs(26, 13).addBox(0.0F, -1.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 7.0F, 1.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition left_hand2 = LeftHand.addOrReplaceChild("left_hand2", CubeListBuilder.create().texOffs(47, 24).addBox(0.5F, 6.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(43, 17).addBox(0.0F, 5.5F, -6.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(54, 48).addBox(1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition piston_left = left_hand2.addOrReplaceChild("piston_left", CubeListBuilder.create().texOffs(10, 37).addBox(-1.0F, -1.0F, -1.25F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(26, 20).addBox(-2.0F, -2.0F, -2.25F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 7.5F, -4.75F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.attackAnimationState, PistonArmorAnimation.ATTACK, ageInTicks);
		this.animate(entity.attackFinishedAnimationState, PistonArmorAnimation.ATTACKFINISH, ageInTicks);
		if (!entity.isSprinting() && entity.onGround()) {
			this.right_leg_base.xRot = Mth.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount;
			this.left_leg_base.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount;
		} else {
			this.right_leg_base.xRot = 0.0F;
			this.left_leg_base.xRot = 0.0F;

		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}