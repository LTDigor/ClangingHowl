package com.mongoose.clanginghowl.client.render.model;

import com.mongoose.clanginghowl.client.render.animation.BloodSpreaderAnimations;
import com.mongoose.clanginghowl.common.entities.hostiles.BloodSpreader;
import com.mongoose.clanginghowl.common.entities.hostiles.BloodyCopy;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class BloodSpreaderModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart main;
	private final ModelPart left_leg2;
	private final ModelPart right_leg2;
	private final ModelPart body_main;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart right_leg1;
	private final ModelPart left_leg1;
	private final ModelPart tail_left;
	private final ModelPart tail_left_part;
	private final ModelPart tail_right;
	private final ModelPart tail_right_part;

	public BloodSpreaderModel(ModelPart root) {
		this.root = root;
		this.main = root.getChild("main");
		this.left_leg2 = this.main.getChild("left_leg2");
		this.right_leg2 = this.main.getChild("right_leg2");
		this.body_main = this.main.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.torso = this.body.getChild("torso");
		this.head = this.body.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.right_leg1 = this.body.getChild("right_leg1");
		this.left_leg1 = this.body.getChild("left_leg1");
		this.tail_left = this.body_main.getChild("tail_left");
		this.tail_left_part = this.tail_left.getChild("tail_left_part");
		this.tail_right = this.body_main.getChild("tail_right");
		this.tail_right_part = this.tail_right.getChild("tail_right_part");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition left_leg2 = main.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(22, 51).addBox(-1.0F, -1.9567F, -1.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.001F))
		.texOffs(52, 47).addBox(-1.0F, 2.0283F, 0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(41, 54).addBox(-1.0F, 5.0283F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 1.9567F, 5.5F));

		PartDefinition right_leg2 = main.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(22, 51).mirror().addBox(-1.0F, -1.9567F, -1.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false)
		.texOffs(52, 47).mirror().addBox(-1.0F, 2.0283F, 0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(41, 54).mirror().addBox(-1.0F, 5.0283F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, 1.9567F, 5.5F));

		PartDefinition body_main = main.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(34, 18).addBox(-3.0F, -3.2F, -4.1F, 5.0F, 5.0F, 6.0F, new CubeDeformation(-0.002F)), PartPose.offset(0.5F, 0.2F, 5.1F));

		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -1.0F, -3.5F, 0.0524F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.9F, -5.3F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(-3.5F, -3.9F, -5.3F, 7.0F, 7.0F, 10.0F, new CubeDeformation(0.15F))
		.texOffs(0, 34).addBox(0.0F, -9.9F, -7.3F, 0.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.3F, -4.3F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(34, 0).mirror().addBox(0.0F, -3.7F, -4.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.3F, -4.9F, -1.3F, 0.0F, 0.0F, -0.6981F));

		PartDefinition cube_r2 = torso.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(34, 0).addBox(0.0F, -3.7F, -4.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.3F, -4.9F, -1.3F, 0.0F, 0.0F, 0.6981F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 34).addBox(-1.5F, -2.56F, -5.26F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(1, 36).addBox(-1.5F, 0.44F, -5.26F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(35, 0).addBox(-1.5F, -2.56F, -5.16F, 3.0F, 3.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -0.64F, -10.34F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 56).addBox(-1.5F, -1.7F, -5.9F, 3.0F, 1.0F, 6.0F, new CubeDeformation(-0.05F))
		.texOffs(22, 43).addBox(-1.5F, -0.8F, -5.9F, 3.0F, 2.0F, 6.0F, new CubeDeformation(-0.05F)), PartPose.offset(0.0F, 0.84F, 0.74F));

		PartDefinition right_leg1 = body.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(32, 51).mirror().addBox(-1.0F, -1.2F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, 2.8F, -6.6F, -0.0698F, 0.0F, 0.0F));

		PartDefinition left_leg1 = body.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(32, 51).addBox(-1.0F, -1.2F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 2.8F, -6.6F, -0.0698F, 0.0F, 0.0F));

		PartDefinition tail_left = body_main.addOrReplaceChild("tail_left", CubeListBuilder.create().texOffs(40, 29).addBox(-0.5F, -0.5F, -0.025F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, -2.5F, 1.725F, -0.5326F, 0.2018F, 0.0367F));

		PartDefinition tail_left_part = tail_left.addOrReplaceChild("tail_left_part", CubeListBuilder.create().texOffs(40, 38).addBox(-0.5F, -0.5F, -0.0333F, 1.0F, 1.0F, 8.0F, new CubeDeformation(-0.001F))
		.texOffs(40, 47).addBox(-1.0F, -1.0F, 6.9667F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.001F))
		.texOffs(34, 29).addBox(-0.5F, -0.5F, 10.3667F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 7.9083F, 0.4712F, 0.0F, 0.0F));

		PartDefinition tail_right = body_main.addOrReplaceChild("tail_right", CubeListBuilder.create().texOffs(40, 29).mirror().addBox(-0.5F, -0.5F, -0.025F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.7F, -2.7F, 1.625F, -0.6167F, -0.1285F, 0.0906F));

		PartDefinition tail_right_part = tail_right.addOrReplaceChild("tail_right_part", CubeListBuilder.create().texOffs(40, 38).mirror().addBox(-0.5F, -0.5F, -0.0333F, 1.0F, 1.0F, 8.0F, new CubeDeformation(-0.001F)).mirror(false)
		.texOffs(52, 17).mirror().addBox(-1.0F, -1.0F, 6.9667F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.001F)).mirror(false)
		.texOffs(34, 29).mirror().addBox(-0.5F, -0.5F, 10.3667F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 7.9083F, 0.576F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(BloodSpreaderAnimations.WALK, limbSwing, limbSwingAmount, 1.0F, 2.5F);
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		if (entity instanceof BloodSpreader bloodSpreader){
			this.animate(bloodSpreader.idleAnimationState, BloodSpreaderAnimations.IDLE, ageInTicks);
			this.animate(bloodSpreader.attackAnimationState, BloodSpreaderAnimations.ATTACK, ageInTicks);
			this.animate(bloodSpreader.roarAnimationState, BloodSpreaderAnimations.ROAR, ageInTicks);
			this.animate(bloodSpreader.appearAnimationState, BloodSpreaderAnimations.APPEAR, ageInTicks);
		} else if (entity instanceof BloodyCopy bloodyCopy){
			this.animate(bloodyCopy.idleAnimationState, BloodSpreaderAnimations.IDLE, ageInTicks);
			this.animate(bloodyCopy.attackAnimationState, BloodSpreaderAnimations.ATTACK, ageInTicks);
			this.animate(bloodyCopy.appearAnimationState, BloodSpreaderAnimations.BLOOD_APPEAR, ageInTicks);
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}