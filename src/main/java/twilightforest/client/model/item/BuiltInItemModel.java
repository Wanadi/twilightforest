package twilightforest.client.model.item;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class BuiltInItemModel implements IBakedModel {

	private class Overrides extends ItemOverrideList {

		Overrides() {
			super(/*Collections.emptyList()*/);
		}

		@Override
		public IBakedModel getModelWithOverrides(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable LivingEntity entity) {
			setItemStack(stack);
			return BuiltInItemModel.this;
		}
	}

	private final TextureAtlasSprite particleTexture;
	private final ItemOverrideList overrides = new Overrides();

	protected BuiltInItemModel(String particleTextureName) {
		this.particleTexture = Minecraft.getInstance().getTextureMap().getAtlasSprite(particleTextureName);
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		return Collections.emptyList();
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return particleTexture;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return overrides;
	}

	@Override
	public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		setTransform(cameraTransformType);
		return Pair.of(this, null);
	}

	protected abstract void setItemStack(ItemStack stack);

	protected abstract void setTransform(ItemCameraTransforms.TransformType transform);
}
