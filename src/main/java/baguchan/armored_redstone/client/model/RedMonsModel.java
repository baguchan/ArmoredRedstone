package baguchan.armored_redstone.client.model;// Made with Blockbench 4.4.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.armored_redstone.client.animation.RedMonsArmorAnimation;
import baguchan.armored_redstone.entity.RedMonsArmorEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class RedMonsModel<T extends RedMonsArmorEntity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart RightLeg;
	private final ModelPart LeftLeg;

	public RedMonsModel(ModelPart root) {
		this.root = root.getChild("root");
		this.RightLeg = this.root.getChild("RightLeg");
		this.LeftLeg = this.root.getChild("LeftLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition RightLeg = root.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 37).addBox(-3.0F, -1.0F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -6.0F, 1.0F));

		PartDefinition cube_r1 = RightLeg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 10).addBox(0.5F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -1.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r2 = RightLeg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 12).addBox(-2.5F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(43, 44).addBox(-2.5F, -5.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition LeftLeg = root.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(0, 37).addBox(-2.0F, -1.0F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -6.0F, 1.0F));

		PartDefinition cube_r3 = LeftLeg.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(43, 44).addBox(-1.5F, -5.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r4 = LeftLeg.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 12).addBox(7.5F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r5 = LeftLeg.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 10).addBox(4.5F, 0.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 5.0F, -1.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -1.0F, -3.0F, 10.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(41, 54).addBox(-6.5F, -6.0F, -4.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(52, 31).addBox(-6.5F, -8.0F, 3.0F, 3.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 45).addBox(2.5F, -8.0F, 3.0F, 3.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(22, 31).addBox(-3.5F, -10.0F, 4.0F, 6.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(47, 22).addBox(-3.5F, -6.0F, -5.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(52, 0).addBox(2.5F, -6.0F, -4.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(29, 44).addBox(4.5F, -6.0F, -2.0F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(15, 44).addBox(-7.5F, -6.0F, -2.0F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(41, 10).addBox(-2.5F, -2.0F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 10).addBox(-4.5F, 3.0F, -2.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(42, 16).addBox(-3.5F, 2.0F, -1.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.5F, -5.0F, -6.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.0F, -2.0F, -6.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(0.0F, -2.0F, -6.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(14, 56).addBox(-2.5F, -6.0F, 6.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -11.0F, 0.0F));

		PartDefinition cube_r6 = body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 56).addBox(-4.0F, -4.0F, 2.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -2.0F, -4.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition RightHand = root.addOrReplaceChild("RightHand", CubeListBuilder.create(), PartPose.offset(-7.0F, -15.0F, 1.0F));

		PartDefinition cube_r7 = RightHand.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(38, 31).addBox(-6.0F, -1.0F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition sholR = RightHand.addOrReplaceChild("sholR", CubeListBuilder.create().texOffs(34, 0).addBox(-4.0F, -7.0F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(21, 22).addBox(-8.0F, -2.0F, -2.5F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition handR = RightHand.addOrReplaceChild("handR", CubeListBuilder.create().texOffs(0, 3).addBox(-6.0F, 10.0F, -2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 3).addBox(-6.0F, 10.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 27).addBox(-7.0F, 5.5F, -2.5F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 3).addBox(-3.0F, 10.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.48F, 0.0F, 0.0F));

		PartDefinition LeftHand = root.addOrReplaceChild("LeftHand", CubeListBuilder.create(), PartPose.offset(7.0F, -15.0F, 1.0F));

		PartDefinition cube_r8 = LeftHand.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(38, 31).addBox(2.0F, -1.0F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition sholL = LeftHand.addOrReplaceChild("sholL", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, -2.0F, -2.5F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(28, 10).addBox(0.0F, -7.0F, -2.5F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition handL = LeftHand.addOrReplaceChild("handL", CubeListBuilder.create().texOffs(0, 27).addBox(1.0F, 5.5F, -2.5F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 3).addBox(2.0F, 10.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 3).addBox(5.0F, 10.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 3).addBox(5.0F, 10.0F, -2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.48F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.attackAnimationState, RedMonsArmorAnimation.ATTACK, ageInTicks);
		if (!entity.isSprinting() && entity.isOnGround()) {
			this.RightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.2F * limbSwingAmount;
			this.LeftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.2F * limbSwingAmount;
		} else {
			this.RightLeg.xRot = 0.0F;
			this.LeftLeg.xRot = 0.0F;
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}