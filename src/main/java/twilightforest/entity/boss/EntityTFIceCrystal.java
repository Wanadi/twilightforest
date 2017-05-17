package twilightforest.entity.boss;

import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.SoundEvent;
import twilightforest.TFSounds;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityTFIceCrystal extends EntityMob {
	private int crystalAge;
	private int maxCrystalAge = -1;

	public EntityTFIceCrystal(World par1World) {
		super(par1World);
        this.setSize(0.6F, 1.8F);

        //this.setCurrentItemOrArmor(0, new ItemStack(TFItems.iceSword));
	}

	@Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, false, null));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }

    @Override
    protected Item getDropItem()
    {
        return Items.SNOWBALL;
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
    	return 8;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
    	return TFSounds.ICE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound()
    {
    	return TFSounds.ICE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
    	return TFSounds.ICE_DEATH;
    }

    public void setToDieIn30Seconds() {
    	this.maxCrystalAge = 600;
    }
    
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!world.isRemote) {
            this.crystalAge++;
            if (this.maxCrystalAge > 0 && this.crystalAge >= this.maxCrystalAge) {
                this.setDead();
            }
        }
    }
}
