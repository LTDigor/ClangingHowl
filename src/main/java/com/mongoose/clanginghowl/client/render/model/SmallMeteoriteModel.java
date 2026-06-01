package com.mongoose.clanginghowl.client.render.model;


import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class SmallMeteoriteModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart main;

	public SmallMeteoriteModel(ModelPart root) {
		this.root = root;
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

		PartDefinition rotated = main.addOrReplaceChild("rotated", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 17.0F, 8.0F, new CubeDeformation(0.1F))
				.texOffs(54, 68).addBox(4.0F, 6.0F, 4.0F, -8.0F, -17.0F, -8.0F, new CubeDeformation(-0.35F))
				.texOffs(0, 25).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {
	}

	public void setupAnim(float p_103811_, float p_103812_, float p_103813_) {
		this.main.yRot = p_103812_ * ((float)Math.PI / 180F);
		this.main.xRot = p_103813_ * ((float)Math.PI / 180F);
	}
}