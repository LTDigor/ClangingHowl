package com.mongoose.clanginghowl.client.render.model;

import com.mongoose.clanginghowl.client.render.animation.CarcassAnimations;
import com.mongoose.clanginghowl.common.entities.hostiles.Carcass;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class CarcassModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart main;
	private final ModelPart body_main;
	private final ModelPart body;
	private final ModelPart left_leg1;
	private final ModelPart right_leg1;
	private final ModelPart left_leg2;
	private final ModelPart left_leg_part;
	private final ModelPart right_leg2;
	private final ModelPart right_leg_part;

	public CarcassModel(ModelPart root) {
		this.root = root;
		this.main = root.getChild("main");
		this.body_main = this.main.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.left_leg1 = this.body_main.getChild("left_leg1");
		this.right_leg1 = this.body_main.getChild("right_leg1");
		this.left_leg2 = this.main.getChild("left_leg2");
		this.left_leg_part = this.left_leg2.getChild("left_leg_part");
		this.right_leg2 = this.main.getChild("right_leg2");
		this.right_leg_part = this.right_leg2.getChild("right_leg_part");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(4.0667F, 11.7833F, 3.4667F));

		PartDefinition body_main = main.addOrReplaceChild("body_main", CubeListBuilder.create().texOffs(44, 42).addBox(-7.5F, -8.4F, -5.7F, 15.0F, 15.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(86, 19).addBox(-5.5F, -8.4F, 5.3F, 11.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0667F, -1.4833F, 7.7333F, -0.0524F, 0.0F, 0.0F));

		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -11.5667F, -21.0667F, 21.0F, 20.0F, 22.0F, new CubeDeformation(0.001F))
		.texOffs(72, 68).addBox(-1.5F, -13.5667F, -11.0667F, 3.0F, 8.0F, 14.0F, new CubeDeformation(0.0F))
		.texOffs(0, 42).addBox(0.0F, -21.5667F, -13.0667F, 0.0F, 15.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.8333F, -6.6333F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(56, 106).mirror().addBox(0.0F, -1.5F, -8.0F, 0.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.8F, -12.9667F, -12.0667F, 0.0F, 0.0F, -0.7505F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(57, 97).addBox(0.0F, -1.5F, -8.0F, 0.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.6F, -13.4667F, -10.0667F, 0.0F, 0.0F, 0.5061F));

		PartDefinition left_leg1 = body_main.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(44, 68).addBox(-1.0F, -4.0F, -3.5F, 7.0F, 22.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5F, -3.2F, -18.2F, 0.0349F, 0.0F, 0.0F));

		PartDefinition right_leg1 = body_main.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(44, 68).mirror().addBox(-6.0F, -4.0F, -3.5F, 7.0F, 22.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-10.5F, -3.2F, -18.2F, 0.0349F, 0.0F, 0.0F));

		PartDefinition left_leg2 = main.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(86, 0).addBox(-1.6333F, -5.1667F, -5.2333F, 6.0F, 10.0F, 9.0F, new CubeDeformation(0.002F)), PartPose.offset(4.0667F, 0.3833F, 8.2667F));

		PartDefinition left_leg_part = left_leg2.addOrReplaceChild("left_leg_part", CubeListBuilder.create().texOffs(0, 89).addBox(-3.2F, -1.55F, -1.5F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 79).addBox(-4.2F, 9.45F, -5.5F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5667F, 2.3833F, 2.2667F));

		PartDefinition right_leg2 = main.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(86, 0).mirror().addBox(-4.3667F, -5.1667F, -5.2333F, 6.0F, 10.0F, 9.0F, new CubeDeformation(0.002F)).mirror(false), PartPose.offset(-12.2F, 0.3833F, 8.2667F));

		PartDefinition right_leg_part = right_leg2.addOrReplaceChild("right_leg_part", CubeListBuilder.create().texOffs(0, 89).mirror().addBox(-2.8F, -1.55F, -1.5F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 79).mirror().addBox(-5.8F, 9.45F, -5.5F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5667F, 2.3833F, 2.2667F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if (entity instanceof Carcass carcass) {
			this.animate(carcass.idleAnimationState, CarcassAnimations.IDLE, ageInTicks);
			this.animate(carcass.attackAnimationState, CarcassAnimations.ATTACK, ageInTicks);
			this.animate(carcass.spitAnimationState, CarcassAnimations.SPIT, ageInTicks);
			this.animate(carcass.quakeAnimationState, CarcassAnimations.QUAKE, ageInTicks);
			this.animate(carcass.appearAnimationState, CarcassAnimations.APPEAR, ageInTicks);
			this.animate(carcass.growthAnimationState, CarcassAnimations.GROWTH, ageInTicks);
			if (carcass.isCurrentAnimation(Carcass.IDLE)) {
				this.animateWalk(CarcassAnimations.WALK, limbSwing, limbSwingAmount, 2.5F, 20.0F);
			}
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}