// Date: 4/27/2012 9:49:06 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package twilightforest.client.model.entity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import twilightforest.entity.passive.EntityTFSquirrel;

public class ModelTFSquirrel<T extends EntityTFSquirrel> extends EntityModel<T> {
	//fields
	RendererModel body;
	RendererModel leg1;
	RendererModel leg2;
	RendererModel leg3;
	RendererModel leg4;
	RendererModel head;
	RendererModel tail;
	RendererModel fluff1;
	RendererModel fluff2;
	RendererModel fluff3;

	public ModelTFSquirrel() {
		textureWidth = 32;
		textureHeight = 32;
		setTextureOffset("head.head", 0, 0);
		setTextureOffset("head.ear2", 16, 0);
		setTextureOffset("head.ear1", 16, 0);
		setTextureOffset("tail.fluff1", 0, 20);
		setTextureOffset("tail.base", 0, 18);
		setTextureOffset("tail.fluff2", 0, 20);
		setTextureOffset("tail.fluff3", 0, 26);

		body = new RendererModel(this, 0, 8);
		body.addBox(-2F, -1F, -2F, 4, 3, 5);
		body.setRotationPoint(0F, 21F, 0F);
		body.setTextureSize(32, 32);
		body.mirror = true;
		setRotation(body, 0F, 0F, 0F);
		leg1 = new RendererModel(this, 0, 16);
		leg1.addBox(0F, 0F, 0F, 1, 1, 1);
		leg1.setRotationPoint(-2F, 23F, 2F);
		leg1.setTextureSize(32, 32);
		leg1.mirror = true;
		setRotation(leg1, 0F, 0F, 0F);
		leg2 = new RendererModel(this, 0, 16);
		leg2.addBox(0F, 0F, 0F, 1, 1, 1);
		leg2.setRotationPoint(1F, 23F, 2F);
		leg2.setTextureSize(32, 32);
		leg2.mirror = true;
		setRotation(leg2, 0F, 0F, 0F);
		leg3 = new RendererModel(this, 0, 16);
		leg3.addBox(0F, 0F, 0F, 1, 1, 1);
		leg3.setRotationPoint(-2F, 23F, -2F);
		leg3.setTextureSize(32, 32);

		setRotation(leg3, 0F, 0F, 0F);
		leg4 = new RendererModel(this, 0, 16);
		leg4.addBox(0F, 0F, 0F, 1, 1, 1);
		leg4.setRotationPoint(1F, 23F, -2F);
		leg4.setTextureSize(32, 32);

		setRotation(leg4, 0F, 0F, 0F);
		head = new RendererModel(this, "head");
		head.setRotationPoint(0F, 22F, -2F);
		setRotation(head, 0F, 0F, 0F);

		head.addBox("head", -2F, -5F, -3F, 4, 4, 4);
		head.addBox("ear2", -2F, -6F, -0.5F, 1, 1, 1);
		head.addBox("ear1", 1F, -6F, -0.5F, 1, 1, 1);

		tail = new RendererModel(this, "tail");
		tail.setRotationPoint(0F, 21F, 2F);

		tail.addBox("base", -0.5F, -1.5F, 0.5F, 1, 1, 1);

		fluff1 = new RendererModel(this, 0, 20);
		fluff1.addBox(-1.5F, -4F, 1F, 3, 3, 3);
		tail.addChild(fluff1);

		fluff2 = new RendererModel(this, 0, 20);
		fluff2.addBox(0F, -3F, -1.5F, 3, 3, 3);
		fluff2.setRotationPoint(-1.5F, -4F, 2.5F);
		fluff1.addChild(fluff2);

		fluff3 = new RendererModel(this, 0, 26);
		fluff3.addBox(1.5F, -3F, -1.5F, 3, 3, 3);
		fluff3.setRotationPoint(-1.5F, -3F, 0F);
		fluff2.addChild(fluff3);
	}

	@Override
	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		body.render(scale);
		leg1.render(scale);
		leg2.render(scale);
		leg3.render(scale);
		leg4.render(scale);
		head.render(scale);
		tail.render(scale);
	}

	private void setRotation(RendererModel model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}


	/**
	 * Used for easily adding entity-dependent animations. The second and third float params here are the same second
	 * and third as in the setRotationAngles method.
	 */
	@Override
	public void setLivingAnimations(T entity, float limbSwing, float limbSwingAmount, float partialTickTime) {
		//EntityTFSquirrel squirrel = (EntityTFSquirrel)entity;


	}

	/**
	 * Sets the models various rotation angles.
	 */
	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
		this.head.rotateAngleX = headPitch / (180F / (float) Math.PI);
		this.head.rotateAngleY = netHeadYaw / (180F / (float) Math.PI);
		this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
		this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

		if (limbSwingAmount > 0.2) {
			float wiggle = Math.min(limbSwingAmount, 0.6F);
			this.tail.rotateAngleX = (MathHelper.cos(ageInTicks * 0.6662F) * 1.0F - (float) Math.PI / 3) * wiggle;
			this.fluff2.rotateAngleX = MathHelper.cos(ageInTicks * 0.7774F) * 1.2F * wiggle;
			this.fluff3.rotateAngleX = MathHelper.cos(ageInTicks * 0.8886F + (float) Math.PI / 2) * 1.4F * wiggle;
		} else {
			this.tail.rotateAngleX = 0.2F + MathHelper.cos(ageInTicks * 0.3335F) * 0.15F;
			this.fluff2.rotateAngleX = 0.1F + MathHelper.cos(ageInTicks * 0.4445F) * 0.20F;
			this.fluff3.rotateAngleX = 0.1F + MathHelper.cos(ageInTicks * 0.5555F) * 0.25F;
		}

	}

}
