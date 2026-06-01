package com.mongoose.clanginghowl.client.render.model;

import com.mongoose.clanginghowl.client.render.animation.HematomaAnimations;
import com.mongoose.clanginghowl.common.entities.hostiles.Hematoma;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class HematomaModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart main;
	private final ModelPart body_main;
	private final ModelPart body;
	private final ModelPart eye;
	private final ModelPart right_leg1;
	private final ModelPart right_leg_part1;
	private final ModelPart left_leg1;
	private final ModelPart left_leg_part1;
	private final ModelPart right_leg2;
	private final ModelPart right_leg_part2;
	private final ModelPart left_leg2;
	private final ModelPart left_leg_part2;

	public HematomaModel(ModelPart root) {
		this.root = root;
		this.main = root.getChild("main");
		this.body_main = this.main.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.eye = this.body_main.getChild("eye");
		this.right_leg1 = this.main.getChild("right_leg1");
		this.right_leg_part1 = this.right_leg1.getChild("right_leg_part1");
		this.left_leg1 = this.main.getChild("left_leg1");
		this.left_leg_part1 = this.left_leg1.getChild("left_leg_part1");
		this.right_leg2 = this.main.getChild("right_leg2");
		this.right_leg_part2 = this.right_leg2.getChild("right_leg_part2");
		this.left_leg2 = this.main.getChild("left_leg2");
		this.left_leg_part2 = this.left_leg2.getChild("left_leg_part2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.33F, 16.0317F, 0.551F));

		PartDefinition body_main = main.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(-0.48F, 1.1683F, -0.401F));

		PartDefinition cube_r1 = body_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 50).addBox(-1.85F, 0.5F, -4.85F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(24, 25).addBox(-3.85F, -2.5F, -4.15F, 8.0F, 7.0F, 4.0F, new CubeDeformation(0.1F))
				.texOffs(1, 26).addBox(-3.85F, -2.5F, -4.15F, 8.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -2.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.6821F, -2.6152F, 2.5193F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(51, 29).addBox(-4.85F, -4.5F, 3.85F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.85F, -5.5F, -1.15F, 11.0F, 10.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(28, 46).addBox(0.15F, -5.5F, -1.15F, 7.0F, 7.0F, 11.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(-1.6821F, 0.6152F, -4.5193F, 0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(46, -1).addBox(0.0F, -3.8F, -4.0F, 0.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.4321F, -5.4012F, -1.5382F, 0.1303F, 0.1165F, -0.7254F));

		PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(43, -9).addBox(0.0F, -3.8F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4679F, -5.4012F, -1.5382F, 0.1736F, 0.0182F, -0.173F));

		PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(45, 6).addBox(0.0F, -3.8F, -4.0F, 0.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.4679F, -5.5012F, -1.5382F, 0.1621F, -0.0651F, 0.3787F));

		PartDefinition eye = body_main.addOrReplaceChild("eye", CubeListBuilder.create(), PartPose.offset(-3.0408F, -1.8606F, -5.7335F));

		PartDefinition cube_r6 = eye.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 40).addBox(-1.5F, -1.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(10, 49).addBox(-1.5F, -1.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.4908F, 0.5016F, -0.6867F, 0.0178F, 0.192F, 0.0034F));

		PartDefinition right_leg1 = main.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(30, 36).addBox(-1.0F, -11.5F, -1.1F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0572F, 2.1683F, -2.1436F, 0.4534F, -0.6206F, -0.6974F));

		PartDefinition right_leg_part1 = right_leg1.addOrReplaceChild("right_leg_part1", CubeListBuilder.create().texOffs(0, 21).addBox(-14.5F, -0.5F, -1.5F, 16.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(10, 40).addBox(-14.5F, 0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6414F, -9.7929F, -0.1F, 0.0F, 0.0F, -0.6632F));

		PartDefinition left_leg1 = main.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(30, 36).mirror().addBox(-1.0F, -11.5F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0227F, 2.1683F, -2.7107F, 0.4673F, 0.6339F, 0.7056F));

		PartDefinition left_leg_part1 = left_leg1.addOrReplaceChild("left_leg_part1", CubeListBuilder.create().texOffs(0, 21).mirror().addBox(-1.5F, -0.5F, -1.5F, 16.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(10, 40).mirror().addBox(13.5F, 0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.6414F, -9.7929F, 0.0F, 0.0F, 0.0F, 0.6632F));

		PartDefinition right_leg2 = main.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(38, 36).addBox(-1.0F, -6.5F, -1.2F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.4343F, 2.2476F, 5.57F, -0.4067F, 0.5732F, -0.6712F));

		PartDefinition right_leg_part2 = right_leg2.addOrReplaceChild("right_leg_part2", CubeListBuilder.create().texOffs(0, 36).addBox(-11.3F, -0.5F, -1.5F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(38, 21).addBox(-11.3F, 0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0025F, -5.6029F, -0.1823F, 0.0F, 0.0F, -0.6632F));

		PartDefinition left_leg2 = main.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(38, 36).mirror().addBox(-1.0F, -6.5F, -1.1F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.9379F, 2.2476F, 5.4929F, -0.4067F, -0.5732F, 0.6712F));

		PartDefinition left_leg_part2 = left_leg2.addOrReplaceChild("left_leg_part2", CubeListBuilder.create().texOffs(0, 36).mirror().addBox(-0.7F, -0.5F, -1.5F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(38, 21).mirror().addBox(10.3F, 0.5F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0025F, -5.6029F, -0.0823F, 0.0F, 0.0F, 0.6632F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(HematomaAnimations.WALK, limbSwing, limbSwingAmount, 2.5F, 20.0F);
		if (entity instanceof Hematoma hematoma){
			this.animate(hematoma.idleAnimationState, HematomaAnimations.IDLE, ageInTicks);
			this.animate(hematoma.attackAnimationState, HematomaAnimations.ATTACK, ageInTicks);
			this.animate(hematoma.spreadAnimationState, HematomaAnimations.SPREAD, ageInTicks);
			this.animate(hematoma.appearAnimationState, HematomaAnimations.APPEAR, ageInTicks);
			this.animate(hematoma.deathAnimationState, HematomaAnimations.DEATH, ageInTicks);
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}