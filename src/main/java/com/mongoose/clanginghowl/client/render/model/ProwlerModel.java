package com.mongoose.clanginghowl.client.render.model;

import com.mongoose.clanginghowl.client.render.animation.ProwlerAnimations;
import com.mongoose.clanginghowl.common.entities.hostiles.Prowler;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class ProwlerModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart main;
	private final ModelPart left_leg;
	private final ModelPart left_leg_part;
	private final ModelPart left_leg_claw_2;
	private final ModelPart left_leg_claw_1;
	private final ModelPart right_leg;
	private final ModelPart right_leg_part;
	private final ModelPart right_leg_claw_2;
	private final ModelPart right_leg_claw_1;
	private final ModelPart body_main;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart left_arm;
	private final ModelPart left_forearm;
	private final ModelPart right_arm;
	private final ModelPart right_forearm;
	private final ModelPart claw1;
	private final ModelPart claw2;
	private final ModelPart tail_1;
	private final ModelPart tail_2;
	private final ModelPart tail_3;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart bottom_jaw;

	public ProwlerModel(ModelPart root) {
		this.root = root;
		this.main = root.getChild("main");
		this.left_leg = this.main.getChild("left_leg");
		this.left_leg_part = this.left_leg.getChild("left_leg_part");
		this.left_leg_claw_2 = this.left_leg_part.getChild("left_leg_claw_2");
		this.left_leg_claw_1 = this.left_leg_part.getChild("left_leg_claw_1");
		this.right_leg = this.main.getChild("right_leg");
		this.right_leg_part = this.right_leg.getChild("right_leg_part");
		this.right_leg_claw_2 = this.right_leg_part.getChild("right_leg_claw_2");
		this.right_leg_claw_1 = this.right_leg_part.getChild("right_leg_claw_1");
		this.body_main = this.main.getChild("body_main");
		this.body = this.body_main.getChild("body");
		this.torso = this.body.getChild("torso");
		this.left_arm = this.body.getChild("left_arm");
		this.left_forearm = this.left_arm.getChild("left_forearm");
		this.right_arm = this.body.getChild("right_arm");
		this.right_forearm = this.right_arm.getChild("right_forearm");
		this.claw1 = this.right_forearm.getChild("claw1");
		this.claw2 = this.right_forearm.getChild("claw2");
		this.tail_1 = this.body_main.getChild("tail_1");
		this.tail_2 = this.tail_1.getChild("tail_2");
		this.tail_3 = this.tail_2.getChild("tail_3");
		this.neck = this.body_main.getChild("neck");
		this.head = this.neck.getChild("head");
		this.bottom_jaw = this.head.getChild("bottom_jaw");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 7.609F, -5.2537F, 0.0175F, 0.0F, 0.0F));

		PartDefinition left_leg = main.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(26, 55).addBox(-1.3212F, -2.7429F, -3.9179F, 4.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(3.8212F, 0.3928F, 9.0196F));

		PartDefinition left_leg_part = left_leg.addOrReplaceChild("left_leg_part", CubeListBuilder.create().texOffs(24, 72).addBox(-1.4914F, -1.5667F, -0.9209F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.01F))
				.texOffs(57, 89).addBox(0.0086F, -3.5667F, 0.0791F, 0.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.1702F, 3.8238F, 4.003F));

		PartDefinition left_leg_claw_2 = left_leg_part.addOrReplaceChild("left_leg_claw_2", CubeListBuilder.create(), PartPose.offset(1.1866F, 11.1833F, -0.2635F));

		PartDefinition cube_r1 = left_leg_claw_2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(82, 33).addBox(0.2518F, 2.75F, -2.8147F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(82, 60).addBox(0.7518F, 2.25F, -4.2147F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1903F, -3.5F, -0.033F, 0.0F, -0.3142F, 0.0F));

		PartDefinition left_leg_claw_1 = left_leg_part.addOrReplaceChild("left_leg_claw_1", CubeListBuilder.create(), PartPose.offset(-1.3952F, 11.4333F, -0.1157F));

		PartDefinition cube_r2 = left_leg_claw_1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(72, 60).addBox(-2.2477F, 2.75F, -2.8884F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(96, 0).addBox(-1.7477F, 1.75F, -4.8884F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3915F, -3.75F, -0.1807F, 0.0F, 0.2967F, 0.0F));

		PartDefinition right_leg = main.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 55).mirror().addBox(-2.6788F, -2.7429F, -3.9179F, 4.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.8212F, 0.3928F, 9.0196F));

		PartDefinition right_leg_part = right_leg.addOrReplaceChild("right_leg_part", CubeListBuilder.create().texOffs(24, 72).mirror().addBox(-1.5086F, -1.5667F, -0.9209F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(57, 89).mirror().addBox(-0.0086F, -3.5667F, 0.0791F, 0.0F, 15.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.1702F, 3.8238F, 4.003F));

		PartDefinition right_leg_claw_2 = right_leg_part.addOrReplaceChild("right_leg_claw_2", CubeListBuilder.create(), PartPose.offset(-1.1866F, 11.1833F, -0.2635F));

		PartDefinition cube_r3 = right_leg_claw_2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(82, 33).mirror().addBox(-2.2518F, 2.75F, -2.8147F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(82, 60).mirror().addBox(-1.7518F, 2.25F, -4.2147F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.1903F, -3.5F, -0.033F, 0.0F, 0.3142F, 0.0F));

		PartDefinition right_leg_claw_1 = right_leg_part.addOrReplaceChild("right_leg_claw_1", CubeListBuilder.create(), PartPose.offset(1.3952F, 11.4333F, -0.1157F));

		PartDefinition cube_r4 = right_leg_claw_1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(72, 60).mirror().addBox(0.2477F, 2.75F, -2.8884F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(96, 0).mirror().addBox(0.7477F, 1.75F, -4.8884F, 1.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.3915F, -3.75F, -0.1807F, 0.0F, -0.2967F, 0.0F));

		PartDefinition body_main = main.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0577F, -1.4152F, 9.152F));

		PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-0.0577F, -2.2925F, -1.8371F));

		PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(80, 71).addBox(-0.5F, -2.2F, 1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(82, 0).addBox(-2.0F, -1.2F, 0.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.05F))
				.texOffs(52, 103).addBox(1.0F, 3.8F, 3.0F, -3.0F, -5.0F, -3.0F, new CubeDeformation(0.0F))
				.texOffs(48, 81).addBox(-2.0F, -1.2F, 0.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.5F, -10.1136F, 2.6345F, -0.6458F, 0.0F, 0.0F));

		PartDefinition cube_r6 = body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(52, 103).addBox(1.5F, 4.5F, 8.7F, -3.0F, -5.0F, -3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.5463F, 6.5974F, -1.4312F, 0.0F, 0.0F));

		PartDefinition cube_r7 = body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(52, 103).addBox(1.5F, 6.3F, 2.0F, -3.0F, -5.0F, -3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -12.9872F, -3.339F, 0.1396F, 0.0F, 0.0F));

		PartDefinition cube_r8 = body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(80, 71).addBox(-0.5F, -10.5F, 6.8F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(12, 83).addBox(-2.0F, -9.5F, 5.8F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.05F))
				.texOffs(60, 81).addBox(-2.0F, -9.5F, 5.8F, 3.0F, 5.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.5F, -9.4178F, -2.3169F, -1.4312F, 0.0F, 0.0F));

		PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(80, 71).addBox(0.4751F, -11.303F, 7.7562F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(12, 75).addBox(-1.0249F, -10.303F, 6.7562F, 3.0F, 5.0F, 3.0F, new CubeDeformation(-0.1F))
				.texOffs(72, 81).addBox(-1.0249F, -10.303F, 6.7562F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-0.4751F, -0.5416F, -9.4067F, 0.1396F, 0.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0F, -1.2754F, -1.4027F));

		PartDefinition cube_r10 = torso.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 100).addBox(-5.5249F, -8.303F, -4.2438F, 0.0F, 9.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-11.0249F, -5.303F, -2.2438F, 11.0F, 9.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5249F, 0.7338F, -8.004F, 0.1396F, 0.0F, 0.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(6.2F, -0.1665F, -7.3488F));

		PartDefinition cube_r11 = left_arm.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 75).addBox(-1.5F, -3.0F, -0.8F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.8F, 1.9718F, 0.1908F, 0.4189F, 0.0F, 0.0F));

		PartDefinition left_forearm = left_arm.addOrReplaceChild("left_forearm", CubeListBuilder.create(), PartPose.offset(0.8F, 4.6064F, 1.4849F));

		PartDefinition cube_r12 = left_forearm.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(30, 115).addBox(-1.5F, 3.0F, -4.8F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(68, 33).addBox(-1.0F, 2.0F, -3.8F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.6346F, -1.294F, 0.4189F, 0.0F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.225F, 0.0561F, -7.2232F, -0.0694F, -0.0073F, 0.0526F));

		PartDefinition cube_r13 = right_arm.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(36, 72).addBox(-1.5F, -3.0F, -0.8F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.775F, 1.8493F, -0.0348F, 0.4189F, 0.0F, 0.0F));

		PartDefinition right_forearm = right_arm.addOrReplaceChild("right_forearm", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.8083F, 6.2406F, 2.0962F, 0.2967F, 0.0F, 0.0F));

		PartDefinition cube_r14 = right_forearm.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(68, 24).addBox(-1.0F, 4.0F, -5.8F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0333F, -4.3913F, -2.1309F, 0.4189F, 0.0F, 0.0F));

		PartDefinition claw1 = right_forearm.addOrReplaceChild("claw1", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.7667F, 1.5982F, -5.0962F, -0.4005F, 0.1881F, 0.0737F));

		PartDefinition cube_r15 = claw1.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(116, 38).addBox(-0.5F, -0.8849F, -2.5447F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition claw2 = right_forearm.addOrReplaceChild("claw2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.7333F, 1.9899F, -5.3053F, -0.3928F, -0.1638F, -0.0602F));

		PartDefinition cube_r16 = claw2.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(119, 56).addBox(1.0F, -2.7F, -1.4F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 1.6625F, -0.1405F, 0.0873F, 0.0F, 0.0F));

		PartDefinition tail_1 = body_main.addOrReplaceChild("tail_1", CubeListBuilder.create(), PartPose.offset(-0.0577F, -1.1792F, -0.7944F));

		PartDefinition cube_r17 = tail_1.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(34, 24).addBox(-3.5F, -3.8351F, -4.5288F, 7.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.8444F, 0.0442F, -0.6894F, 0.0F, 0.0F));

		PartDefinition tail_2 = tail_1.addOrReplaceChild("tail_2", CubeListBuilder.create(), PartPose.offset(0.1F, 3.0872F, 4.6557F));

		PartDefinition cube_r18 = tail_2.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(34, 40).addBox(-2.0F, -3.4618F, -11.4138F, 4.0F, 4.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 4.3925F, 8.3454F, -0.2705F, 0.0F, 0.0F));

		PartDefinition tail_3 = tail_2.addOrReplaceChild("tail_3", CubeListBuilder.create(), PartPose.offset(-0.1F, 2.7176F, 7.4283F));

		PartDefinition cube_r19 = tail_3.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 24).addBox(-0.5F, -6.4498F, 1.979F, 0.0F, 13.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(52, 0).addBox(-2.0F, -1.4498F, -1.021F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.1823F, 0.8718F, -0.0087F, 0.0F, 0.0F));

		PartDefinition neck = body_main.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(-0.0201F, -2.1511F, -11.5738F));

		PartDefinition cube_r20 = neck.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(26, 92).addBox(0.0F, -4.0F, -5.4F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0376F, 6.4189F, 0.9338F, 0.3198F, 0.0098F, 0.0F));

		PartDefinition cube_r21 = neck.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 54).addBox(-3.4346F, -1.7338F, -8.189F, 5.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.921F, -0.683F, 0.33F, 0.7125F, 0.0098F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(48, 55).addBox(-2.4872F, -3.0816F, -11.2887F, 5.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(88, 68).addBox(-2.4872F, -3.0816F, -11.2887F, 5.0F, 3.0F, 7.0F, new CubeDeformation(-0.1F))
				.texOffs(90, 56).addBox(-2.4872F, -0.0816F, -11.2887F, 5.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(52, 15).addBox(-3.4872F, -3.0816F, -4.2887F, 7.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(94, 25).addBox(-3.4872F, -3.0816F, -4.2887F, 7.0F, 4.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0398F, 4.7648F, -4.2703F));

		PartDefinition cube_r22 = head.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 96).addBox(-3.5F, -2.0F, -2.5F, 7.0F, 4.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0127F, 1.2184F, -2.5887F, 0.3927F, 0.0F, 0.0F));

		PartDefinition cube_r23 = head.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(110, 8).addBox(-2.1014F, -1.4122F, -0.2476F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.3264F, -1.6972F, -2.2729F, -0.1898F, 0.0656F, 0.3461F));

		PartDefinition cube_r24 = head.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(98, 8).addBox(-2.1908F, -2.4424F, 0.5237F, 5.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.3264F, -1.6972F, -2.2729F, 0.595F, -0.9226F, -0.7039F));

		PartDefinition cube_r25 = head.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(93, 17).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.1F))
				.texOffs(76, 15).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0775F, 0.9184F, -3.6887F, 0.2793F, 0.0F, 0.0F));

		PartDefinition bottom_jaw = head.addOrReplaceChild("bottom_jaw", CubeListBuilder.create(), PartPose.offset(-0.0872F, 0.8183F, -1.0974F));

		PartDefinition cube_r26 = bottom_jaw.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(88, 81).addBox(-3.5F, 0.5001F, -3.6913F, 7.0F, 1.0F, 5.0F, new CubeDeformation(0.01F))
				.texOffs(90, 41).addBox(-2.5F, -0.4999F, -10.6913F, 5.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(64, 40).addBox(-2.5F, 0.5001F, -10.6913F, 5.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 67).addBox(-3.5F, -1.4999F, -3.6913F, 7.0F, 3.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.1F, 0.6F, 0.5F, 0.3665F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(ProwlerAnimations.WALK, limbSwing, limbSwingAmount, 1.0F, 2.5F);
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		if (entity instanceof Prowler prowler){
			this.animate(prowler.idleAnimationState, ProwlerAnimations.IDLE, ageInTicks);
			this.animate(prowler.attackAnimationState, ProwlerAnimations.ATTACK, ageInTicks);
			this.animate(prowler.appearAnimationState, ProwlerAnimations.APPEAR, ageInTicks);
		}
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}