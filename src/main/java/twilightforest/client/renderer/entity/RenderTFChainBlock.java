package twilightforest.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EntityTFChainBlock;

public class RenderTFChainBlock<T extends EntityTFChainBlock> extends EntityRenderer<T> {

	private static final ResourceLocation textureLoc = TwilightForestMod.getModelTexture("blockgoblin.png");
	private final ModelBase model;

	public RenderTFChainBlock(EntityRendererManager manager, ModelBase modelTFSpikeBlock) {
		super(manager);
		this.model = modelTFSpikeBlock;
	}

	@Override
	public void doRender(T chainBlock, double x, double y, double z, float yaw, float partialTicks) {
		super.doRender(chainBlock, x, y, z, yaw, partialTicks);

		GlStateManager.pushMatrix();
		GlStateManager.translated(x, y, z);

		this.bindEntityTexture(chainBlock);

		GlStateManager.scalef(-1.0F, -1.0F, 1.0F);


		GlStateManager.rotatef(MathHelper.wrapDegrees((float) y), 1, 0, 1);
		GlStateManager.rotatef(MathHelper.wrapDegrees(((float) x + (float) z) * 11F), 0, 1, 0);
//        GlStateManager.rotatef(MathHelper.wrapDegrees((float)yaw), 0, 0, 1);


		this.model.render(chainBlock, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();

		renderManager.renderEntityStatic(chainBlock.chain1, partialTicks, false);
		renderManager.renderEntityStatic(chainBlock.chain2, partialTicks, false);
		renderManager.renderEntityStatic(chainBlock.chain3, partialTicks, false);
		renderManager.renderEntityStatic(chainBlock.chain4, partialTicks, false);
		renderManager.renderEntityStatic(chainBlock.chain5, partialTicks, false);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTFChainBlock entity) {
		return textureLoc;
	}
}
