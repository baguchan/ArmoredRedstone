package baguchan.armored_redstone.client.model;// Made with Blockbench 4.4.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.armored_redstone.client.animation.FireArmorAnimation;
import baguchan.armored_redstone.entity.FireArmorEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class FireArmorModel<T extends FireArmorEntity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart RightLeg;
	private final ModelPart LeftLeg;
	private final ModelPart body;
	private final ModelPart RightHand;
	private final ModelPart LeftHand;

	public FireArmorModel(ModelPart root) {
		this.root = root;
		this.RightLeg = root.getChild("RightLeg");
		this.LeftLeg = root.getChild("LeftLeg");
		this.body = root.getChild("body");
		this.RightHand = root.getChild("RightHand");
		this.LeftHand = root.getChild("LeftHand");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(21, 23).addBox(-3.0F, 6.0F, -4.0F, 5.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(12, 43).addBox(-2.0F, 0.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(39, 38).addBox(-2.0F, 5.0F, -3.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 17.0F, 1.0F));

		PartDefinition cube_r1 = RightLeg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(50, 15).addBox(-1.5F, 0.5F, -2.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(39, 38).addBox(-1.0F, 5.0F, -3.0F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(21, 23).addBox(-2.0F, 6.0F, -4.0F, 5.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(12, 43).addBox(-1.0F, 0.0F, -2.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 17.0F, 1.0F));

		PartDefinition cube_r2 = LeftLeg.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(50, 15).addBox(-0.5F, 0.5F, -2.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(38, 21).addBox(-2.0F, 5.0F, -1.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).addBox(-4.0F, 6.0F, -2.5F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(-5.0F, 2.0F, -3.0F, 10.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(10, 49).addBox(-6.0F, -3.0F, -4.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 46).addBox(-6.0F, -3.0F, 3.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(38, 44).addBox(3.0F, -3.0F, 3.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).addBox(3.5F, -2.0F, 5.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(-5.5F, -2.0F, 5.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(55, 32).addBox(-5.0F, 3.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(55, 32).addBox(4.0F, 3.0F, 5.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(20, 50).addBox(-2.0F, -2.0F, 6.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(34, 12).addBox(-3.0F, -3.0F, 4.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(38, 0).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(48, 44).addBox(3.0F, -3.0F, -4.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(30, 31).addBox(5.0F, -3.0F, -2.0F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(16, 31).addBox(-7.0F, -3.0F, -2.0F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, -2.5F, -6.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 41).addBox(-2.0F, 1.0F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, 0.0F));

		PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(45, 27).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition RightHand = partdefinition.addOrReplaceChild("RightHand", CubeListBuilder.create().texOffs(0, 30).addBox(-5.0F, -3.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(30, 0).addBox(-5.5F, -2.0F, -1.5F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(30, 50).addBox(-4.0F, 8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(26, 43).addBox(-4.5F, 4.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(50, 9).addBox(-4.5F, -6.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(39, 31).addBox(-5.0F, -2.0F, -1.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 10.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition LeftHand = partdefinition.addOrReplaceChild("LeftHand", CubeListBuilder.create().texOffs(54, 1).addBox(-1.5F, -2.0F, -1.5F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 30).addBox(-5.0F, -3.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(30, 50).addBox(-4.0F, 8.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(26, 43).addBox(-4.5F, 4.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(50, 9).addBox(-4.5F, -6.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(39, 31).addBox(-7.0F, -2.0F, -1.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 10.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.attackAnimationState, FireArmorAnimation.ATTACK, ageInTicks);
		this.animate(entity.attackFinishedAnimationState, FireArmorAnimation.ATTACKFINISH, ageInTicks);
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